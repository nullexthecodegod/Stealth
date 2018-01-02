package nl.x.client.cheat.cheats.fight;

import net.minecraft.network.play.client.C03PacketPlayer;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "Regen")
public class Regen extends Cheat {

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if (this.mc.thePlayer.getHealth() < this.mc.thePlayer.getMaxHealth() && mc.thePlayer.onGround) {
				for (int i = 1; i < this.mc.thePlayer.getMaxHealth() - this.mc.thePlayer.getHealth(); ++i) {
					mc.getNetHandler()
							.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX,
									this.mc.thePlayer.posY, this.mc.thePlayer.posZ, mc.thePlayer.onGround));
				}
			}
		}
		super.onEvent(e);
	}

}
