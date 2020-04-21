package render;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Faction;
import model.Planet;
import render.engine.Loader;

public class Game {
  private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

  //  private List<Faction> factions;
  private List<Planet> planets;
  private PlanetRenderer renderer;
  private Loader loader;

  public Loader getLoader() {
    return loader;
  }

  public Game(PlanetRenderer renderer) {
    this.renderer = renderer;
    this.loader = new Loader();

  }

  private void loadFactions() {
    LOGGER.debug("Load Factions");
    Faction.init();
  }

  private void loadPlanets() {
    LOGGER.debug("Load Planets");
    planets = Planet.load(this);
  }

  public boolean startNewGame() {
    LOGGER.debug("Start New Game");

    loadFactions();
    loadPlanets();

    //		establishMilitaryForces();
    //		establishFacilities();

    //		prepareEconomy();
    //		startManufacturing();

    return true;
  }

  public void render(Shader shader, Camera camera) {
    renderer.prepare();

    camera.move();
    shader.start();

    planets.get(0).increasePosition(0, 0, .01f);

    renderer.render(planets.get(0), shader, camera);
    shader.stop();

//        for (Planet planet: planets) {
//          planet.increasePosition(0, 0, .01f);
//          camera.move();
//          shader.start();
//          shader.loadViewMatrix(camera);
//    
//    
//          renderer.render(planet, shader, camera);
//          shader.stop();
//        }

  }

  public void cleanUp() {
    loader.cleanUp();
  }
}
