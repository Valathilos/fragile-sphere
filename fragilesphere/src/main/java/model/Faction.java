package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Faction {
  private static final Logger log = LoggerFactory.getLogger(Faction.class);

  private static Map<String, Faction> factions = new HashMap<String, Faction>();
  //    private List<Planet> ownedPlanets = new ArrayList<Planet>();
  private String name;
  private String shortName;
  private boolean isClan;
  private boolean isPeriphery;
  private float[] colour;

  public float[] getColour() {
    return colour;
  }

  private String nameGenerator;
  private String eraMods;


  //    public static Faction createFaction() {
  //    	Faction faction = new Faction();
  //    	faction.setShortName("TST");
  //    	faction.setName("Test");
  //    	
  //    	return faction;
  //    }

  public void setName(String name) {
    this.name = name;

  }

  public void setShortName(String hint) {
    this.shortName = hint;

  }

  public static Faction getFaction(String hint) {
    return factions.get(hint);
  }

  //	public void capture(Planet planet) {
  //		ownedPlanets.add(planet);
  //	}
  //	
  //	public void lose(Planet planet) {
  //		ownedPlanets.remove(planet);
  //	}

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public static List<Faction> init() {
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {
        private Faction faction;
        private String value;

        public void startElement(String uri, String locallName, String qName, Attributes attributes) throws SAXException {
          if (qName.equalsIgnoreCase("faction")) {
            faction = new Faction();	
          }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
          if (qName.equalsIgnoreCase("ShortName")) {
            faction.setShortName(value);
            factions.put(value, faction);
          }

          if (qName.equalsIgnoreCase("FullName")) {
            faction.setName(value);
          }

          if (qName.equalsIgnoreCase("eramods")) {
            faction.setEraMods(value);
          }

          if (qName.equalsIgnoreCase("namegenerator")) {
            faction.setNameGenerator(value);
          }

          if (qName.equalsIgnoreCase("colorrgb")) {
            float[] colour = new float[3];
            StringTokenizer st = new StringTokenizer(value, ",");

            int i = 0;
            while (st.hasMoreElements()) {
              int colorInt = Integer.parseInt(String.valueOf(st.nextElement())); 
              colour[i] = colorInt / 255.0f;
              i++;
            }
            faction.setColour(colour);
          }

          if (qName.equalsIgnoreCase("periphery")) {
            faction.setIsPeriphery(value);
          }

          if (qName.equalsIgnoreCase("clan")) {
            faction.setIsClan(value);
          }

          //					log.debug("Parsing Element: " + qName);
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
          value = new String(ch, start, length);	
        }
      };
      InputStream stream = Faction.class.getClassLoader().getResourceAsStream("data/universe/factions.xml");
      parser.parse(stream, handler);
      return new ArrayList<Faction>(factions.values());
    } catch (ParserConfigurationException | SAXException | IOException e) {
      log.error(MarkerFactory.getMarker("Error"), "Problem initialising faction list", e);
    } 

    return Collections.emptyList();
  }

  public void setIsClan(String value) {
    isClan = Boolean.parseBoolean(value);

  }

  protected void setIsPeriphery(String value) {
    isPeriphery = Boolean.parseBoolean(value);

  }

  protected void setColour(float[] value) {
    this.colour = value;

  }

  protected void setNameGenerator(String value) {
    this.nameGenerator = value;

  }

  protected void setEraMods(String value) {
    this.eraMods = value;

  }

  public boolean isClan() {
    return isClan;
  }

  public boolean isPeriphery() {
    return isPeriphery;
  }

  public String getNameGenerator() {
    return nameGenerator;
  }

  public String getEraMods() {
    return eraMods;
  }

}
