package nl.x.client.cheat.cheats.player;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.Event.State;
import nl.x.api.event.impl.EventBoundingBox;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "Phase")
public class Phase extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("New", "Aris", "SkipClip", "Offset"), "New");

	public Phase() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e;
			this.setSuffix(this.mode.getValue().toString());
			float direction = mc.thePlayer.rotationYaw;
			if (mc.thePlayer.moveForward < 0.0F) {
				direction += 180.0F;
			}
			if (mc.thePlayer.moveStrafing > 0.0F) {
				direction = direction - 90.0F
						* (mc.thePlayer.moveForward > 0.0F ? 0.5F : mc.thePlayer.moveForward < 0.0F ? -0.5F : 1.0F);
			}
			if (mc.thePlayer.moveStrafing < 0.0F) {
				direction = direction + 90.0F
						* (mc.thePlayer.moveForward > 0.0F ? 0.5F : mc.thePlayer.moveForward < 0.0F ? -0.5F : 1.0F);
			}
			double x = Math.cos((direction + 90.0F) * 3.141592653589793D / 180.0D) * 0.2D;
			double z = Math.sin((direction + 90.0F) * 3.141592653589793D / 180.0D) * 0.2D;
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "new":
					break;
				case "aris":
					if (event.getState().equals(State.pre) && this.isInsideBlock() && mc.thePlayer.isSneaking()) {
						float yaw = mc.thePlayer.rotationYaw;
						mc.thePlayer.boundingBox.offsetAndUpdate(1.2 * Math.cos(Math.toRadians(yaw + 90.0f)), 0.0,
								1.2 * Math.sin(Math.toRadians(yaw + 90.0f)));
					}
					break;
				case "offset":
					if (this.isInsideBlock() && mc.thePlayer.isSneaking()) {
						double[] offset = { x * 1.9D, 1.0D, z * 1.9D };
						mc.getNetHandler()
								.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
										mc.thePlayer.posX + offset[0], mc.thePlayer.posY, mc.thePlayer.posZ + offset[2],
										mc.thePlayer.onGround));
					}
					break;
			}
		}
		if (e instanceof EventBoundingBox) {
			EventBoundingBox event = (EventBoundingBox) e;
			if (event.getBoundingBox() != null && event.getBoundingBox().maxY > mc.thePlayer.boundingBox.minY
					&& mc.thePlayer.isSneaking()) {
				event.setBoundingBox(null);
			}

		}
		super.onEvent(e);
	}

	private boolean isInsideBlock() {
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); y < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxY) + 1; ++y) {
				for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
						.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
					final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					if (block != null && !(block instanceof BlockAir)) {
						AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z),
								mc.theWorld.getBlockState(new BlockPos(x, y, z)));
						if (block instanceof BlockHopper) {
							boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
						}
						if (boundingBox != null && mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
