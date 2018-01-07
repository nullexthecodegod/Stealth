package nl.x.client.cheat.cheats.display.hud.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.Cheat;
import nl.x.client.Client;
import nl.x.client.cheat.CheatManager;
import nl.x.client.cheat.cheats.display.hud.Component;

/**
 * @author NullEX
 *
 */
public class ArrayListComp extends Component {
	private float[] hue = new float[] { 0.0f };

	/*
	 * @see nl.x.client.cheat.cheats.display.hud.Component#render()
	 */
	@Override
	public void render() {
		int y = 2;
		float[] huee = new float[] { this.hue[0] };
		for (int i = 0; i < this.getRenderMods().size(); i++) {
			Cheat c = this.getRenderMods().get(i);
			int color = Color.getHSBColor(huee[0] / 255.0f, 0.6f, 0.9f).getRGB();
			String name = c.getName()
					+ (c.getSuffix().isEmpty() || c.getSuffix().length() <= 0 ? "" : "§7 - " + c.getSuffix());
			int x = Display.getWidth() / 2 - Client.INSTANCE.tahoma.getStringWidth(name);
			if (this.getRenderMods().size() > 1) {
				int other = (i + 1 < this.getRenderMods().size() ? i + 1 : i);
				if (this.getRenderMods().get(other) != null) {
					Cheat c2 = this.getRenderMods().get(other);
					String name2 = c2.getName() + (c2.getSuffix().isEmpty() || c2.getSuffix().length() <= 0 ? ""
							: "§7 - " + c2.getSuffix());
					Gui.drawRect(x - 2, y + Client.INSTANCE.tahoma.getStringHeight(name),
							Display.getWidth() / 2 - (other == i ? 0 : Client.INSTANCE.tahoma.getStringWidth(name2)),
							y + Client.INSTANCE.tahoma.getStringHeight(name) + 1, color);
				}
			}
			Gui.drawRect(x - 2, y - 2, Display.getWidth() / 2, y + Client.INSTANCE.tahoma.getStringHeight(name),
					new Color(0, 0, 0, 200).getRGB());
			Gui.drawRect(x - 2, y - 2, x - 1, y + Client.INSTANCE.tahoma.getStringHeight(name), color);
			Client.INSTANCE.tahoma.drawString(name, x, y, color);
			float[] arrf = huee;
			arrf[0] = arrf[0] + 9.0f;
			if (huee[0] > 255.0f) {
				huee[0] = huee[0] - 255.0f;
			}
			y += Client.INSTANCE.tahoma.getStringHeight(name) + 2;
		}
		float[] arrf = this.hue;
		arrf[0] = (float) (arrf[0] + 0.5);
		if (this.hue[0] > 255.0f) {
			this.hue[0] = this.hue[0] - 255.0f;
		}

	}

	private List<Cheat> getRenderMods() {
		ArrayList<Cheat> psuedoMods = new ArrayList<Cheat>();
		for (Cheat c : CheatManager.INSTANCE.buffer) {
			if (!c.isEnabled() || !c.isShown())
				continue;
			psuedoMods.add(c);
		}
		psuedoMods.sort((c1, c2) -> {
			String name = c1.getName()
					+ (c1.getSuffix().isEmpty() || c1.getSuffix().length() <= 0 ? "" : "§7 - " + c1.getSuffix());
			String name2 = c2.getName()
					+ (c2.getSuffix().isEmpty() || c2.getSuffix().length() <= 0 ? "" : "§7 - " + c2.getSuffix());
			if (Client.INSTANCE.tahoma.getStringWidth(name) > Client.INSTANCE.tahoma.getStringWidth(name2)) {
				return -1;
			}
			if (Client.INSTANCE.tahoma.getStringWidth(name) == Client.INSTANCE.tahoma.getStringWidth(name2)) {
				return 0;
			}
			return 1;
		});
		return psuedoMods;
	}

}
