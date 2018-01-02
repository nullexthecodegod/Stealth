package nl.x.client.cheat.cheats.display.hud.impl;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.google.common.collect.Lists;

import nl.x.client.cheat.cheats.display.hud.Component;
import nl.x.client.cheat.cheats.display.hud.impl.armor.ArmorRender;

/**
 * @author NullEX
 *
 */
public class ArmorHudComp extends Component {
	public ArrayList<ArmorRender> comp = Lists.newArrayList();

	public ArmorHudComp() {
		int y = Display.getHeight() / 2;
		for (int index = 0; index < 4; ++index) {
			this.comp.add(new ArmorRender(y, index));
			y -= 20;
		}

	}

	/*
	 * @see nl.x.client.cheat.cheats.display.hud.Component#render()
	 */
	@Override
	public void render() {
		this.comp.forEach(c -> c.render());

	}

}
