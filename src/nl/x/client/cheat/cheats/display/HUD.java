package nl.x.client.cheat.cheats.display;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.Gui;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventHUD;
import nl.x.client.Client;
import nl.x.client.cheat.cheats.display.hud.Component;
import nl.x.client.cheat.cheats.display.hud.impl.ArmorHudComp;
import nl.x.client.cheat.cheats.display.hud.impl.ArrayListComp;

/**
 * @author NullEX
 *
 */
@Info(name = "HUD", category = Category.Display, enabled = true)
public class HUD extends Cheat {
	public ArrayList<Component> components = Lists.newArrayList(new ArrayListComp(), new ArmorHudComp());

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventHUD) {
			EventHUD event = (EventHUD) e;
			String version = Client.info.get("version");
			String render = Client.info.get("name") + (version.contains(".") ? " v" : " b") + version;
			Gui.drawRect(0, 0, mc.fontRendererObj.getStringWidth(render + " "), 12, new Color(0, 0, 0, 200).getRGB());
			mc.fontRendererObj.drawString(render, 1, 2, -1);
			mc.fontRendererObj.drawString("Branch - " + Client.info.get("dev"),
					Display.getWidth() / 2
							- mc.fontRendererObj.getStringWidth("Branch - " + Client.info.get("dev") + " "),
					Display.getHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT, -1);
			this.components.forEach(Component::render);
			Client.INSTANCE.tabFactory.render();
		}
		super.onEvent(e);
	}

}
