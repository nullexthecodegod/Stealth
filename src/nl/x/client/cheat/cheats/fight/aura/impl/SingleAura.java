package nl.x.client.cheat.cheats.fight.aura.impl;

import java.util.Objects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import nl.x.api.event.Event;
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
public class SingleAura extends AuraMode {
	public EntityLivingBase target;
	public Timer timer = new Timer();

	/**
	 * @param parent
	 */
	public SingleAura(Aura parent) {
		super("Single", parent);
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
						e.setPitch(set[1]);
						e.setYaw(set[0]);
					}
					break;
				case post:
					if (Objects.nonNull(this.target) && this.timer.hasPassed(200)) {
						if (mc.thePlayer.ticksExisted % 4 == 0) {
							this.getParent().attack(this.target);
						}
						this.getParent().attack(this.target);
						this.timer.reset();
					}
					break;
			}
		}
		super.onEvent(ev);
	}

}
