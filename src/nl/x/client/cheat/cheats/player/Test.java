package nl.x.client.cheat.cheats.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "Test")
public class Test extends Cheat {

	public Test() {
		this.addValue(new BooleanValue("Test", true));
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if (mc.thePlayer.ticksExisted % 20 == 0) {
				for (Entity en : mc.theWorld.getLoadedEntityList()) {
					if (en instanceof EntityPlayerSP || !(en instanceof EntityPlayer))
						continue;
					EntityPlayer p = (EntityPlayer) en;
					System.out.println(p.getCommandSenderName() + " | Speed " + p.getAIMoveSpeed() + " | Air "
							+ p.getAir() + " | Eye " + p.getEyeHeight() + "");
				}
			}
		}
		super.onEvent(e);
	}

}
