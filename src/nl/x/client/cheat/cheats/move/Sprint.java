package nl.x.client.cheat.cheats.move;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "Sprint")
public class Sprint extends Cheat {

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if (mc.thePlayer.moveForward != 0.0 || mc.thePlayer.moveStrafing != 0.0) {
				mc.thePlayer.setSprinting(true);
			}
		}
		super.onEvent(e);
	}

}
