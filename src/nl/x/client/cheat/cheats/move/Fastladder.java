package nl.x.client.cheat.cheats.move;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventMove;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "Fastladder")
public class Fastladder extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("NCP", "AAC"), "NCP");

	public Fastladder() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			this.setSuffix(this.mode.getValue().toString());
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "ncp":
					break;
				case "aac":
					if (mc.thePlayer.hasInput() && mc.thePlayer.isOnLadder() && !mc.thePlayer.isSneaking()) {
						mc.thePlayer.motionY = 0.23;
					}
					break;
			}
		}
		if (e instanceof EventPacket) {
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "ncp":
					break;
				case "aac":
					break;
			}

		}
		if (e instanceof EventMove) {
			EventMove event = (EventMove) e;
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "ncp":
					if (event.y > 0.0 && mc.thePlayer.isOnLadder()) {
						event.y *= 2.5;
					}
					break;
			}
		}
		super.onEvent(e);
	}

}
