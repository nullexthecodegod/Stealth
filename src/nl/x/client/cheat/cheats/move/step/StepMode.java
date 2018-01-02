package nl.x.client.cheat.cheats.move.step;

import net.minecraft.client.Minecraft;
import nl.x.api.event.Event;
import nl.x.client.cheat.cheats.move.Step;

/**
 * @author NullEX
 *
 */
public class StepMode {
	public Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private Step parent;

	/**
	 * @param name
	 * @param parent
	 */
	public StepMode(String name, Step parent) {
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
	public Step getParent() {
		return parent;
	}
}
