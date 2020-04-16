package render;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Shader {
  private int program;
  private int vs;
  private int fs;

  public Shader(String fileName){
    program = glCreateProgram();

    vs = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vs, readFile(fileName + ".vs"));
    glCompileShader(vs);
    if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
      System.err.println(glGetShaderInfoLog(vs));
      System.exit(1);
    }

    fs = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fs, readFile(fileName + ".fs"));
    glCompileShader(fs);
    if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
      System.err.println(glGetShaderInfoLog(fs));
      System.exit(1);
    }

    glAttachShader(program, vs);
    glAttachShader(program, fs);

    glLinkProgram(program);
    if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
      System.err.println(glGetProgramInfoLog(program));
      System.exit(1);
    }
    glValidateProgram(program);
    if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
      System.err.println(glGetProgramInfoLog(program));
      System.exit(1);
    }

  }

  public void start() {
    glUseProgram(program);
  }

  public void stop() {
    glUseProgram(0);
  }

  public void cleanUp() {
    stop();
    glDetachShader(program, vs);
    glDetachShader(program, fs);
    glDeleteShader(vs);
    glDeleteShader(fs);
    glDeleteProgram(program);
  }

  public void setUniform(String name, int value) {
    int location = glGetUniformLocation(program, name);

    if (location != -1) {
      glUniform1i(location, value);
    }
  }

  public void setUniform(String name, Vector3f value) {
    int location = glGetUniformLocation(program, name);

    if (location != -1) {
      glUniform3f(location, value.x, value.y, value.z);
    }
  }

  public void setUniform(String name, Vector2f value) {
    int location = glGetUniformLocation(program, name);

    if (location != -1) {
      glUniform2f(location, value.x, value.y);
    }
  }

  public void setUniform(String name, float value) {
    int location = glGetUniformLocation(program, name);

    if (location != -1) {
      glUniform1f(location, value);
    }
  }

  public void setUniform(String name, Matrix4f value) {
    int location = glGetUniformLocation(program, name);

    if (location != -1) {
      FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
      value.get(buffer);
      //TODO: Fix bug here
      glUniformMatrix4fv(location, false, buffer);
    }
  }

  private String readFile(String fileName) {
    StringBuilder sb = new StringBuilder();
    BufferedReader br;

    try{
      InputStream stream = Shader.class.getClassLoader().getResourceAsStream("shaders/" + fileName);
      br = new BufferedReader(new InputStreamReader(stream));
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line).append("//\n");
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return sb.toString();
  }
}
