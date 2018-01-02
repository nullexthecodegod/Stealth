package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFallingBlock extends Render
{

    public RenderFallingBlock(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    public void doRender(EntityFallingBlock fallingBlock, double p_180557_2_, double p_180557_4_, double p_180557_6_, float p_180557_8_, float p_180557_9_)
    {
        if (fallingBlock.getBlock() != null)
        {
            this.bindTexture(TextureMap.locationBlocksTexture);
            IBlockState var10 = fallingBlock.getBlock();
            Block var11 = var10.getBlock();
            BlockPos var12 = new BlockPos(fallingBlock);
            World var13 = fallingBlock.getWorldObj();

            if (var10 != var13.getBlockState(var12) && var11.getRenderType() != -1)
            {
                if (var11.getRenderType() == 3)
                {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float)p_180557_2_, (float)p_180557_4_, (float)p_180557_6_);
                    GlStateManager.disableLighting();
                    Tessellator var14 = Tessellator.getInstance();
                    WorldRenderer var15 = var14.getWorldRenderer();
                    var15.startDrawingQuads();
                    var15.setVertexFormat(DefaultVertexFormats.BLOCK);
                    int var16 = var12.getX();
                    int var17 = var12.getY();
                    int var18 = var12.getZ();
                    var15.setTranslation((double)((float)(-var16) - 0.5F), (double)(-var17), (double)((float)(-var18) - 0.5F));
                    BlockRendererDispatcher var19 = Minecraft.getMinecraft().getBlockRendererDispatcher();
                    IBakedModel var20 = var19.getModelFromBlockState(var10, var13, (BlockPos)null);
                    var19.getBlockModelRenderer().renderModel(var13, var20, var10, var12, var15, false);
                    var15.setTranslation(0.0D, 0.0D, 0.0D);
                    var14.draw();
                    GlStateManager.enableLighting();
                    GlStateManager.popMatrix();
                    super.doRender(fallingBlock, p_180557_2_, p_180557_4_, p_180557_6_, p_180557_8_, p_180557_9_);
                }
            }
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityFallingBlock entity)
    {
        return TextureMap.locationBlocksTexture;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityFallingBlock)entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     *  
     * @param entityYaw The yaw rotation of the passed entity
     */
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityFallingBlock)entity, x, y, z, entityYaw, partialTicks);
    }
}