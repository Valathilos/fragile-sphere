package render.engine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entities.Entity;
import model.TexturedModel;
import render.Camera;
import render.Shader;
import toolbox.Maths;

public class Renderer {
  private static final float FOV = 70;
  private static final float NEAR_PLANE = 0.1f;
  private static final float FAR_PLANE = 500;

  protected Matrix4f projectionMatrix;

  public Renderer(Shader shader) {
    createProjectionMatrix();
    shader.start();
    shader.setUniform("projectionMatrix", projectionMatrix);
    shader.stop();
  }

  public void prepare() {
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);      
    GL11.glClearColor(1, 1, 1, 1);
  }

  public void render(Entity entity, Shader shader, Camera camera) {
    TexturedModel texturedModel = entity.getModel();
    RawModel model = texturedModel.getRawModel();

    shader.start();

    GL30.glBindVertexArray(model.getVaoId());
    GL20.glEnableVertexAttribArray(0);
    GL20.glEnableVertexAttribArray(1);
    //		GL20.glEnableVertexAttribArray(2);

    Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), 
        entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());

    shader.setUniform("transformationMatrix", transformationMatrix);
    shader.setUniform("viewMatrix", Maths.createViewMatrix(camera));

    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getId());
    GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    GL20.glDisableVertexAttribArray(0);
    GL20.glDisableVertexAttribArray(1);
    //		GL20.glDisableVertexAttribArray(2);
    GL30.glBindVertexArray(0);

    shader.stop();
  }

  private void createProjectionMatrix() {
    int width = DisplayManager.getWidth();
    int height = DisplayManager.getHeight();

    float aspectRatio = (float) width / (float) height;
    float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
    float x_scale = y_scale / aspectRatio;
    float frustum_length = FAR_PLANE - NEAR_PLANE;

    projectionMatrix = new Matrix4f();
    projectionMatrix.m00(x_scale);
    projectionMatrix.m11(y_scale);
    projectionMatrix.m22(-((FAR_PLANE - NEAR_PLANE) / frustum_length));
    projectionMatrix.m23(-1);
    projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
    projectionMatrix.m33(0);
  }
}
