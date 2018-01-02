package nl.x.client.cheat.cheats.fight;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.cheat.value.values.NumberValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventAlways;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.entity.player.MoveUtils;
import nl.x.api.utils.misc.Timer;
import nl.x.api.utils.misc.Utilites;

/**
 * @author NullEX
 *
 */
@Info(name = "AutoHeal")
public class AutoHeal extends Cheat {
	public BooleanValue soup = new BooleanValue("Soup", true), pots = new BooleanValue("Potions", true);
	public BooleanValue drop = new BooleanValue("Drop", true), jump = new BooleanValue("Jump", false);
	public NumberValue health = new NumberValue("Health", 4.5, 1, 10, 0.1),
			delay = new NumberValue("Delay", 250, 200, 1000, 1);
	public Timer timer = new Timer();

	public AutoHeal() {
		this.addValue(this.health, this.delay, this.soup, this.pots);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventAlways) {
			if (this.soup.getValue()) {
				if (!this.getValues().contains(this.drop)) {
					this.addValue(this.drop);
				}
			} else {
				if (this.getValues().contains(this.drop)) {
					this.getValues().remove(this.drop);
				}
			}
			if (this.pots.getValue()) {
				if (!this.getValues().contains(this.jump)) {
					this.addValue(this.jump);
				}
			} else {
				if (this.getValues().contains(this.jump)) {
					this.getValues().remove(this.jump);
				}
			}
		}
		if (e instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e;
			if (this.soup.getValue()) {
				if (mc.thePlayer.getHealth() < (this.health.getValue().doubleValue() * 2)) {
					Utilites.dropFirst(Items.bowl);
					if (!Utilites.hotbarHas(Items.mushroom_stew) && !Utilites.hotbarIsFull()) {
						Utilites.shiftClick(Items.mushroom_stew);
					}
					if (this.timer.hasPassed(this.delay.getValue().floatValue())
							&& Utilites.hotbarHas(Items.mushroom_stew)) {
						Utilites.useFirst(Items.mushroom_stew);
						this.timer.reset();
					}
				}
			}
			if (this.pots.getValue()) {
				if (mc.thePlayer.getHealth() < (this.health.getValue().doubleValue() * 2)) {
					if (this.hotBarHasPots()) {
						int potSlot;
						if (this.jump.getValue()) {
							MoveUtils.setSpeed(0.0);
							if (mc.thePlayer.onGround) {
								mc.thePlayer.motionY = 0.41;
							}
						}
						switch (event.getState()) {
							case pre:
								event.setPitch(this.jump.getValue() ? 90 : -90);
								break;
							case post:
								if ((potSlot = this.getPotionFromInventory()) != -1
										&& timer.hasPassed(this.delay.getValue().floatValue())) {
									for (int i = 36; i < 45; ++i) {
										final ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i)
												.getStack();
										if (stack != null && this.isSplashPot(stack)) {
											final int oldSlot = this.mc.thePlayer.inventory.currentItem;
											mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(i - 36));
											float pitch = mc.thePlayer.rotationPitch;
											mc.thePlayer.rotationPitch = this.jump.getValue() ? 90 : -90;
											mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(stack));
											mc.thePlayer.rotationPitch = pitch;
											mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldSlot));
											this.timer.reset();
											break;
										}
									}
								}
								break;
						}
					} else if (!Utilites.hotbarIsFull()) {
						this.grabPot();
					}
				}
			}
		}
		super.onEvent(e);
	}

	private boolean isSplashPot(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof ItemPotion) {
			ItemPotion potion = (ItemPotion) stack.getItem();
			if (ItemPotion.isSplash(stack.getItemDamage())) {
				for (Object o2 : potion.getEffects(stack)) {
					PotionEffect effect = (PotionEffect) o2;
					if (effect.getPotionID() != Potion.heal.getId())
						continue;
					return true;
				}
			}
		}
		return false;
	}

	private int getPotionFromInventory() {
		int pot = -1;
		int counter = 0;
		for (int i2 = 1; i2 < 45; ++i2) {
			ItemStack is2;
			ItemPotion potion;
			Item item;
			if (!mc.thePlayer.inventoryContainer.getSlot(i2).getHasStack()
					|| !((item = (is2 = mc.thePlayer.inventoryContainer.getSlot(i2).getStack())
							.getItem()) instanceof ItemPotion)
					|| (potion = (ItemPotion) item).getEffects(is2) == null)
				continue;
			for (Object o2 : potion.getEffects(is2)) {
				PotionEffect effect = (PotionEffect) o2;
				if (effect.getPotionID() != Potion.heal.id || !ItemPotion.isSplash(is2.getItemDamage()))
					continue;
				++counter;
				pot = i2;
			}
		}
		return pot;
	}

	private void grabPot() {
		for (int i2 = 9; i2 < 36; ++i2) {
			ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i2).getStack();
			if (stack == null || !this.isSplashPot(stack))
				continue;
			mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, i2, 1, 2, mc.thePlayer);
			break;
		}
	}

	private boolean hotBarHasPots() {
		for (int i2 = 36; i2 < 45; ++i2) {
			ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i2).getStack();
			if (stack == null || !this.isSplashPot(stack))
				continue;
			return true;
		}
		return false;
	}

}
