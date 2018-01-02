package nl.x.client.cheat.cheats.player;

import java.util.HashSet;
import java.util.LinkedList;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.event.Event;
import nl.x.api.event.Event.State;
import nl.x.api.event.impl.EventAlways;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.misc.BlockHelper;

/**
 * @author NullEX
 *
 */
@Info(name = "Fucker")
public class Fucker extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("Bed", "Egg", "Cake", "Other"), "Bed");
	public ArrayValue speed = new ArrayValue("Speed", Lists.newArrayList("Instant", "Normal"), "Instant");
	public ArrayValue cake = new ArrayValue("Cake Mode", Lists.newArrayList("Break", "Eat"), "Break");
	public BooleanValue look = new BooleanValue("Look", true), swing = new BooleanValue("Swing", true);
	public double currentDamage;

	public Fucker() {
		this.addValue(this.mode, this.speed, this.look, this.swing);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventAlways) {
			if (this.mode.getValue().toString().equalsIgnoreCase("cake")) {
				if (!this.getValues().contains(this.cake)) {
					this.addValue(this.cake);
				}
			} else {
				if (this.getValues().contains(this.cake)) {
					this.getValues().remove(this.cake);
				}
			}
		}
		if (e instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e;
			int radius = 5;
			for (int x = -radius; x < radius; x++) {
				for (int y = radius; y > -radius; y--) {
					for (int z = -radius; z < radius; z++) {
						BlockPos blockPos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y,
								mc.thePlayer.posZ + z);
						Block block = mc.theWorld.getBlockState(blockPos).getBlock();
						if (block != Blocks.air && this.isValid(blockPos)) {
							if (this.look.getValue() && event.getState().equals(State.pre)) {
								float[] set = BlockHelper.getBlockRotations(mc.thePlayer.posX + x,
										mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
								event.setYaw(set[0]);
								event.setPitch(set[1]);
							}
							if (this.mode.getValue().toString().equalsIgnoreCase("cake")) {
								if (this.cake.getValue().toString().equalsIgnoreCase("break")) {
									if (this.speed.getValue().toString().equalsIgnoreCase("instant")) {
										this.smashBlock(blockPos);
									} else {
										this.currentDamage += block.getPlayerRelativeBlockHardness(mc.thePlayer,
												mc.theWorld, blockPos);
										mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
												Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
										mc.thePlayer.swingItem();
										if (this.currentDamage >= 1.0f) {
											mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
													Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
											this.currentDamage = 0.0f;
										}

									}
								} else {
									mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
											mc.thePlayer.getHeldItem(), blockPos,
											EnumFacing.getFacingFromVector((float) mc.thePlayer.posX,
													(float) mc.thePlayer.posY, (float) mc.thePlayer.posZ),
											this.getVec3(blockPos));
								}
								if (this.swing.getValue()) {
									mc.thePlayer.swingItem();
								}
							} else {
								if (this.speed.getValue().toString().equalsIgnoreCase("instant")) {
									this.smashBlock(blockPos);
								} else {
									this.currentDamage += block.getPlayerRelativeBlockHardness(mc.thePlayer,
											mc.theWorld, blockPos);
									mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
											Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
									mc.thePlayer.swingItem();
									if (this.currentDamage >= 1.0f) {
										mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
												Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
										this.currentDamage = 0.0f;
									}

								}
								if (this.swing.getValue()) {
									mc.thePlayer.swingItem();
								}
							}
						}
					}
				}
			}

		}
		super.onEvent(e);
	}

	public void smashBlock(BlockPos pos) {
		mc.thePlayer.sendQueue.addToSendQueue(
				new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
		mc.thePlayer.sendQueue.addToSendQueue(
				new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
	}

	public Vec3 getVec3(BlockPos blockPos) {
		return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
	}

	private BlockPos find() {
		final LinkedList<BlockPos> queue = new LinkedList<BlockPos>();
		final HashSet<BlockPos> alreadyProcessed = new HashSet<BlockPos>();
		queue.add(new BlockPos(mc.thePlayer));
		while (!queue.isEmpty()) {
			final BlockPos currentPos = queue.poll();
			if (alreadyProcessed.contains(currentPos)) {
				continue;
			}
			alreadyProcessed.add(currentPos);
			if (mc.thePlayer.getDistanceSq(currentPos) > 16.0) {
				continue;
			}
			int d = Block.getIdFromBlock(mc.theWorld.getBlockState(currentPos).getBlock());
			boolean bed = this.mode.getValue().toString().equalsIgnoreCase("bed");
			boolean cake = this.mode.getValue().toString().equalsIgnoreCase("cake");
			boolean egg = this.mode.getValue().toString().equalsIgnoreCase("egg");
			if (d != 0 && ((egg && d == Block.getIdFromBlock(Blocks.dragon_egg))
					|| (bed && d == Block.getIdFromBlock(Blocks.bed))
					|| (cake && d == Block.getIdFromBlock(Blocks.cake)))) {
				return currentPos;
			}
			queue.add(currentPos.north());
			queue.add(currentPos.south());
			queue.add(currentPos.west());
			queue.add(currentPos.east());
			queue.add(currentPos.down());
			queue.add(currentPos.up());
		}
		return null;
	}

	public boolean isValid(BlockPos currentPos) {
		int d = Block.getIdFromBlock(mc.theWorld.getBlockState(currentPos).getBlock());
		boolean bed = this.mode.getValue().toString().equalsIgnoreCase("bed");
		boolean cake = this.mode.getValue().toString().equalsIgnoreCase("cake");
		boolean egg = this.mode.getValue().toString().equalsIgnoreCase("egg");
		if (d != 0 && ((egg && d == Block.getIdFromBlock(Blocks.dragon_egg))
				|| (bed && d == Block.getIdFromBlock(Blocks.bed)) || (cake && d == Block.getIdFromBlock(Blocks.cake)))
				&& d != Block.getIdFromBlock(Blocks.air)) {
			return true;
		}
		return false;
	}

}
