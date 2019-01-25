package guis.buttons;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import managers.DisplayManager;
import render.Loader;

public class ExitButton extends AbstractButton {

//	private GUIText text;

	public ExitButton(Loader loader, String texture, Vector2f position, Vector2f scale) {
		super(loader, "textures/" + texture, position, scale);
//		FontType font = new FontType(loader.loadTexture("resources/fonts/calibri.png"), new File("resources/fonts/calibri.fnt"));
//		text = new GUIText("Exit", 1, font, position, 1f, true);
		
	}

	@Override
	public void onClick(IButton button) {
		GLFW.glfwSetWindowShouldClose(DisplayManager.getWindow(), true);
	}

	@Override
	public void whileHovering(IButton button) {
		// TODO Auto-generated method stub

	}

}
