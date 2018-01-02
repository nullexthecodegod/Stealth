package nl.x.client.cheat.cheats.move.flight.impl;

import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.entity.player.MoveUtils;
import nl.x.client.cheat.cheats.move.Flight;
import nl.x.client.cheat.cheats.move.flight.FlightMode;

/**
 * @author NullEX
 *
 */
public class Normal extends FlightMode {

	/**
	 * @param parent
	 */
	public Normal(Flight parent) {
		super("Normal", parent);
	}

	/*
	 * @see
	 * nl.x.client.cheat.cheats.move.flight.FlightMode#onEvent(nl.x.api.event.
	 * Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			double speed = 1.0;
			mc.thePlayer.onGround = true;
			if (mc.thePlayer.movementInput.jump) {
				mc.thePlayer.motionY = speed;
			} else if (mc.thePlayer.movementInput.sneak) {
				mc.thePlayer.motionY = -speed;
			} else {
				mc.thePlayer.motionY = 0.0;
			}
			MoveUtils.setSpeed(speed);
		}
		super.onEvent(e);
	}

}
