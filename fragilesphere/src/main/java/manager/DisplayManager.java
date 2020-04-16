package manager;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class DisplayManager {
  private static GLFWVidMode vidMode;
  private static long win;
  private static GLFWCursorPosCallback mouseCallback;
  private static int mouseY;
  private static int mouseX;
  
  private static int frames;
  private static long time;
  private static String title;


  public static void createDisplay(String windowTitle) {
    glfwSetErrorCallback(GLFWErrorCallback.createPrint());
    title = windowTitle;

    //initialise GLFW
    if (!glfwInit()) {
      throw new IllegalStateException("GLFW failed to initialise");
    }


    //Configure GLFW
    vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    int width = vidMode.width();
    int height = vidMode.height();

    win = glfwCreateWindow(width, height, windowTitle, 0, 0);

    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

    glfwSetCursorPosCallback(win, mouseCallback = new GLFWCursorPosCallback(){

      @Override
      public void invoke(long window, double xPos, double yPos) {
        mouseX = (int) xPos;
        mouseY = (int) yPos;
      }
    });

    glfwMakeContextCurrent(win);

    //enable v-sync
    glfwSwapInterval(1);

    glfwShowWindow(win);

    GL.createCapabilities();
    
    time = System.currentTimeMillis();
  }

  public static void destroyDisplay() {
    mouseCallback.free();
    glfwDestroyWindow(win);
    glfwTerminate();
  }

  public static int getWidth() {
    return vidMode.width();
  }

  public static int getHeight() {
    return vidMode.height();
  }

  public static Vector2f getNormalizedMouseCoords() {
    float normalisedX = -1.0f + 2.0f * (float) mouseX / (float) getWidth();
    float normalisedY = -1.0f + 2.0f * (float) mouseY / (float) getHeight();

    return new Vector2f(normalisedX, normalisedY);
  }

  public static int getMouseX() {
    return mouseX;
  }

  public static int getMouseY() {
    return mouseY;
  }

  public static boolean isMouseButtonDown(int button_id) {
    if (button_id  == 0)
      return (GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS);
    if (button_id  == 1)
      return (GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS);
    if (button_id  == 2)
      return (GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_3) == GLFW.GLFW_PRESS);
    return false;
  }

  public static long getWindow() {
    return win;
  }

  public static boolean shouldWindowClose() {
    return GLFW.glfwWindowShouldClose(win);
  }

  public static void updateDisplay() {
    glfwSwapBuffers(win);
    
    frames++;
    if (System.currentTimeMillis() > time + 1000) {
      glfwSetWindowTitle(win, title + " (FPS: " + frames +")" );
      time = System.currentTimeMillis();
      frames = 0;        
    }
  }
}
