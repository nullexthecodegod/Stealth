package nl.x.client.cheat.cheats.player;

import com.google.common.collect.Lists;

import net.minecraft.network.play.client.C03PacketPlayer;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventPacket.Type;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "NoFall")
public class NoFall extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("Send", "Set"), "Set");

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "set":
					if (this.mc.thePlayer.fallDistance < 3.0f) {
						return;
					}
					((EventUpdate) e).setOnGround(true);
					break;
				case "send":
					break;
			}
		}
		if (e instanceof EventPacket) {
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "set":
					if (((EventPacket) e).getType() == Type.outbound
							&& ((EventPacket) e).getPacket() instanceof C03PacketPlayer) {
						if (this.mc.thePlayer.fallDistance < 3.0f) {
							return;
						}
						C03PacketPlayer packet = (C03PacketPlayer) ((EventPacket) e).getPacket();
						packet.onGround = true;
					}
					break;
				case "send":
					break;
			}
		}
		super.onEvent(e);
	}

}
