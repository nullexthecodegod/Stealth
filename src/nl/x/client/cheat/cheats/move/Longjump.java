package nl.x.client.cheat.cheats.move;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.misc.Timer;

/**
 * @author NullEX
 *
 */
@Info(name = "Longjump", desc = "Makes you well... jump longer")
public class Longjump extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("New", "Gomme"), "New");
	private boolean shouldflag = false;
	public Timer timer = new Timer();

	public Longjump() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e;
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "new":
					// Broken aswell
					if (mc.thePlayer.hasInput()) {
						if (mc.thePlayer.onGround) {
							mc.thePlayer.jump();
							mc.thePlayer.motionY = 0.4;
						} else {
							double yaw = mc.thePlayer.rotationYawHead;
							if (mc.gameSettings.keyBindBack.pressed && !mc.gameSettings.keyBindForward.pressed) {
								yaw -= 180;
							}
							yaw = Math.toRadians(yaw);
							final double dX = -Math.sin(yaw) * 2.75;
							final double dZ = Math.cos(yaw) * 2.75;
							if (timer.hasPassed(40L)) {
								mc.thePlayer.motionX = dX * 0.65;
								mc.thePlayer.motionZ = dZ * 0.65;
								timer.reset();
							}
						}

					}
					break;
				case "gomme":
					if (mc.thePlayer.onGround) {
						mc.thePlayer.jump();

					}

					shouldflag = !shouldflag;
					// Broken need to fix it
					if (!mc.thePlayer.onGround) {

						double mX = mc.thePlayer.motionX;
						double mZ = mc.thePlayer.motionZ;
						if (shouldflag) {
							mc.thePlayer.motionX = -(mX / 6);
							mc.thePlayer.motionZ = -(mZ / 6);
						}
						if (timer.hasPassed(5l)) {
							mc.thePlayer.motionZ *= 1.55;
							mc.thePlayer.motionX *= 1.55;
							timer.reset();
						}
					}
					break;
			}
		}
		super.onEvent(e);
	}

}
