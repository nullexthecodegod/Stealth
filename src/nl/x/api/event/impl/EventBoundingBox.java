package nl.x.api.event.impl;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class EventBoundingBox extends Event {
	private Block block;
	private BlockPos blockPos;
	private AxisAlignedBB boundingBox;

	/**
	 * @param block
	 * @param blockPos
	 * @param boundingBox
	 */
	public EventBoundingBox(Block block, BlockPos blockPos, AxisAlignedBB boundingBox) {
		this.block = block;
		this.blockPos = blockPos;
		this.boundingBox = boundingBox;
	}

	/**
	 * @return the boundingBox
	 */
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	/**
	 * @param boundingBox
	 *            the boundingBox to set
	 */
	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}

	/**
	 * @return the block
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * @return the blockPos
	 */
	public BlockPos getBlockPos() {
		return blockPos;
	}

}
