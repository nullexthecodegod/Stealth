package nl.x.client.cheat.cheats.player;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "Nuker")
public class Nuker extends Cheat {
	private BlockPos blockPos;
	private Block blockType;

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e2) {
		if (e2 instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e2;
			double rad = 4.0;
			double x2 = Minecraft.getMinecraft().thePlayer.posX - rad;
			while (x2 <= Minecraft.getMinecraft().thePlayer.posX + rad) {
				double y2 = Minecraft.getMinecraft().thePlayer.posY - rad;
				while (y2 <= Minecraft.getMinecraft().thePlayer.posY + rad) {
					double z2 = Minecraft.getMinecraft().thePlayer.posZ - rad;
					while (z2 <= Minecraft.getMinecraft().thePlayer.posZ + rad) {
						BlockPos b = new BlockPos(x2, y2, z2);
						if (!Minecraft.getMinecraft().theWorld.getBlockState(b).equals(Blocks.air)
								|| !Minecraft.getMinecraft().theWorld.getBlockState(b).equals(Blocks.bedrock)) {
							Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
									C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, b, EnumFacing.DOWN));
						}
						z2 += 1.0;
					}
					y2 += 1.0;
				}
				x2 += 1.0;
			}
		}
		super.onEvent(e2);
	}

}
