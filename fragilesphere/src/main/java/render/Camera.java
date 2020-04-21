package render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Camera {
  private static final Logger LOGGER = LoggerFactory.getLogger(Camera.class);

  private Vector3f position;
  private float pitch;
  private float yaw;
  private float roll;

  private Matrix4f projection;
  private long window;

  public Camera(long window) {
    this.window = window;
    position = new Vector3f(0.0f, 0, 0);
  }

  public void move() {
    //		LOGGER.debug("Camera pos: {}", position.toString());
    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_TRUE) {
      position.z-=0.02f;
    }    
    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_TRUE) {
      //			position.z+=0.02f;
      position.z+=1.0f;
    }
    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_TRUE) {
      position.x+=0.02f;
    }
    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_TRUE) {
      position.x-=0.02f;
    }
    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_Q) == GLFW.GLFW_TRUE) {
      position.y-=0.02f;
    }
    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_E) == GLFW.GLFW_TRUE) {
      position.y+=0.02f;
    }
    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_TRUE) {
      yaw+=0.02f;
    }
    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_TRUE) {
      yaw-=0.02f;
    }
  }

  public Vector3f getPosition() {
    return position;
  }

  public float getPitch() {
    return pitch;
  }

  public float getYaw() {
    return yaw;
  }

  public float getRoll() {
    return roll;
  }

  public Matrix4f getProjection() {
    return projection;
  }


  //	public Camera (int width, int height) {
  //		position = new Vector3f(0,0,0);
  ////		projection = new Matrix4f().setOrtho2D(left, right, bottom, top);
  //	}
}
