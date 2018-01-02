package nl.x.client.cheat.cheats.display.hud.impl;

import java.awt.Color;

import org.lwjgl.opengl.Display;

import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.Cheat;
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
		for (Cheat c : CheatManager.INSTANCE.buffer) {
			if (c.isEnabled() && c.isShown()) {
				int color = Color.getHSBColor(huee[0] / 255.0f, 0.6f, 0.9f).getRGB();
				String name = c.getName()
						+ (c.getSuffix().isEmpty() || c.getSuffix().length() <= 0 ? "" : "§7 - " + c.getSuffix());
				int x = Display.getWidth() / 2 - mc.fontRendererObj.getStringWidth(name);
				Gui.drawRect(x - 2, y - 2, Display.getWidth() / 2, y + mc.fontRendererObj.FONT_HEIGHT,
						new Color(0, 0, 0, 200).getRGB());
				mc.fontRendererObj.drawString(name, x, y, color);
				float[] arrf = huee;
				arrf[0] = arrf[0] + 9.0f;
				if (huee[0] > 255.0f) {
					huee[0] = huee[0] - 255.0f;
				}
				y += mc.fontRendererObj.FONT_HEIGHT + 2;
			}
		}
		float[] arrf = this.hue;
		arrf[0] = (float) (arrf[0] + 0.5);
		if (this.hue[0] > 255.0f) {
			this.hue[0] = this.hue[0] - 255.0f;
		}

	}

}
