package nl.x.client.cheat.cheats.fight;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.event.impl.EventVelocity;

/**
 * @author NullEX
 *
 */
@Info(name = "Velocity")
public class Velocity extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("NCP", "AAC"), "NCP");
	public BooleanValue smart = new BooleanValue("Smart", true);

	public Velocity() {
		this.addValue(this.mode, this.smart);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventVelocity) {
			if (this.smart.getValue() ? mc.thePlayer.hurtTime != 0 : true) {
				switch (this.mode.getValue().toString().toLowerCase()) {
					case "aac":
						mc.thePlayer.setSpeed(0.1);
						break;
					case "ncp":
						((EventVelocity) e).setCanceled(true);
						break;
				}
			}
		}
		if (e instanceof EventUpdate) {
			this.setSuffix(this.mode.getValue() + (this.smart.getValue() ? " | Smart" : ""));
		}
		if (e instanceof EventPacket) {

		}
		super.onEvent(e);
	}

}
