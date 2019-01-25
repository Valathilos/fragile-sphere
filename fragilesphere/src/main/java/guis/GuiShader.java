package guis;

import org.joml.Matrix4f;

import shaders.ShaderProgram;

public class GuiShader extends ShaderProgram{
    
    private static final String VERTEX_FILE = "shaders/gui/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "shaders/gui/guiFragmentShader.txt";
     
    private int location_transformationMatrix;
 
    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    public void setUniform(String name, Matrix4f matrix) {
	// TODO Auto-generated method stub
	
    }
     
     
     
 
}
