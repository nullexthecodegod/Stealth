package nl.x.client.cheat.cheats.player.scaffold;

import net.minecraft.client.Minecraft;
import nl.x.api.event.Event;
import nl.x.client.cheat.cheats.player.Scaffold;

/**
 * @author NullEX
 *
 */
public class ScaffoldMode {
	public Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private Scaffold parent;

	/**
	 * @param name
	 * @param parent
	 */
	public ScaffoldMode(String name, Scaffold parent) {
		this.name = name;
		this.parent = parent;
	}

	/**
	 * 
	 * @param e
	 */
	public void onEvent(Event e) {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the parent
	 */
	public Scaffold getParent() {
		return parent;
	}
}
