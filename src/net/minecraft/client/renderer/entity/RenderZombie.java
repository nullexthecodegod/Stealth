package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderZombie extends RenderBiped
{
    private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
    private final ModelBiped field_82434_o;
    private final ModelZombieVillager zombieVillagerModel;
    private final List field_177121_n;
    private final List field_177122_o;

    public RenderZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelZombie(), 0.5F, 1.0F);
        LayerRenderer var2 = (LayerRenderer)this.layerRenderers.get(0);
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillager();
        this.addLayer(new LayerHeldItem(this));
        LayerBipedArmor var3 = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.field_177189_c = new ModelZombie(0.5F, true);
                this.field_177186_d = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(var3);
        this.field_177122_o = Lists.newArrayList(this.layerRenderers);

        if (var2 instanceof LayerCustomHead)
        {
            this.removeLayer(var2);
            this.addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
        }

        this.removeLayer(var3);
        this.addLayer(new LayerVillagerArmor(this));
        this.field_177121_n = Lists.newArrayList(this.layerRenderers);
    }

    public void func_180579_a(EntityZombie zombie, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.func_82427_a(zombie);
        super.doRender(zombie, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation func_180578_a(EntityZombie zombie)
    {
        return zombie.isVillager() ? zombieVillagerTextures : zombieTextures;
    }

    private void func_82427_a(EntityZombie zombie)
    {
        if (zombie.isVillager())
        {
            this.mainModel = this.zombieVillagerModel;
            this.layerRenderers = this.field_177121_n;
        }
        else
        {
            this.mainModel = this.field_82434_o;
            this.layerRenderers = this.field_177122_o;
        }

        this.modelBipedMain = (ModelBiped)this.mainModel;
    }

    protected void rotateCorpse(EntityZombie bat, float p_77043_2_, float p_77043_3_, float partialTicks)
    {
        if (bat.isConverting())
        {
            p_77043_3_ += (float)(Math.cos((double)bat.ticksExisted * 3.25D) * Math.PI * 0.25D);
        }

        super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityLiving entity)
    {
        return this.func_180578_a((EntityZombie)entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     *  
     * @param entityYaw The yaw rotation of the passed entity
     */
    public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.func_180579_a((EntityZombie)entity, x, y, z, entityYaw, partialTicks);
    }

    protected void rotateCorpse(EntityLivingBase bat, float p_77043_2_, float p_77043_3_, float partialTicks)
    {
        this.rotateCorpse((EntityZombie)bat, p_77043_2_, p_77043_3_, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     *  
     * @param entityYaw The yaw rotation of the passed entity
     */
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.func_180579_a((EntityZombie)entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.func_180578_a((EntityZombie)entity);
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
        this.func_180579_a((EntityZombie)entity, x, y, z, entityYaw, partialTicks);
    }
}
