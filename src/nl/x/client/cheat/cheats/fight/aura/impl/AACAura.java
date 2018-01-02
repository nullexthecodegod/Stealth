package nl.x.client.cheat.cheats.fight.aura.impl;

import java.util.Objects;
import java.util.Random;

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
public class AACAura extends AuraMode {
	public EntityLivingBase target;
	public float offset = 2;
	public Timer timer = new Timer(), rotationTimer = new Timer();
	public Random random = new Random();

	/**
	 * @param parent
	 */
	public AACAura(Aura parent) {
		super("AAC", parent);
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
						this.offset = 2;
						while (this.offset > 1.0) {
							e.setPitch(set[1] / this.offset);
							e.setYaw(set[0] / this.offset);
							mc.thePlayer.rotationYawHead = e.getYaw();
							this.offset -= 0.1 * this.random.nextInt(3);
						}
					}
					break;
				case post:
					if (Objects.nonNull(this.target)
							&& mc.thePlayer.getDistanceToEntity(this.target) <= this.getParent().reach.getValue()
									.doubleValue()
							&& this.timer.hasPassed(150 + this.random.nextInt(100)) && !this.random.nextBoolean()) {
						float yaw = mc.thePlayer.rotationYaw;
						float pitch = mc.thePlayer.rotationPitch;
						int offset = random.nextInt(5);
						float[] set = RotationHelper.INSTANCE.getRotationsNeeded(this.target);
						this.getParent().attack(this.target);
						this.timer.reset();
					}
					break;
			}
		}
		super.onEvent(ev);
	}

}
