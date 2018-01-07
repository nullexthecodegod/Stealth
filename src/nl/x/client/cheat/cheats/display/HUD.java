package nl.x.client.cheat.cheats.display;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
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
@Info(name = "HUD", enabled = true, shown = false)
public class HUD extends Cheat {
	public ArrayList<Component> components = Lists.newArrayList(new ArrayListComp(), new ArmorHudComp());

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventHUD) {
			EventHUD event = (EventHUD) e;
			if (!mc.gameSettings.showDebugInfo) {
				String version = Client.info.get("version");
				String render = Client.info.get("name") + (version.contains(".") ? " v" : " b") + version;
				mc.fontRendererObj.drawString(render, 1, 2, -1);
				this.components.forEach(Component::render);
				Client.INSTANCE.tab.render();
			}
		}
		super.onEvent(e);
	}

}
