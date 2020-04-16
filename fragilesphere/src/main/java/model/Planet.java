package model;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import entities.Entity;
import render.Loader;

public class Planet extends Entity{
  private static final Logger LOGGER = LoggerFactory.getLogger(Planet.class);

  private String name;
  private String description;
  private Vector3f location;
  private List<Faction> owner = new ArrayList<Faction>();
  private List<Event> events = new ArrayList<Event>();


  public Planet(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
    super(model, position, rotX, rotY, rotZ, scale);
  }

  public static List<Planet> load(final Loader loader) {
    final List<Planet> planets = new ArrayList<Planet> ();
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {
        private String name;
        private String desc;
        private String faction;
        private String value;
        private List<Event> events = new ArrayList<Event>();
        private Instant eventDate;
        private String eventDetail;


        //				private boolean bIgnoreElement;
        private float xcoord = 0.0f;
        private float ycoord = 0.0f;
        private final float[] CONFLICT_COLOR = {6.0f, 6.0f, 6.0f};

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
          //Handle processing in the endElement
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {

          //if end of planet data, add to list of planets
          if (qName.equalsIgnoreCase("planet")) {
            float[] colours = {0, 0, 0};
            
            LOGGER.debug(name + ": " + faction);
            
            Faction owner = null;
            if (faction.contains(",")) {
              StringTokenizer st = new StringTokenizer(faction, ",");

              //Planet is controlled by multiple factions, set an 'In Conflict' color
              colours = CONFLICT_COLOR;

              while (st.hasMoreElements()) {
                owner = Faction.getFaction(String.valueOf(st.nextElement()));
              }

            } else {
              owner = Faction.getFaction(faction);
              colours = owner.getColour();
            }
            //Set it's location						
            Vector3f location = new Vector3f(xcoord/2000, ycoord/2000, 1.0f);
            RawModel model = loader.loadUntexturedModelToVao(new float[]{location.x, location.y, location.z}, new int[]{0}, colours);
            TexturedModel texturedModel = new TexturedModel(model, null);

            Planet planet = new Planet(texturedModel, location, 0,0,0,1);
            planet.setLocation(location);
            planet.setName(name);
            planet.setDescription(desc);
            planet.addOwner(owner);
            for (Event event : events) {
              planet.addEvent(event);
            }
            planets.add(planet);
          }

          if (qName.equalsIgnoreCase("name")) {
            name = value;
          }

          if (qName.equalsIgnoreCase("desc")) {
            desc = value;
          }

          if (qName.equalsIgnoreCase("xcood")) {
            try {
              xcoord = Float.parseFloat(value);
            } catch (NumberFormatException e) {
              LOGGER.error(MarkerFactory.getMarker("Error"), "xcoord could not be understood", e);
            }
          }
          if (qName.equalsIgnoreCase("ycood")) {
            try {
              ycoord = Float.parseFloat(value);
            } catch (NumberFormatException e) {
              LOGGER.error(MarkerFactory.getMarker("Error"), "ycoord could not be understood", e);
            }
          }

          if (qName.equalsIgnoreCase("faction")) {
            faction = value;
          }

          if (qName.equalsIgnoreCase("date")) {
            eventDate = LocalDate.parse(value).atStartOfDay(ZoneId.of("Etc/UTC")).toInstant();
          }

          if (qName.equalsIgnoreCase("faction")) {
            eventDetail = value;
          }

          if (qName.equalsIgnoreCase("factionChange")) {
            Event event = Event.recordEvent(eventDate, eventDetail); 
            LOGGER.debug("Faction Change: {} : {}", event.getOccurredAt(),event.getDetail() );
            events.add(event);
          }

        }

        public void characters(char[] ch, int start, int length) throws SAXException {
          value = new String(ch, start, length);	
        }
      };
      
      InputStream stream = Planet.class.getClassLoader().getResourceAsStream("data/universe/planets.xml");
      parser.parse(stream, handler);
      return planets;
      
    } catch (ParserConfigurationException | SAXException | IOException e) {
      LOGGER.error(MarkerFactory.getMarker("Error"), "Problem loading planets", e);
    }

    return Collections.emptyList();
  }

  //	public void finalise() {
  //		draw_count = 1;
  //		
  //		v_id = glGenBuffers();
  //		glBindBuffer(GL_ARRAY_BUFFER, v_id);
  //		glBufferData(GL_ARRAY_BUFFER, createBuffer(new float[]{location.x, location.y, location.z}), GL_STATIC_DRAW);
  ////		glVertexAttribPointer (0, 3, GL_FLOAT, false, 0, 0);
  //		glBindBuffer(GL_ARRAY_BUFFER, 0);
  //
  //		c_id = glGenBuffers();
  //		glBindBuffer(GL_ARRAY_BUFFER, c_id);
  //		glBufferData(GL_ARRAY_BUFFER, createBuffer(faction.getColour()), GL_STATIC_DRAW);
  ////		glVertexAttribPointer (1, 3, GL_FLOAT, false, 0, 0);
  //		glBindBuffer(GL_ARRAY_BUFFER, 0);
  //		
  ////		t_id = glGenBuffers();
  ////		glBindBuffer(GL_ARRAY_BUFFER, t_id);
  ////		glBufferData(GL_ARRAY_BUFFER, createBuffer(new float[]{0,0}), GL_STATIC_DRAW);
  ////		glBindBuffer(GL_ARRAY_BUFFER, 0);
  //
  //		i_id = glGenBuffers();
  //		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
  //		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBuffer(new int[] {0}), GL_STATIC_DRAW);
  //		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
  //	}

  //	public FloatBuffer createBuffer(float[] data) {
  //		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
  //		buffer.put(data);
  //		buffer.flip();
  //		
  //		return buffer;
  //	}

  //	public IntBuffer createBuffer(int[] data) {
  //		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
  //		buffer.put(data);
  //		buffer.flip();
  //		
  //		return buffer;
  //	}

  public Vector3f getLocation() {
    return location;
  }

  public void setName(String name) {
    this.name = name;

  }

  public void addOwner(Faction faction) {

    if (!owner.contains(faction)) {
      owner.add(faction);
    }
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public void removeOwner(Faction faction) {
    if (owner.contains(faction)) {
      owner.remove(faction);
    }
  }

  public void setDescription(String desc) {
    this.description = desc;
  }

  public String getName() {
    return name;
  }

  public List<Faction> getOwner() {
    return Collections.unmodifiableList(owner);
  }

  public String getDescription() {
    return description;
  }

  public void setLocation(Vector3f loc) {
    this.location = loc;
  }

  //	public void render(Shader shader) {
  //
  ////		glDisable(GL_POINT_SMOOTH);
  ////		glClear(GL_COLOR_BUFFER_BIT);
  ////	    glLoadIdentity();//load identity matrix
  //
  ////		shader.setUniform("red", faction.getColour()[0]);
  ////		shader.setUniform("green", faction.getColour()[1]);
  ////		shader.setUniform("blue", faction.getColour()[2]);
  //		
  ////		System.out.println(faction.getColour()[1]);
  ////		shader.setUniform("red", faction.getColour()[0]);
  ////		shader.setUniform("green", faction.getColour()[1]);
  ////		shader.setUniform("blue", 0.0f);
  //		
  //	    glPointSize(2.0f);
  //
  //	    glEnableVertexAttribArray(0);
  //		glEnableVertexAttribArray(1);
  ////		glEnableVertexAttribArray(2);
  //		
  //		glBindBuffer(GL_ARRAY_BUFFER, v_id);
  //		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
  ////		
  //		glBindBuffer(GL_ARRAY_BUFFER, c_id);
  //		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
  //		
  ////		glBindBuffer(GL_ARRAY_BUFFER, t_id);
  ////		glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
  //		
  //		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
  //		glDrawElements(GL_POINTS, draw_count, GL_UNSIGNED_INT, 0);
  //		
  //		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
  //		glBindBuffer(GL_ARRAY_BUFFER, 0);
  //		
  //		glDisableVertexAttribArray(0);
  //		glDisableVertexAttribArray(1);
  ////		glDisableVertexAttribArray(2);
  //
  //	}

}
