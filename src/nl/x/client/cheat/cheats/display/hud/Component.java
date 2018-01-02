package nl.x.client.cheat.cheats.display.hud;

import net.minecraft.client.Minecraft;

/**
 * @author NullEX
 *
 */
public abstract class Component {
	public Minecraft mc = Minecraft.getMinecraft();

	public abstract void render();
}
