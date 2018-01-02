package nl.x.client.cheat.cheats.fight;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventPacket;
import nl.x.api.event.impl.EventSpawnPlayer;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "AntiBot")
public class AntiBot extends Cheat {
	public static BooleanValue invis = new BooleanValue("Invis", true),
			spawnDistance = new BooleanValue("Spawn Distance", true),
			spawnArmor = new BooleanValue("Spawn Armor", true), itemPickup = new BooleanValue("Item pickup", true);
	public static ArrayList<EntityPlayer> valid = Lists.newArrayList();

	public AntiBot() {
		this.addValue(this.invis, this.spawnDistance, this.spawnArmor, this.itemPickup);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#disable()
	 */
	@Override
	public void disable() {
		this.valid.clear();
		super.disable();
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e;
			if (!mc.thePlayer.isEntityAlive()) {
				this.valid.clear();
			}
			if (this.invis.getValue()) {
				for (Entity en : mc.theWorld.getLoadedEntityList()) {
					if (!(en instanceof EntityPlayer) || en instanceof EntityPlayerSP || !en.isInvisible())
						continue;
					mc.theWorld.removeEntity(en);
				}
			}
		}
		if (e instanceof EventSpawnPlayer) {
			EventSpawnPlayer event = (EventSpawnPlayer) e;
			switch (event.getState()) {
				case post:
					EntityPlayer en = event.getPlayer();
					if (this.spawnDistance.getValue()) {
						if (mc.thePlayer.getDistanceToEntity(en) < 4.0) {
							mc.theWorld.removeEntity(en);
							return;
						}
					}
					if (this.spawnArmor.getValue()) {
						final ItemStack[] renderStack = en.inventory.armorInventory;
						for (int index = 3; index >= 0; --index) {
							final ItemStack armourStack = renderStack[index];
							if (armourStack != null) {
								mc.theWorld.removeEntity(en);
								return;
							}
						}
					}
					break;
				case pre:
					break;
			}
		}
		if (e instanceof EventPacket) {
			EventPacket event = (EventPacket) e;
			switch (event.getType()) {
				case inbound:
					if (this.itemPickup.getValue()) {
						if (event.getPacket() instanceof S0DPacketCollectItem) {
							S0DPacketCollectItem p = (S0DPacketCollectItem) event.getPacket();
							for (Entity en : mc.theWorld.getLoadedEntityList()) {
								if (!(en instanceof EntityPlayer) || en instanceof EntityPlayerSP
										|| en.getEntityId() != p.getEntityID())
									continue;
								this.valid.add((EntityPlayer) en);
								break;
							}

						}
					}
					break;
				case outbound:
					break;
			}

		}
		super.onEvent(e);
	}

}
