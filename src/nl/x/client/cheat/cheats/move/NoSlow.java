package nl.x.client.cheat.cheats.move;

import com.google.common.collect.Lists;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventSlowdown;
import nl.x.api.event.impl.EventSlowdown.From;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "NoSlow")
public class NoSlow extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("NCP", "AAC"), "NCP");

	public NoSlow() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		this.setSuffix(this.mode.getValue().toString());
		if (e instanceof EventSlowdown) {
			EventSlowdown event = (EventSlowdown) e;
			this.setSuffix(this.mode.getValue().toString());
			if (event.getFrom().equals(From.item)) {
				switch (this.mode.getValue().toString().toLowerCase()) {
					case "ncp":
						if (mc.thePlayer.isBlocking()) {
							mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
									C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
						}
						event.setCanceled(true);
						break;
					case "aac":
						if (mc.thePlayer.isBlocking()) {
							mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
									C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
						}
						event.setModifier(0.7);
						break;
				}
			}
		}
		if (e instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e;
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "ncp":
					break;
				case "aac":
					if (mc.thePlayer.getItemInUse() != null && mc.thePlayer.hasInput()) {
						if (mc.thePlayer.onGround) {
						} else if (mc.thePlayer.motionY < -0.0) {
						}
					}
					break;
			}
		}
		if (e instanceof EventPacket) {
			EventPacket event = (EventPacket) e;
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "ncp":
					break;
				case "aac":
					if (event.getPacket() instanceof C03PacketPlayer && mc.thePlayer.isUsingItem()) {
						C03PacketPlayer p = (C03PacketPlayer) event.getPacket();
						p.setMoving(false);
						event.setPacket(p);
					}
					break;
			}
		}
		super.onEvent(e);
	}

}
