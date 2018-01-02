package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import nl.x.api.event.impl.EventSlowdown;
import nl.x.api.event.impl.EventSlowdown.From;

public class BlockSoulSand extends Block {

	public BlockSoulSand() {
		super(Material.sand);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		float var4 = 0.125F;
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1 - var4,
				pos.getZ() + 1);
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		EventSlowdown event = new EventSlowdown(From.soulsand, 0.4);
		event.fire();
		if (event.isCanceled())
			return;
		entityIn.motionX *= event.getModifier();
		entityIn.motionZ *= event.getModifier();
	}
}
