package net.minecraft.item;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.block.BlockDispenser;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemArmor extends Item
{
    /** Holds the 'base' maxDamage that each armorType have. */
    private static final int[] maxDamageArray = new int[] {11, 16, 15, 13};
    public static final String[] EMPTY_SLOT_NAMES = new String[] {"minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots"};
    private static final IBehaviorDispenseItem dispenserBehavior = new BehaviorDefaultDispenseItem()
    {
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
            BlockPos var3 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
            int var4 = var3.getX();
            int var5 = var3.getY();
            int var6 = var3.getZ();
            AxisAlignedBB var7 = new AxisAlignedBB((double)var4, (double)var5, (double)var6, (double)(var4 + 1), (double)(var5 + 1), (double)(var6 + 1));
            List var8 = source.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, var7, Predicates.and(IEntitySelector.NOT_SPECTATING, new IEntitySelector.ArmoredMob(stack)));

            if (var8.size() > 0)
            {
                EntityLivingBase var9 = (EntityLivingBase)var8.get(0);
                int var10 = var9 instanceof EntityPlayer ? 1 : 0;
                int var11 = EntityLiving.getArmorPosition(stack);
                ItemStack var12 = stack.copy();
                var12.stackSize = 1;
                var9.setCurrentItemOrArmor(var11 - var10, var12);

                if (var9 instanceof EntityLiving)
                {
                    ((EntityLiving)var9).setEquipmentDropChance(var11, 2.0F);
                }

                --stack.stackSize;
                return stack;
            }
            else
            {
                return super.dispenseStack(source, stack);
            }
        }
    };

    /**
     * Stores the armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots
     */
    public final int armorType;

    /** Holds the amount of damage that the armor reduces at full durability. */
    public final int damageReduceAmount;

    /**
     * Used on RenderPlayer to select the correspondent armor to be rendered on the player: 0 is cloth, 1 is chain, 2 is
     * iron, 3 is diamond and 4 is gold.
     */
    public final int renderIndex;

    /** The EnumArmorMaterial used for this ItemArmor */
    private final ItemArmor.ArmorMaterial material;

    public ItemArmor(ItemArmor.ArmorMaterial material, int renderIndex, int armorType)
    {
        this.material = material;
        this.armorType = armorType;
        this.renderIndex = renderIndex;
        this.damageReduceAmount = material.getDamageReductionAmount(armorType);
        this.setMaxDamage(material.getDurability(armorType));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
    }

    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        if (renderPass > 0)
        {
            return 16777215;
        }
        else
        {
            int var3 = this.getColor(stack);

            if (var3 < 0)
            {
                var3 = 16777215;
            }

            return var3;
        }
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    /**
     * Return the armor material for this armor item.
     */
    public ItemArmor.ArmorMaterial getArmorMaterial()
    {
        return this.material;
    }

    /**
     * Return whether the specified armor ItemStack has a color.
     */
    public boolean hasColor(ItemStack stack)
    {
        return this.material != ItemArmor.ArmorMaterial.LEATHER ? false : (!stack.hasTagCompound() ? false : (!stack.getTagCompound().hasKey("display", 10) ? false : stack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    public int getColor(ItemStack stack)
    {
        if (this.material != ItemArmor.ArmorMaterial.LEATHER)
        {
            return -1;
        }
        else
        {
            NBTTagCompound var2 = stack.getTagCompound();

            if (var2 != null)
            {
                NBTTagCompound var3 = var2.getCompoundTag("display");

                if (var3 != null && var3.hasKey("color", 3))
                {
                    return var3.getInteger("color");
                }
            }

            return 10511680;
        }
    }

    /**
     * Remove the color from the specified armor ItemStack.
     */
    public void removeColor(ItemStack stack)
    {
        if (this.material == ItemArmor.ArmorMaterial.LEATHER)
        {
            NBTTagCompound var2 = stack.getTagCompound();

            if (var2 != null)
            {
                NBTTagCompound var3 = var2.getCompoundTag("display");

                if (var3.hasKey("color"))
                {
                    var3.removeTag("color");
                }
            }
        }
    }

    /**
     * Sets the color of the specified armor ItemStack
     */
    public void setColor(ItemStack stack, int color)
    {
        if (this.material != ItemArmor.ArmorMaterial.LEATHER)
        {
            throw new UnsupportedOperationException("Can\'t dye non-leather!");
        }
        else
        {
            NBTTagCompound var3 = stack.getTagCompound();

            if (var3 == null)
            {
                var3 = new NBTTagCompound();
                stack.setTagCompound(var3);
            }

            NBTTagCompound var4 = var3.getCompoundTag("display");

            if (!var3.hasKey("display", 10))
            {
                var3.setTag("display", var4);
            }

            var4.setInteger("color", color);
        }
    }

    /**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair The ItemStack to be repaired
     * @param repair The ItemStack that should repair this Item (leather for leather armor, etc.)
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return this.material.getRepairItem() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        int var4 = EntityLiving.getArmorPosition(itemStackIn) - 1;
        ItemStack var5 = playerIn.getCurrentArmor(var4);

        if (var5 == null)
        {
            playerIn.setCurrentItemOrArmor(var4, itemStackIn.copy());
            itemStackIn.stackSize = 0;
        }

        return itemStackIn;
    }

    public static enum ArmorMaterial
    {
        LEATHER("LEATHER", 0, "leather", 5, new int[]{1, 3, 2, 1}, 15),
        CHAIN("CHAIN", 1, "chainmail", 15, new int[]{2, 5, 4, 1}, 12),
        IRON("IRON", 2, "iron", 15, new int[]{2, 6, 5, 2}, 9),
        GOLD("GOLD", 3, "gold", 7, new int[]{2, 5, 3, 1}, 25),
        DIAMOND("DIAMOND", 4, "diamond", 33, new int[]{3, 8, 6, 3}, 10);
        private final String name;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;

        private ArmorMaterial(String p_i45789_1_, int p_i45789_2_, String name, int maxDamage, int[] reductionAmounts, int enchantability)
        {
            this.name = name;
            this.maxDamageFactor = maxDamage;
            this.damageReductionAmountArray = reductionAmounts;
            this.enchantability = enchantability;
        }

        public int getDurability(int armorType)
        {
            return ItemArmor.maxDamageArray[armorType] * this.maxDamageFactor;
        }

        public int getDamageReductionAmount(int armorType)
        {
            return this.damageReductionAmountArray[armorType];
        }

        public int getEnchantability()
        {
            return this.enchantability;
        }

        public Item getRepairItem()
        {
            return this == LEATHER ? Items.leather : (this == CHAIN ? Items.iron_ingot : (this == GOLD ? Items.gold_ingot : (this == IRON ? Items.iron_ingot : (this == DIAMOND ? Items.diamond : null))));
        }

        public String getName()
        {
            return this.name;
        }
    }
}
