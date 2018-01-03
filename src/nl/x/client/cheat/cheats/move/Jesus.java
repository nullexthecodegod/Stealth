package nl.x.client.cheat.cheats.move;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.misc.Timer;

/**
 * @author NullEX
 *
 */
@Info(name = "Jesus")
public class Jesus extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("NCP", "AAC"), "NCP");
	public Timer timer = new Timer();

	public Jesus() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			this.setSuffix(this.mode.getValue().toString());
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "ncp":
					if ((isInLiquid() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava())
							&& !mc.thePlayer.isSneaking()) {
						if (this.timer.hasPassed(5000L)) {
							mc.thePlayer.motionY = 1.0E-9;
							mc.thePlayer.motionX *= 1.2;
							mc.thePlayer.motionZ *= 1.2;
							this.timer.reset();
						}
						mc.thePlayer.motionY = (mc.thePlayer.isInsideOfMaterial(Material.air) ? 0.041 : 0.087);
					}
					break;
				case "aac":
					if (mc.thePlayer.isInWater() && !mc.gameSettings.keyBindSneak.isPressed()
							&& !mc.gameSettings.keyBindJump.isPressed()) {
						mc.thePlayer.motionY += 0.02;
					}
					break;
			}

		}
		if (e instanceof EventPacket) {
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "ncp":
					break;
				case "aac":
					break;
			}

		}
		super.onEvent(e);
	}

	public boolean isInLiquid() {
		boolean inLiquid = false;
		final int y = (int) mc.thePlayer.boundingBox.minY;
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
				final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					inLiquid = true;
				}
			}
		}
		return inLiquid;
	}

}
