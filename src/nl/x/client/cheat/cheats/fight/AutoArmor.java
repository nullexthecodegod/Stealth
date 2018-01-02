package nl.x.client.cheat.cheats.fight;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author Aristhena
 *
 */
@Info(name = "AutoArmor")
public class AutoArmor extends Cheat {

	
	
	/* 
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
	        if (mc.thePlayer != null && (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory || !mc.currentScreen.getClass().getName().contains("inventory"))) {
	            int slotID = -1;
	            double maxProt = -1.0;
	            for (int i = 9; i < 45; ++i) {
	                ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
	                if (stack != null) {
	                    if (this.canEquip(stack)) {
	                        final double protValue = this.getProtectionValue(stack);
	                        if (protValue >= maxProt) {
	                            slotID = i;
	                            maxProt = protValue;
	                        }
	                    }
	                }
	            }
	            if (slotID != -1) {
	                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotID, 0, 1, mc.thePlayer);
	            }
	        }
		}
		super.onEvent(e);
	}
	
    private boolean canEquip(final ItemStack stack) {
        return (mc.thePlayer.getEquipmentInSlot(1) == null && stack.getUnlocalizedName().contains("boots")) || (mc.thePlayer.getEquipmentInSlot(2) == null && stack.getUnlocalizedName().contains("leggings")) || (mc.thePlayer.getEquipmentInSlot(3) == null && stack.getUnlocalizedName().contains("chestplate")) || (mc.thePlayer.getEquipmentInSlot(4) == null && stack.getUnlocalizedName().contains("helmet"));
    }
    
    private double getProtectionValue(final ItemStack stack) {
        return ((ItemArmor)stack.getItem()).damageReduceAmount + (100 - ((ItemArmor)stack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 4 * 0.0075;
    }
	
}
