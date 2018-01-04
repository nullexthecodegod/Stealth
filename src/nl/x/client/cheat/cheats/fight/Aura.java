package nl.x.client.cheat.cheats.fight;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.cheat.value.values.NumberValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventAlways;
import nl.x.client.cheat.cheats.fight.aura.AuraMode;
import nl.x.client.cheat.cheats.fight.aura.impl.AACAura;
import nl.x.client.cheat.cheats.fight.aura.impl.SingleAura;
import nl.x.client.cheat.cheats.fight.aura.impl.TPAura;
import nl.x.client.cheat.cheats.fight.aura.impl.Tick;

/**
 * @author NullEX
 *
 */
@Info(name = "Aura", category = Category.Fight)
public class Aura extends Cheat {
	public ArrayValue modeValue = new ArrayValue("Mode", Lists.newArrayList("Single", "Tick", "AAC", "TPAura"),
			"Single");
	public ArrayList<AuraMode> modes = Lists.newArrayList(new AACAura(this), new SingleAura(this), new TPAura(this),
			new Tick(this));
	public AuraMode mode;
	public BooleanValue block = new BooleanValue("Block", true), unblock = new BooleanValue("Unblock", true),
			crits = new BooleanValue("Crits", false), teams = new BooleanValue("Teams", true);
	public NumberValue reach = new NumberValue("Reach", 5.5, 0.1, 8.0, 0.1);

	public Aura() {
		this.addValue(this.modeValue, this.block, this.unblock, this.crits, this.reach);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event ev) {
		if (ev instanceof EventAlways) {
			for (AuraMode t : this.modes) {
				if (t.getName().equalsIgnoreCase(this.modeValue.getValue().toString())) {
					mode = t;
				} else {
					continue;
				}
			}
		}
		if (this.mode != null) {
			this.setSuffix(this.mode.getName());
			this.mode.onEvent(ev);
		}
		super.onEvent(ev);
	}

	public void attack(EntityLivingBase target) {
		float sharpLevel = EnchantmentHelper.func_152377_a(this.mc.thePlayer.getHeldItem(),
				target.getCreatureAttribute());
		if (this.mc.thePlayer.isBlocking() && unblock.getValue()) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
					C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
		}
		if (this.crits.getValue()) {
			this.crit();
		}
		this.mc.thePlayer.swingItem();
		mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
		if (this.crits.getValue()) {
			this.mc.thePlayer.onCriticalHit(target);
		}
		if (sharpLevel > 0.0f) {
			this.mc.thePlayer.onEnchantmentCritical(target);
		}
	}

	public void crit() {
		if (mc.thePlayer.onGround) {
			boolean v = true;
			if (v) {
				C03PacketPlayer.C04PacketPlayerPosition reset = new C03PacketPlayer.C04PacketPlayerPosition(
						this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false);
				reset.setMoving(false);
				this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.05, this.mc.thePlayer.posZ, false));
				this.mc.thePlayer.sendQueue.addToSendQueue(reset);
				this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.012511, this.mc.thePlayer.posZ, false));
				this.mc.thePlayer.sendQueue.addToSendQueue(reset);
			} else {
				mc.thePlayer.motionY = 0.05;
			}
		}
	}

	public void isValid(EntityPlayer target) {

	}

	public boolean isOnSameTeam(Entity entity) {
		if (this.mc.thePlayer.getDisplayName().getFormattedText()
				.contains("\u00a7" + this.getTeamFromName(this.mc.thePlayer))
				&& entity.getDisplayName().getFormattedText()
						.contains("\u00a7" + Aura.getTeamFromName(this.mc.thePlayer))) {
			return true;
		}
		return false;
	}

	public static String getTeamFromName(Entity entity) {
		Matcher matcher = Pattern.compile("\u00a7(.).*\u00a7r").matcher(entity.getDisplayName().getFormattedText());
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "f";
	}

}
