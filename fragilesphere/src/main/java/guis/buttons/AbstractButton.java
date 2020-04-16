package guis.buttons;

import java.util.List;

import org.joml.Vector2f;

import guis.GuiTexture;
import manager.DisplayManager;
import render.Loader;

public abstract class AbstractButton implements IButton {
	private GuiTexture guiTexture;
	
	private Vector2f originalScale;
	
	boolean isHidden = true;
	boolean isHovering = false;
	
	public AbstractButton(Loader loader, String texture, Vector2f position, Vector2f scale){
		guiTexture = new GuiTexture(loader.loadTexture(texture), position, scale);
		originalScale = scale;
	}
	
	public void update() {
		if (!isHidden){
			Vector2f location = guiTexture.getPosition();
			Vector2f scale = guiTexture.getScale();
			
			Vector2f mouseCoords = DisplayManager.getNormalizedMouseCoords();
			
			if (location.y + scale.y > -mouseCoords.y && location.y - scale.y < -mouseCoords.y && location.x + scale.x > mouseCoords.x && location.x - scale.x < mouseCoords.x) {
				whileHovering(this);
				if (!isHovering) {
					isHovering = true;
					onStartHover(this);
				}
				if (DisplayManager.isMouseButtonDown(0)){ ///LMB
 					onClick(this);
				}
	 		} else {
				if (isHovering) {
					isHovering = false;
					onStopHover(this);
				}
			}
		}
	}
	
	public void show(List<GuiTexture> textures) {
		if (isHidden) {
			textures.add(guiTexture);
			isHidden = false;
		}
	}
	
	public void hide(List<GuiTexture> textures) {
		if (!isHidden) {
			textures.remove(guiTexture);
			isHidden = true;
		}
	}
	
	public void playHoverAnimation(float scaleFactor){
		guiTexture.setScale(new Vector2f(originalScale.x + scaleFactor, originalScale.y + scaleFactor));
	}
	
	public void onStartHover(IButton button) {
		button.playHoverAnimation(0.042f);
		isHovering = true;
	}

	public void onStopHover(IButton button) {
		button.resetScale();
	}
	
	public void resetScale() {
		guiTexture.setScale(originalScale);
	}

	public boolean isHidden() {
		return isHidden;
	}

	public GuiTexture getTexture() {
		return guiTexture;
	}
}
