package render;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import guis.menus.MainMenu;
import render.engine.DisplayManager;

public class Main implements Runnable{

  public Thread main;
  
  private static enum State {
    INTRO, MAIN_MENU, GAME;
  }

  private State state = State.MAIN_MENU;
  private Game game;
  private GLFWKeyCallback keyboardCallback;
  private MainMenu menu;
  private PlanetRenderer renderer;
  private Shader shader;

  private void start() {
    main = new Thread(this, "main");
    main.start();    
  }
  
  public void run(){
    init();

    loop();
  }

  private void init() {
    DisplayManager.createDisplay("Fragile Sphere");
    
    shader = new Shader("planet");

    renderer = new PlanetRenderer(shader);

    game = new Game(renderer);

    game.startNewGame();


    GLFW.glfwSetKeyCallback(DisplayManager.getWindow(), keyboardCallback = new GLFWKeyCallback(){

      @Override
      public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action != GLFW_RELEASE)
          return;

        switch(state) {
          case INTRO -> {
            if (key == GLFW_KEY_ENTER) {
              state = State.MAIN_MENU;
            } else if (key == GLFW_KEY_ESCAPE) {
              GLFW.glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
            };
          }
          case MAIN_MENU -> {
            if (key == GLFW_KEY_ENTER) {
              menu.hide();
              state = State.GAME;
            } else if (key == GLFW_KEY_ESCAPE) {
              GLFW.glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
            }
          }
          case GAME -> {	
            if (key == GLFW_KEY_ESCAPE) {
              state = State.MAIN_MENU;
            }
          }
        }
      }

    });

  }

  private void loop() {

    // Set the clear color
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glEnable(GL_TEXTURE_2D);

    Camera camera = new Camera(DisplayManager.getWindow());

    while (!DisplayManager.shouldWindowClose()){
      glfwPollEvents();
      switch(state) {
        case INTRO -> {
          GL11.glColor3f(1.0f, 0f, 0f);
          GL11.glRectf(0, 0, 640, 480);

        }
        case MAIN_MENU -> showMainMenu();          
        case GAME -> {				
          renderer.prepare();

          shader.start();
//          shader.setUniform("textureSampler", 0);
//          shader.setUniform("sampler", 0);
          //							shader.setUniform("projection", target);
          //							tex.bind(0);
          game.render(shader, camera);
          //							model.render();
          shader.stop();

          //							renderer.render(texturedModel);

          //GUI Renderers go here

          renderer.prepare();
          //							renderer.render(model2);
        }
      }
      DisplayManager.updateDisplay();
    }

    shader.cleanUp();
    game.cleanUp();
    DisplayManager.destroyDisplay();
    keyboardCallback.free();
  }

  private void showMainMenu() {
    if (menu == null){
      menu = new MainMenu();
    }
    if (menu.isHidden()) {
      menu.show();
    }
    menu.update();
  }

  public static void main(String[] args) {
    new Main().start();
  }


}
