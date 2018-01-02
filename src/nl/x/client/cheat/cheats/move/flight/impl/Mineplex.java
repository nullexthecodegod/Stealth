package nl.x.client.cheat.cheats.move.flight.impl;

import net.minecraft.util.MovementInput;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.entity.player.MoveUtils;
import nl.x.api.utils.misc.Timer;
import nl.x.client.cheat.cheats.move.Flight;
import nl.x.client.cheat.cheats.move.flight.FlightMode;

/**
 * @author NullEX
 *
 */
public class Mineplex extends FlightMode {
	private Timer timer = new Timer();

	/**
	 * @param parent
	 */
	public Mineplex(Flight parent) {
		super("Flight", parent);
	}

	/*
	 * @see
	 * nl.x.client.cheat.cheats.move.flight.FlightMode#onEvent(nl.x.api.event.
	 * Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			int time = 0;
			if (!mc.thePlayer.onGround) {
				double multiplier = 0.4873D;
				double mx1 = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0F));
				double mz1 = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0F));
				double x1 = MovementInput.moveForward * multiplier * mx1 + MovementInput.moveStrafe * multiplier * mz1;
				double z1 = MovementInput.moveForward * multiplier * mz1 - MovementInput.moveStrafe * multiplier * mx1;

				mc.timer.timerSpeed = 0.58F;
				mc.thePlayer.onGround = true;
				mc.thePlayer.motionY = 0.00;
				if (this.timer.hasPassed(3000)) {
					((EventUpdate) e).setY(-40);
					this.timer.reset();
				}
				mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX + x1, mc.thePlayer.posY, mc.thePlayer.posZ + z1);
				MoveUtils.setSpeed(1.5);
			}
		}
		super.onEvent(e);
	}

}
