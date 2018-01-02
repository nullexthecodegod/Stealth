package nl.x.client.cheat.cheats.move.flight.impl;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.entity.player.MoveUtils;
import nl.x.client.cheat.cheats.move.Flight;
import nl.x.client.cheat.cheats.move.flight.FlightMode;

/**
 * @author NullEX
 *
 */
public class Hypixel extends FlightMode {

	/**
	 * @param parent
	 */
	public Hypixel(Flight parent) {
		super("Hypixel", parent);
	}

	/*
	 * @see
	 * nl.x.client.cheat.cheats.move.flight.FlightMode#onEvent(nl.x.api.event.
	 * Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			mc.thePlayer.motionY = 0.0D;
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-9D, mc.thePlayer.posZ);
			if (mc.thePlayer.ticksExisted % 3 == 0 && mc.theWorld
					.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.2D, mc.thePlayer.posZ))
					.getBlock() instanceof BlockAir) {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-9D,
								mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
			}
			double baseSpeed = 0.32;
			if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
				int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
				baseSpeed *= 1.0 + 0.25 * (amplifier + (amplifier < 1 ? 1 : 0));
			}
			MoveUtils.setSpeed(baseSpeed);
		}
		super.onEvent(e);
	}

}
