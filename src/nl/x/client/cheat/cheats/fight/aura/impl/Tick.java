package nl.x.client.cheat.cheats.fight.aura.impl;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.entity.player.PlayerHelper;
import nl.x.api.utils.entity.player.RotationHelper;
import nl.x.api.utils.misc.Timer;
import nl.x.client.cheat.cheats.fight.Aura;
import nl.x.client.cheat.cheats.fight.aura.AuraMode;

/**
 * @author NullEX
 *
 */
public class Tick extends AuraMode {
	public EntityLivingBase target;
	public Timer timer = new Timer();

	/**
	 * @param parent
	 */
	public Tick(Aura parent) {
		super("Tick", parent);
	}

	/*
	 * @see nl.x.client.cheat.cheats.fight.aura.AuraMode#onEvent(nl.x.api.event.
	 * Event)
	 */
	@Override
	public void onEvent(Event ev) {
		if (ev instanceof EventUpdate) {
			EventUpdate e = (EventUpdate) ev;
			switch (e.getState()) {
				case pre:
					this.target = PlayerHelper.getClosestEntity(this.getParent().reach.getValue().floatValue());
					if (Objects.nonNull(this.target)) {
						if (this.mc.thePlayer.getCurrentEquippedItem() != null
								&& this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword
								&& this.getParent().block.getValue()) {
							this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(),
									this.mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
						}
						float[] set = RotationHelper.INSTANCE.getRotationsNeeded(this.target);
						e.setYaw(set[0]);
						e.setPitch(set[1]);
					}
					break;
				case post:
					if (Objects.nonNull(this.target) && mc.thePlayer
							.getDistanceToEntity(this.target) <= this.getParent().reach.getValue().doubleValue()
							&& this.timer.hasPassed(500)) {
						float yaw = mc.thePlayer.rotationYaw;
						float pitch = mc.thePlayer.rotationPitch;
						mc.thePlayer.rotationYaw = RotationHelper.INSTANCE.getYaw(this.target);
						mc.thePlayer.rotationPitch = RotationHelper.INSTANCE.getPitch(this.target);
						this.swap(9, mc.thePlayer.inventory.currentItem, target, false, false);
						attack(target, false);
						if (mc.thePlayer.ticksExisted % 10 == 0) {
							attack(target, true);
						}
						attack(target, false);
						attack(target, true);
						this.swap(9, mc.thePlayer.inventory.currentItem, target, true, false);
						attack(target, false);
						attack(target, true);
						mc.thePlayer.rotationYaw = yaw;
						mc.thePlayer.rotationPitch = pitch;
						this.timer.reset();
					}
					break;
			}
		}
		if (ev instanceof EventPacket) {

		}
		super.onEvent(ev);
	}

	public void swap(int slot, int hotbarNum, Entity target, boolean attack, boolean crit) {
		if (attack) {
			mc.thePlayer.swingItem();
			if (!crit) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
			}
			mc.playerController.attackEntity(mc.thePlayer, target);
			mc.thePlayer.swingItem();
			mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
			if (mc.thePlayer.ticksExisted % 2 == 0) {
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
			}
			if (mc.thePlayer.ticksExisted % 25 == 0) {
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
			}
		}
		Minecraft.getMinecraft().playerController.windowClick(
				Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2,
				Minecraft.getMinecraft().thePlayer);
	}

	public void attack(EntityLivingBase entity, final boolean crit) {
		mc.thePlayer.swingItem();
		if (crit && mc.thePlayer.onGround) {
			this.getParent().crit();
		} else {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
		}
		Minecraft.getMinecraft().thePlayer.sendQueue
				.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
	}

}
