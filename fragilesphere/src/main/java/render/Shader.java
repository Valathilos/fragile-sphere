package render;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import shaders.ShaderProgram;
import toolbox.Maths;

public class Shader extends ShaderProgram {
  private static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

  private int location_transformationMatrix;
  private int location_projectionMatrix;
  private int location_viewMatrix;

  public Shader(String fileName){
    super(Shader.class.getClassLoader().getResourceAsStream("shaders/" + fileName + ".vs"), 
        Shader.class.getClassLoader().getResourceAsStream("shaders/" + fileName + ".fs"));
  }

  @Override
  protected void getAllUniformLocations() {
    location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    location_projectionMatrix = super.getUniformLocation("projectionMatrix");
    location_viewMatrix = super.getUniformLocation("viewMatrix");
  }

  @Override
  protected void bindAttributes() {
    super.bindAttribute(0, "position");
    super.bindAttribute(1, "textureCoords");
  }

  public void setUniform(String name, int value) {
    int location = getUniformLocation(name);

    if (location != -1) {
      GL20.glUniform1i(location, value);
    }
  }

  public void setUniform(String name, Vector3f value) {
    int location = getUniformLocation(name);

    if (location != -1) {
      GL20.glUniform3f(location, value.x, value.y, value.z);
    }
  }

  public void setUniform(String name, Vector2f value) {
    int location = getUniformLocation(name);

    if (location != -1) {
      GL20.glUniform2f(location, value.x, value.y);
    }
  }

  public void setUniform(String name, float value) {
    int location = getUniformLocation(name);

    if (location != -1) {
      GL20.glUniform1f(location, value);
    }
  }

  public void setUniform(String name, Matrix4f value) {
    int location = getUniformLocation(name);

    if (location != -1) {
      FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
      value.get(buffer);
      //TODO: Fix bug here
      GL20.glUniformMatrix4fv(location, false, buffer);
    }
  }

  public void loadTransformationMatrix(Matrix4f matrix){
    super.loadMatrix(location_transformationMatrix, matrix);
  }

  public void loadProjectionMatrix(Matrix4f matrix){
    super.loadMatrix(location_projectionMatrix, matrix);
  }

  public void loadViewMatrix(Camera camera){
    super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
  }

}
