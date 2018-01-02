package nl.x.client.cheat.cheats.move.flight;

import net.minecraft.client.Minecraft;
import nl.x.api.event.Event;
import nl.x.client.cheat.cheats.move.Flight;

/**
 * @author NullEX
 *
 */
public class FlightMode {
	public Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private Flight parent;

	/**
	 * @param name
	 * @param parent
	 */
	public FlightMode(String name, Flight parent) {
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
	public Flight getParent() {
		return parent;
	}

}
