package render;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import entities.Entity;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import guis.menus.MainMenu;
import managers.DisplayManager;
import model.RawModel;
import model.TexturedModel;
import textures.ModelTexture;

public class Test {

  private static enum State {
    INTRO, MAIN_MENU, GAME;
  }

  private State state = State.GAME;
  private MainMenu menu;
  private GLFWKeyCallback keyboardCallback;

  private void run(){
    init();

    loop();

  }

  private void init() {



    DisplayManager.createDisplay("Tester");

    GLFW.glfwSetKeyCallback(DisplayManager.getWindow(), keyboardCallback = new GLFWKeyCallback(){

      @Override
      public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action != GLFW_RELEASE)
          return;
        switch(state) {
        case INTRO:
          if (key == GLFW_KEY_ENTER) {
            state = State.MAIN_MENU;
          }
          if (key == GLFW_KEY_ESCAPE) {
            GLFW.glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
          }
          break;
        case MAIN_MENU:
          if (key == GLFW_KEY_ENTER) {
            menu.hide();
            state = State.GAME;
          }
          if (key == GLFW_KEY_ESCAPE) {
            GLFW.glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
          }
          break;
        case GAME:	
          if (key == GLFW_KEY_ESCAPE) {
            state = State.MAIN_MENU;
          }
          break;
        }
      }

    });

    state = State.GAME;

  }

  private void loop() {


    // Set the clear color
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glEnable(GL_TEXTURE_2D);

    //		

    //		
    //			int[] indices = {
    //					0,1,3,	
    //					3,1,2,	
    //					4,5,7,
    //					7,5,6,
    //					8,9,11,
    //					11,9,10,
    //					12,13,15,
    //					15,13,14,	
    //					16,17,19,
    //					19,17,18,
    //					20,21,23,
    //					23,21,22
    //			};

    //				
    //				
    //				float[] vertices2 = {
    //						-0.15f, 0.0f,0f,  //TOP LEFT
    //						-0.15f,-0.15f,0f, //BOTTOM LEFT
    //						0.15f,-0.15f,0f,  //BOTTOM RIGHT
    //						0.15f, 0.0f,0f    //TOP RIGHT
    //				};
    //				

    //		
    //					float[] colours = {
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f,
    //							234/255.0f,38/255.0f,41/255.0f
    //							
    //							
    //					};
    //				
    ////				float[] colours2 = {
    ////						51.0f, 0.0f, 0.0f,
    ////						51.0f, 0.0f, 0.0f,
    ////						51.0f, 0.0f, 0.0f,
    ////						51.0f, 0.0f, 0.0f
    ////					};
    //				


    //				Model model = new Model(vertices, texture, indices);
    Shader shader = new Shader("shader1");

    //				Matrix4f projection = new Matrix4f().ortho2D(width/2 * -1, width/2, -1 * height/2, height/2);
    //				Matrix4f scale = new Matrix4f().scale(400);
    //				Matrix4f target = new Matrix4f();

    //				projection.mul(scale, target);

    //		float x = 0;

    Renderer renderer = new Renderer(shader);
    Loader loader = new Loader();

    //Test Font Rendering
    TextMaster.init(loader);
    FontType font = new FontType(loader.loadTexture("fonts/Candara/candara.png"), "fonts/Candara/candara.fnt");
    GUIText text = new GUIText("A sample string of text!", 5, font, new Vector2f(0,0), 1f, true);

    //Test Entity Rendering
    ModelTexture texture = new ModelTexture(loader.loadTexture("textures/planet.jpg"));
    RawModel model = OBJLoader.loadObjModel("data/planet", loader);
    //	RawModel model = generateTestModel(loader);

    TexturedModel texturedModel = new TexturedModel(model, texture);
    Entity entity = new Entity(texturedModel, new Vector3f(0,0,-30),0,0,0,1);
    //		RawModel model2 = loader.loadToVao(vertices2, indices, colours2);

    //Text textures
    List<GuiTexture> guis = new ArrayList<GuiTexture>();
    ////		GuiTexture gui = new GuiTexture(loader.loadTexture("./resources/textures/cavhooah.jpg"), new Vector2f(0.25f, 0.25f), new Vector2f(0.05f, 0.05f));
    //		GuiTexture gui2 = new GuiTexture(loader.loadTexture("./resources/textures/thinmatrix.png"), new Vector2f(0f, .75f), new Vector2f(1f, 0.35f));
    //		GuiTexture gui3 = new GuiTexture(loader.loadTexture("./resources/textures/SkullHiRes.gif"), new Vector2f(0.5f, 0.2f), new Vector2f(0.25f, 0.25f));
    //		guis.add(gui2);
    //		guis.add(gui);
    //		guis.add(gui3);


    GuiRenderer guiRenderer = new GuiRenderer(loader);

    Camera camera = new Camera(DisplayManager.getWindow());


    //Button testing
    //		AbstractButton button = new AbstractButton(loader, "./resources/textures/cavhooah.jpg", new Vector2f(0,0), new Vector2f(0.2f,0.2f) ) {
    //
    //			@Override
    //			public void onClick(IButton button) {
    //				System.out.println("Button was clicked");
    //				
    //			}
    //
    //			@Override
    //			public void onStartHover(IButton button) {
    //				button.playHoverAnimation(0.092f);
    //				
    //			}
    //
    //			@Override
    //			public void onStopHover(IButton button) {
    //				button.resetScale();
    //				
    //			}
    //
    //			@Override
    //			public void whileHovering(IButton button) {
    //				// TODO Auto-generated method stub
    //				
    //			}
    //			
    //		};

    while (!DisplayManager.shouldWindowClose()){
      glfwPollEvents();
      switch(state) {
      case INTRO:
        GL11.glColor3f(1.0f, 0f, 0f);
        GL11.glRectf(0, 0, 640, 480);
        break;
      case MAIN_MENU:
        showMainMenu();
        break;
      case GAME:	
        //				GL11.glColor3f(0f, 0f, 1.0f);
        //				GL11.glRectf(0, 0, 640, 480);

        //			if (DisplayManager.shouldDisplayMenu()) {
        //				DisplayManager.showMainMenu();
        //			} else {
        //			if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_ESCAPE) == GLFW_TRUE) {
        //				if (button.isHidden()) {
        //					button.show(guis);					
        //				} else {
        //					button.hide(guis);
        //				}
        //				//				glfwSetWindowShouldClose(DisplayManager.getWindow(), GL_TRUE);
        //			}

        //			entity.increasePosition(0, 0, -0.1f);
//        entity.increaseRotation(0, 1, 0);

        camera.move();

        //			button.update();

        renderer.prepare();


        //			shader.setUniform("sampler", 0);
        //			shader.setUniform("projection", target);
        //			tex.bind(0);

        guiRenderer.render(guis);

        //			model.render();
        //			shader.start();
        renderer.render(entity, shader, camera);
        //			shader.stop();
        //			renderer.prepare();
        //			renderer.render(model2);

        TextMaster.render();

        break;
      }
      DisplayManager.updateDisplay();

    }

    //		button.hide(guis);
    TextMaster.cleanUp();
    guiRenderer.cleanUp();
    shader.cleanUp();
    DisplayManager.destroyDisplay();
    keyboardCallback.free();
  }

  private RawModel generateTestModel(Loader loader) {
    float[] colours = {
        51.0f, 30.0f, 0.0f,
        51.0f, 0.0f, 30.0f,
        51.0f, 30.0f, 0.0f,
        51.0f, 0.0f, 30.0f
    };

    float[] textureCoords = new float[] {
        0,0, //TOP LEFT
        1,0, //BOTTOM LEFT
        1,1, //BOTTOM RIGHT
        0,1  //TOP RIGHT
    };

    float[] vertices = {			
        -0.25f,0.25f,-0.25f,	
        -0.25f,-0.25f,-0.25f,	
        0.25f,-0.25f,-0.25f,	
        0.25f,0.25f,-0.25f,		

        -0.25f,0.25f,0.25f,	
        -0.25f,-0.25f,0.25f,	
        0.25f,-0.25f,0.25f,	
        0.25f,0.25f,0.25f,

        0.25f,0.25f,-0.25f,	
        0.25f,-0.25f,-0.25f,	
        0.25f,-0.25f,0.25f,	
        0.25f,0.25f,0.25f,

        -0.25f,0.25f,-0.25f,	
        -0.25f,-0.25f,-0.25f,	
        -0.25f,-0.25f,0.25f,	
        -0.25f,0.25f,0.25f,

        -0.25f,0.25f,0.25f,
        -0.25f,0.25f,-0.25f,
        0.25f,0.25f,-0.25f,
        0.25f,0.25f,0.25f,

        -0.25f,-0.25f,0.25f,
        -0.25f,-0.25f,-0.25f,
        0.25f,-0.25f,-0.25f,
        0.25f,-0.25f,0.25f};

    int[] indices = {
        0,1,3,
        3,1,2
    };
    return loader.loadToVao(vertices, textureCoords, indices, colours);
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
    new Test().run();
    //		new Test().runGameState();
  }

  private void runGameState() {
    DisplayManager.createDisplay("Game State");

    while (!DisplayManager.shouldWindowClose()){
      glfwPollEvents();

      checkInput();

      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

      render();

      DisplayManager.updateDisplay();
    }
    DisplayManager.destroyDisplay();
  }

  private void render() {
    switch(state) {
    case INTRO:
      GL11.glColor3f(1.0f, 0f, 0f);
      GL11.glRectf(0, 0, 640, 480);
      break;
    case MAIN_MENU:
      showMainMenu();
      break;
    case GAME:	
      GL11.glColor3f(0f, 0f, 1.0f);
      GL11.glRectf(0, 0, 640, 480);
      break;
    }
  }

  private void checkInput(){
    switch(state) {
    case INTRO:
      if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_ENTER) == GLFW_TRUE) {
        state = State.MAIN_MENU;
      }
      if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_ESCAPE) == GLFW_TRUE) {
        GLFW.glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
      }
      break;
    case MAIN_MENU:
      if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_ENTER) == GLFW_TRUE) {
        state = State.GAME;
      }
      if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_ESCAPE) == GLFW_TRUE) {
        GLFW.glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
      }
      break;
    case GAME:	
      if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_BACKSPACE) == GLFW_TRUE) {
        state = State.MAIN_MENU;
      }
      if (glfwGetKey(DisplayManager.getWindow(), GLFW_KEY_ESCAPE) == GLFW_TRUE) {
        GLFW.glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
      }
      break;
    }
  }
}
