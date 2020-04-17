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

public class Shader extends ShaderProgram {
  private static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);
  
  private int location_transformationMatrix;
  private int location_projectionMatrix;
  private int location_viewMatrix;

  public Shader(String fileName){
    super(Shader.class.getClassLoader().getResourceAsStream("shaders/" + fileName + ".vs"), 
        Shader.class.getClassLoader().getResourceAsStream("shaders/" + fileName + ".fs"));
    
    //    program = glCreateProgram();
    //
    //    vs = glCreateShader(GL_VERTEX_SHADER);
    //    glShaderSource(vs, readFile(fileName + ".vs"));
    //    glCompileShader(vs);
    //    if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
    //      System.err.println(glGetShaderInfoLog(vs));
    //      System.exit(1);
    //    }
    //
    //    fs = glCreateShader(GL_FRAGMENT_SHADER);
    //    glShaderSource(fs, readFile(fileName + ".fs"));
    //    glCompileShader(fs);
    //    if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
    //      System.err.println(glGetShaderInfoLog(fs));
    //      System.exit(1);
    //    }
    //
    //    glAttachShader(program, vs);
    //    glAttachShader(program, fs);
    //
    //    glLinkProgram(program);
    //    if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
    //      System.err.println(glGetProgramInfoLog(program));
    //      System.exit(1);
    //    }
    //    glValidateProgram(program);
    //    if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
    //      System.err.println(glGetProgramInfoLog(program));
    //      System.exit(1);
    //    }

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
    super.bindAttribute(1, "textureCoordinates");
  }

  //  public void start() {
  //    glUseProgram(program);
  //  }
  //
  //  public void stop() {
  //    glUseProgram(0);
  //  }
  //
  //  public void cleanUp() {
  //    stop();
  //    glDetachShader(program, vs);
  //    glDetachShader(program, fs);
  //    glDeleteShader(vs);
  //    glDeleteShader(fs);
  //    glDeleteProgram(program);
  //  }
  //
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

  //  private String readFile(String fileName) {
  //    StringBuilder sb = new StringBuilder();
  //    BufferedReader br;
  //
  //    try{
  //      InputStream stream = Shader.class.getClassLoader().getResourceAsStream("shaders/" + fileName);
  //      br = new BufferedReader(new InputStreamReader(stream));
  //      String line;
  //      while ((line = br.readLine()) != null) {
  //        sb.append(line).append("//\n");
  //      }
  //      br.close();
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //
  //    return sb.toString();
  //  }
}
