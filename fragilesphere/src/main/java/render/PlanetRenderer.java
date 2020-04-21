package render;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.Entity;
import model.Planet;
import model.RawModel;
import model.TexturedModel;
import render.engine.DisplayManager;
import render.engine.Renderer;
import toolbox.Maths;

public class PlanetRenderer extends Renderer {
  private static final Logger LOGGER = LoggerFactory.getLogger(PlanetRenderer.class);

  public PlanetRenderer(Shader shader) {
    super(shader);
  }
  
  public void prepare() {
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);		
    GL11.glClearColor(0, 0, 0, 1);
  }

  public void render(Entity entity, Shader shader, Camera camera) {
    GL11.glPointSize(5.0f);

    TexturedModel texturedModel = entity.getModel();
    RawModel model = texturedModel.getRawModel();
//    LOGGER.debug("{} : {}", ((Planet)entity).getName(), model.getVaoId());

    shader.start();

    GL30.glBindVertexArray(model.getVaoId());
    GL20.glEnableVertexAttribArray(0);
    GL20.glEnableVertexAttribArray(1);
//    GL20.glEnableVertexAttribArray(2);

    Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), 
        entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
    
    shader.loadTransformationMatrix(transformationMatrix);

    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getId());
    
//
    Matrix4f viewMatrix = Maths.createViewMatrix(camera);
//
    shader.setUniform("transformationMatrix", transformationMatrix);
//
    shader.setUniform("viewMatrix", viewMatrix );



    GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    GL20.glDisableVertexAttribArray(0);
    GL20.glDisableVertexAttribArray(1);
//    GL20.glDisableVertexAttribArray(2);
    GL30.glBindVertexArray(0);

    shader.stop();
  }  
}
