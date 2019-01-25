package guis.buttons;

import java.util.List;

import guis.GuiTexture;

public interface IButton {
	void onClick(IButton button);
	
	void onStartHover(IButton button);
	
	void onStopHover(IButton button);
	
	void whileHovering(IButton button);
	
	void show(List<GuiTexture> textures);
	
	void hide(List<GuiTexture> textures);
	
	void playHoverAnimation(float scaleFactor);
	
	void resetScale();
	
	void update();
}
