package nl.x.client.cheat.cheats.move.step.impl;

import net.minecraft.network.play.client.C03PacketPlayer;
import nl.x.api.event.Event;
import nl.x.api.event.Event.State;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventStep;
import nl.x.api.event.impl.EventTick;
import nl.x.api.event.impl.EventUpdate;
import nl.x.client.cheat.cheats.move.Step;
import nl.x.client.cheat.cheats.move.step.StepMode;

/**
 * @author NullEX
 *
 */
public class NCP extends StepMode {
	private double preY;
	private boolean resetNextTick;

	/**
	 * @param parent
	 */
	public NCP(Step parent) {
		super("NCP", parent);
	}

	/*
	 * @see
	 * nl.x.client.cheat.cheats.move.step.StepMode#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventStep) {
			EventStep event = (EventStep) e;
			if (event.getEntity() != mc.thePlayer) {
				return;
			}
			if (event.getState() == State.pre) {
				this.preY = mc.thePlayer.posY;
				if (this.canStep()) {
					event.setStepHeight(1.0f);
				}
			} else {
				if (event.getStepHeight() != 1.0f) {
					return;
				}
				double offset = mc.thePlayer.boundingBox.minY - this.preY;
				if (offset > 0.6) {
					mc.timer.timerSpeed = 0.3f;
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
							mc.thePlayer.posY + 0.42, mc.thePlayer.posZ, mc.thePlayer.onGround));
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
							mc.thePlayer.posY + 0.75, mc.thePlayer.posZ, mc.thePlayer.onGround));
					this.resetNextTick = true;
				}
			}
		}
		if (e instanceof EventUpdate) {

		}
		if (e instanceof EventPacket) {

		}
		if (e instanceof EventTick) {
			if (this.resetNextTick) {
				mc.timer.timerSpeed = 1.0f;
				this.resetNextTick = false;
			}
		}
		super.onEvent(e);
	}

	private boolean canStep() {
		if (mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround && mc.thePlayer.motionY < 0.0
				&& !mc.thePlayer.movementInput.jump) {
			return true;
		}
		return false;
	}

}
