package guis.menus;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import guis.buttons.AbstractButton;
import guis.buttons.ExitButton;
import guis.buttons.StartButton;
import render.Loader;

public class MainMenu {

	private boolean isHidden = true;
	private List<AbstractButton> buttons = new ArrayList<AbstractButton>();
	private List<GuiTexture> textures = new ArrayList<GuiTexture>();
	
	private GuiRenderer renderer;
	
	public MainMenu() {
		Loader loader = new Loader();
		TextMaster.init(loader);

		textures.add( new GuiTexture(loader.loadTexture("textures/megamek-splash.gif"), new Vector2f(0.0f, 0.0f), new Vector2f(1f, 1f)));
		
		renderer = new GuiRenderer(loader);
		buttons.add(new StartButton(loader, "adder_d.gif", new Vector2f(0.0f, 0.4f), new Vector2f(0.05f, 0.05f)));
		buttons.add(new ExitButton(loader, "battle_armor.gif", new Vector2f(0.0f, 0.2f), new Vector2f(0.05f, 0.05f)));
		buttons.add(new ExitButton(loader, "cavhooah.jpg", new Vector2f(0.0f, -0.2f), new Vector2f(0.05f, 0.05f)));
		buttons.add(new ExitButton(loader, "grave_diggers.jpg", new Vector2f(0.0f, -0.4f), new Vector2f(0.05f, 0.05f)));
	}
	
	public boolean isHidden() {
		return isHidden;
	}

	public void show() {
		if (isHidden) {
			isHidden = false;
			for (AbstractButton button : buttons) {
				button.show(textures);
			}
		}
	}

	public void hide() {
		if (!isHidden) {
			isHidden = true;
			for (AbstractButton button : buttons) {
				button.hide(textures);
			}
		}
	}
	
	public void update(){
		for (AbstractButton button : buttons) {
			button.update();
		}
		renderer.render(textures);
	}

	public void cleanUp() {
		renderer.cleanUp();
		
	}
	
}
