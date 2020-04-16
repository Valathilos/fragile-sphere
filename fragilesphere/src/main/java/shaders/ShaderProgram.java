package shaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ShaderProgram {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShaderProgram.class);

  private int programId;
  private int vertexShaderId;
  private int fragmentShaderId;

  private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

  public ShaderProgram(String vertexFile, String fragmentFile){
    vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
    fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
    programId = GL20.glCreateProgram();
    GL20.glAttachShader(programId, vertexShaderId);
    GL20.glAttachShader(programId, fragmentShaderId);
    GL20.glLinkProgram(programId);
    GL20.glValidateProgram(programId);
    bindAttributes();
    getAllUniformLocations();
  }

  protected abstract void getAllUniformLocations();

  protected int getUniformLocation(String uniformName){
    return GL20.glGetUniformLocation(programId,uniformName);
  }

  public void start(){
    GL20.glUseProgram(programId);
  }

  public void stop(){
    GL20.glUseProgram(0);
  }

  public void cleanUp(){
    stop();
    GL20.glDetachShader(programId, vertexShaderId);
    GL20.glDetachShader(programId, fragmentShaderId);
    GL20.glDeleteShader(vertexShaderId);
    GL20.glDeleteShader(fragmentShaderId);
    GL20.glDeleteProgram(programId);
  }

  protected abstract void bindAttributes();

  protected void bindAttribute(int attribute, String variableName){
    GL20.glBindAttribLocation(programId, attribute, variableName);
  }

  protected void loadFloat(int location, float value){
    GL20.glUniform1f(location, value);
  }

  protected void loadVector(int location, Vector3f vector){
    GL20.glUniform3f(location,vector.x,vector.y,vector.z);
  }

  protected void loadBoolean(int location, boolean value){
    float toLoad = 0;
    if(value){
      toLoad = 1;
    }
    GL20.glUniform1f(location, toLoad);
  }

  protected void loadMatrix(int location, Matrix4f matrix){
    matrix.store(matrixBuffer);
    matrixBuffer.flip();
    GL20.glUniformMatrix4fv(location, false, matrixBuffer);
  }

  private static int loadShader(String file, int type){
    StringBuilder shaderSource = new StringBuilder();
    try{
      InputStream stream = ClassLoader.getSystemResourceAsStream(file);
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      String line;
      while((line = reader.readLine()) != null){
        shaderSource.append(line).append("//\n");
      }
      reader.close();
    }catch(IOException e){
      LOGGER.error("Problem loading Shader: " + file, e);
      System.exit(-1);
    }
    
    int shaderID = GL20.glCreateShader(type);
    GL20.glShaderSource(shaderID, shaderSource);
    GL20.glCompileShader(shaderID);
    if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
      LOGGER.error("Could not compile shader!: " + GL20.glGetShaderInfoLog(shaderID, 500));
      System.exit(-1);
    }
    return shaderID;
  }

}