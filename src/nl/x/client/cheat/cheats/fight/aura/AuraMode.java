package nl.x.client.cheat.cheats.fight.aura;

import net.minecraft.client.Minecraft;
import nl.x.api.event.Event;
import nl.x.client.cheat.cheats.fight.Aura;

/**
 * @author NullEX
 *
 */
public class AuraMode {
	public Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private Aura parent;

	/**
	 * @param name
	 * @param parent
	 */
	public AuraMode(String name, Aura parent) {
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
	public Aura getParent() {
		return parent;
	}

}
