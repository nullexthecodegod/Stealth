package nl.x.api.event.impl;

import nl.x.api.event.Event;
import nl.x.client.cheat.CheatManager;

/**
 * @author NullEX
 *
 */
public class EventAlways extends Event {

	public void run() {
		CheatManager.INSTANCE.buffer.forEach(c -> c.onEvent(this));
	}

}
