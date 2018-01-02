package nl.x.client.cheat.cheats.move.flight.impl;

import net.minecraft.network.play.client.C03PacketPlayer;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;
import nl.x.client.cheat.cheats.move.Flight;
import nl.x.client.cheat.cheats.move.flight.FlightMode;

/**
 * @author NullEX
 *
 */
public class Test extends FlightMode {

	/**
	 * @param parent
	 */
	public Test(Flight parent) {
		super("Test", parent);
	}

	/*
	 * @see
	 * nl.x.client.cheat.cheats.move.flight.FlightMode#onEvent(nl.x.api.event.
	 * Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if (!mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0;
			}

			mc.thePlayer.motionY = -0.001;

			if (mc.thePlayer.ticksExisted % 4 == 0) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,
						mc.thePlayer.posY, mc.thePlayer.posZ, ((EventUpdate) e).getYaw(), ((EventUpdate) e).getPitch(),
						((EventUpdate) e).isOnGround()));
			}
		}
		super.onEvent(e);
	}

}
