package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

//Our MouseHandler class extends the abstract class
//abstract classes should never be instantiated so here
//we create a concrete that we can instantiate
public class MouseHandler extends GLFWCursorPosCallback {
	
	private double x;
	private double y;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		// TODO Auto-generated method stub
		// this basically just prints out the X and Y coordinates 
		// of our mouse whenever it is in our window
		x = xpos;
		y = ypos;
//		System.out.println("X: " + xpos + " Y: " + ypos);
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public boolean next() {
		// TODO Auto-generated method stub
		return false;
	}
}