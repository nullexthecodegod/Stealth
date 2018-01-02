package nl.x.client.cheat.cheats.move.step.impl;

import nl.x.api.event.Event;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventStep;
import nl.x.api.event.impl.EventUpdate;
import nl.x.client.cheat.cheats.move.Step;
import nl.x.client.cheat.cheats.move.step.StepMode;

/**
 * @author NullEX
 *
 */
public class AAC extends StepMode {

	/**
	 * @param parent
	 */
	public AAC(Step parent) {
		super("AAC", parent);
	}

	/*
	 * @see
	 * nl.x.client.cheat.cheats.move.step.StepMode#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventStep) {

		}
		if (e instanceof EventUpdate) {

		}
		if (e instanceof EventPacket) {

		}
		super.onEvent(e);
	}

}
