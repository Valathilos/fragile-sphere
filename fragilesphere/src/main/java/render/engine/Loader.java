package render.engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import model.RawModel;
import textures.Texture;

public class Loader {

  private List<Integer> vaos = new ArrayList<Integer>();
  private List<Integer> vbos = new ArrayList<Integer>();
  private List<Integer> textures = new ArrayList<Integer>();

  public RawModel loadToVao(float[] positions, float[] textureCoords, int[] indices) {
    int vaoId = createVAO();
    bindIndicesBuffer(indices);
    storeDataInAttributeList(0, 3, positions);
    storeDataInAttributeList(1, 2, textureCoords);
    unbindVAO();
    return new RawModel(vaoId, indices.length);
  }

  public RawModel loadToVao(float[] positions, float[] textureCoords, int[] indices, float[] colours) {
    int vaoId = createVAO();
    bindIndicesBuffer(indices);
    storeDataInAttributeList(0, 3, positions);
    storeDataInAttributeList(1, 3, colours);
    storeDataInAttributeList(2, 2, textureCoords);
    unbindVAO();
    return new RawModel(vaoId, indices.length);
  }

  public int loadToVao(float[] positions, float[] textureCoords) {
    int vaoId = createVAO();
    storeDataInAttributeList(0, 2, positions);
    storeDataInAttributeList(1, 2, textureCoords);
    unbindVAO();
    return vaoId;
  }

  public RawModel loadUntexturedModelToVao(float[] positions, int[] indices, float[] colours) {
    int vaoId = createVAO();
    bindIndicesBuffer(indices);
    storeDataInAttributeList(0, 3, positions);
//    storeDataInAttributeList(1, 3, indices);
    storeDataInAttributeList(2, 3, colours);

    unbindVAO();
    return new RawModel(vaoId, indices.length);
  }

  public RawModel loadToVao(float[] positions) {
    int vaoId = createVAO();
    storeDataInAttributeList(0, 2, positions);
    unbindVAO();
    return new RawModel(vaoId, positions.length/2);
  }

  public int loadTexture(String fileName) {
    Texture texture = new Texture(fileName);
    textures.add(texture.getTextureId());
    return texture.getTextureId();
  }

  private int createVAO() {
    int vaoId = GL30.glGenVertexArrays();
    vaos.add(vaoId);
    GL30.glBindVertexArray(vaoId);

    return vaoId;
  }

  private void bindIndicesBuffer(int[] indices) {
    int vboId = GL15.glGenBuffers();
    vbos.add(vboId);
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
    IntBuffer buffer = storeDataInIntBuffer(indices);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
  }

  private IntBuffer storeDataInIntBuffer(int[] data) {
    IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public void cleanUp() {
    for (int vao : vaos) {
      GL30.glDeleteVertexArrays(vao);
    }

    for (int vbo : vbos) {
      GL15.glDeleteBuffers(vbo);
    }

    for (int texture : textures) {
      GL11.glDeleteTextures(texture);
    }
  }

  private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data){
    int vboId = GL15.glGenBuffers();
    vbos.add(vboId);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
    FloatBuffer buffer = storeDataInFloatBuffer(data);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
  }

  public void unbindVAO() {
    GL30.glBindVertexArray(0);
  }

  private FloatBuffer storeDataInFloatBuffer(float[] data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
}
