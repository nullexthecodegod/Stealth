package nl.x.client.cheat.cheats.player.scaffold.impl;

import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.entity.player.RotationHelper;
import nl.x.api.utils.misc.Timer;
import nl.x.client.cheat.cheats.player.Scaffold;
import nl.x.client.cheat.cheats.player.scaffold.ScaffoldMode;

/**
 * @author NullEX
 *
 */
public class NewScaffold extends ScaffoldMode {
	private BlockEntry blockEntry;
	public Timer timer = new Timer();

	/**
	 * @param parent
	 */
	public NewScaffold(Scaffold parent) {
		super("New", parent);
	}

	/*
	 * @see
	 * nl.x.client.cheat.cheats.player.scaffold.ScaffoldMode#onEvent(nl.x.api.
	 * event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e;
			if (this.getSlotWithBlock() == -1) {
				return;
			}
			if (!mc.thePlayer.onGround && mc.thePlayer.motionY < 0.24 && mc.thePlayer.motionY > 0.0
					&& this.getParent().tower.getValue() && mc.thePlayer.rotationPitch > 85.0f) {
				mc.thePlayer.motionY = -10.0;
			}
			switch (event.getState()) {
				case post:
					if (this.blockEntry == null) {
						return;
					}
					final int currentSlot = mc.thePlayer.inventory.currentItem;
					final int slot = this.getSlotWithBlock();
					mc.thePlayer.inventory.currentItem = slot;
					mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
					mc.thePlayer.swingItem();
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(),
							this.blockEntry.getPosition(), this.blockEntry.getFacing(),
							new Vec3(this.blockEntry.getPosition().getX(), this.blockEntry.getPosition().getY(),
									this.blockEntry.getPosition().getZ()));
					mc.thePlayer.inventory.currentItem = currentSlot;
					mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentSlot));
					this.blockEntry = null;
					break;
				case pre:
					if (this.getParent().tower.getValue() && mc.thePlayer.rotationPitch >= 85.0f
							&& mc.thePlayer.onGround) {
						mc.thePlayer.jump();
					}
					this.blockEntry = this.find();
					if (this.blockEntry == null) {
						return;
					}
					float[] rotations = RotationHelper.INSTANCE.getRotations(this.blockEntry.getPosition());
					event.setYaw(rotations[0]);
					event.setPitch(RotationHelper.INSTANCE.getRotations(
							this.getPositionByFace(this.blockEntry.getPosition(), this.blockEntry.getFacing()))[1]
							- 3.0f);
					break;
			}
		}
		super.onEvent(e);
	}

	public BlockEntry find() {
		EnumFacing[] invert = { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST,
				EnumFacing.WEST };
		BlockPos position = new BlockPos(mc.thePlayer.getPositionVector()).offset(EnumFacing.DOWN);
		if (!(mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
			return null;
		}
		for (final EnumFacing facing : EnumFacing.values()) {
			BlockPos offset = position.offset(facing);
			IBlockState blockState = mc.theWorld.getBlockState(offset);
			if (!(mc.theWorld.getBlockState(offset).getBlock() instanceof BlockAir)) {
				return new BlockEntry(offset, invert[facing.ordinal()]);
			}
		}
		BlockPos[] offsets = { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1),
				new BlockPos(0, 0, 1) };
		if (mc.thePlayer.onGround) {
			for (BlockPos offset : offsets) {
				BlockPos offsetPos = position.add(offset.getX(), 0, offset.getZ());
				IBlockState blockState2 = mc.theWorld.getBlockState(offsetPos);
				if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
					for (EnumFacing facing2 : EnumFacing.values()) {
						BlockPos offset2 = offsetPos.offset(facing2);
						IBlockState blockState3 = mc.theWorld.getBlockState(offset2);
						if (!(mc.theWorld.getBlockState(offset2).getBlock() instanceof BlockAir)) {
							return new BlockEntry(offset2, invert[facing2.ordinal()]);
						}
					}
				}
			}
		}
		return null;
	}

	private int getSlotWithBlock() {
		for (int i = 0; i < 9; ++i) {
			ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
			if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
				return i;
			}
		}
		return -1;
	}

	public Vec3 getPositionByFace(BlockPos position, EnumFacing facing) {
		Vec3 offset = new Vec3(facing.getDirectionVec().getX() / 2.0, facing.getDirectionVec().getY() / 2.0,
				facing.getDirectionVec().getZ() / 2.0);
		Vec3 point = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
		return point.add(offset);
	}

	class BlockEntry {
		private BlockPos position;
		private EnumFacing facing;

		public BlockEntry(BlockPos position, EnumFacing facing) {
			this.position = position;
			this.facing = facing;
		}

		public BlockPos getPosition() {
			return this.position;
		}

		public EnumFacing getFacing() {
			return this.facing;
		}
	}

}
