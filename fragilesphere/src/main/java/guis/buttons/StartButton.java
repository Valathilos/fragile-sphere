package guis.buttons;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import render.engine.DisplayManager;
import render.engine.Loader;

public class StartButton extends AbstractButton {
//	private GUIText text;

	public StartButton(Loader loader, String texture, Vector2f position, Vector2f scale) {
		super(loader, "textures/" + texture, position, scale);
//		FontType font = new FontType(loader.loadTexture("fonts/calibri.png"), "fonts/calibri.fnt");
//		text = new GUIText("Start", 1, font, position, 1f, true);
		
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
