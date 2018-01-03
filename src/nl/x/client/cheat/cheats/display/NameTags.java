package nl.x.client.cheat.cheats.display;

import java.awt.Font;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventNametag;
import nl.x.api.event.impl.EventRender3D;
import nl.x.api.utils.render.RenderUtil;
import nl.x.api.utils.render.cfont.CFontRenderer;

/**
 * @author NullEX (Not fully most of it is made by Aristhena because he is god
 *         <3)
 *
 */
@Info(name = "NameTags")
public class NameTags extends Cheat {
	private double gradualFOVModifier;
	public CFontRenderer font = new CFontRenderer(new Font("Adobe Courier", Font.PLAIN, 18), true, true);
	public BooleanValue ttf = new BooleanValue("Font", true), armor = new BooleanValue("Armor", true);

	public NameTags() {
		this.addValue(this.ttf);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event event) {
		if (event instanceof EventRender3D) {
			float pTicks = mc.timer.renderPartialTicks;
			for (Entity e : mc.theWorld.getLoadedEntityList()) {
				if (e instanceof EntityPlayerSP || !(e instanceof EntityPlayer))
					continue;
				GlStateManager.pushMatrix();
				String str = (e.isSneaking() ? "§c" : "") + e.getDisplayName().getFormattedText() + " §";
				final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * pTicks - mc.getRenderManager().viewerPosX;
				double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * pTicks - mc.getRenderManager().viewerPosY;
				final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * pTicks - mc.getRenderManager().viewerPosZ;
				double health = ((EntityPlayer) e).getHealth() / 2.0;
				y += e.height + 0.2;
				if (health >= 6.0) {
					str += "2";
				} else if (health >= 3.5) {
					str += "6";
				} else {
					str += "4";
				}
				str += health;
				GlStateManager.disableDepth();
				GlStateManager.translate(x, y, z);
				if (this.mc.gameSettings.thirdPersonView == 2) {
					GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
					GlStateManager.rotate(this.mc.getRenderManager().playerViewX, -1.0f, 0.0f, 0.0f);
				} else {
					GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
					GlStateManager.rotate(this.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
				}
				this.scale(e);
				GlStateManager.disableLighting();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.disableTexture2D();
				GlStateManager.translate(0.0, -2.5, 0.0);
				int strWidth = (this.ttf.getValue() ? this.font.getStringWidth(str)
						: mc.fontRendererObj.getStringWidth(str));
				RenderUtil.INSTANCE.drawRect(-strWidth / 2 - 3, -12.0, strWidth / 2 + 3, 1.0, -1191182336);
				if (this.ttf.getValue()) {
					this.font.drawString(str, -strWidth / 2, -9.0, -1);
				} else {
					mc.fontRendererObj.drawString(str, -strWidth / 2, -9.0, -1);
				}
				GlStateManager.enableTexture2D();
				GlStateManager.enableDepth();
				if (this.armor.getValue()) {
					this.renderArmor((EntityPlayer) e);
				}
				GlStateManager.enableLighting();
				GlStateManager.popMatrix();
				Gui.drawRect(0, 0, 0, 0, 0);
			}
		}
		if (event instanceof EventNametag) {
			((EventNametag) event).setCanceled(true);
		}
		super.onEvent(event);
	}

	private void scale(Entity ent) {
		final float dist = mc.thePlayer.getDistanceToEntity(ent);
		float scale = 0.02672f;
		final float factor = (float) ((dist <= 8.0f) ? (8.0 * 0.1) : (dist * 0.1));
		scale *= factor;
		GlStateManager.scale(-scale, -scale, scale);
	}

	public void renderArmor(EntityPlayer player) {
		int xOffset = 0;
		for (final ItemStack armourStack : player.inventory.armorInventory) {
			if (armourStack != null) {
				xOffset -= 8;
			}
		}
		if (player.getHeldItem() != null) {
			xOffset -= 8;
			final ItemStack stock = player.getHeldItem().copy();
			if (stock.hasEffect() && (stock.getItem() instanceof ItemTool || stock.getItem() instanceof ItemArmor)) {
				stock.stackSize = 1;
			}
			this.renderItemStack(stock, xOffset, -30);
			xOffset += 16;
		}
		final ItemStack[] renderStack = player.inventory.armorInventory;
		for (int index = 3; index >= 0; --index) {
			final ItemStack armourStack = renderStack[index];
			if (armourStack != null) {
				final ItemStack renderStack2 = armourStack;
				this.renderItemStack(renderStack2, xOffset, -30);
				xOffset += 16;
			}
		}
	}

	private void renderItemStack(final ItemStack stack, final int x, final int y) {
		GlStateManager.pushMatrix();
		GlStateManager.disableAlpha();
		GlStateManager.clear(256);
		this.mc.getRenderItem().zLevel = -150.0f;
		this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
		this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, stack, x, y);
		this.mc.getRenderItem().zLevel = 0.0f;
		GlStateManager.disableBlend();
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlStateManager.disableDepth();
		GlStateManager.disableLighting();
		this.renderEnchantText(stack, x, y);
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		GlStateManager.scale(2.0f, 2.0f, 2.0f);
		GlStateManager.enableAlpha();
		GlStateManager.popMatrix();
	}

	private void renderEnchantText(final ItemStack stack, final int x, final int y) {
		int enchantmentY = y - 24;
		if (stack.getEnchantmentTagList() != null && stack.getEnchantmentTagList().tagCount() >= 6) {
			this.font.drawString("god", x * 2, enchantmentY, 16711680);
			return;
		}
		if (stack.getItem() instanceof ItemArmor) {
			final int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
			final int projectileProtectionLevel = EnchantmentHelper
					.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack);
			final int blastProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId,
					stack);
			final int fireProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId,
					stack);
			final int thornsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
			final int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
			if (protectionLevel > 0) {
				this.font.drawString("pr" + protectionLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (projectileProtectionLevel > 0) {
				this.font.drawString("pp" + projectileProtectionLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (blastProtectionLevel > 0) {
				this.font.drawString("bp" + blastProtectionLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (fireProtectionLevel > 0) {
				this.font.drawString("fp" + fireProtectionLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (thornsLevel > 0) {
				this.font.drawString("t" + thornsLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (unbreakingLevel > 0) {
				this.font.drawString("u" + unbreakingLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
		}
		if (stack.getItem() instanceof ItemBow) {
			final int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
			final int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
			final int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
			final int unbreakingLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
			if (powerLevel > 0) {
				this.font.drawString("po" + powerLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (punchLevel > 0) {
				this.font.drawString("pu" + punchLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (flameLevel > 0) {
				this.font.drawString("f" + flameLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (unbreakingLevel2 > 0) {
				this.font.drawString("u" + unbreakingLevel2, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
		}
		if (stack.getItem() instanceof ItemSword) {
			final int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
			final int knockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
			final int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
			final int unbreakingLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
			if (sharpnessLevel > 0) {
				this.font.drawString("sh" + sharpnessLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (knockbackLevel > 0) {
				this.font.drawString("kn" + knockbackLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (fireAspectLevel > 0) {
				this.font.drawString("f" + fireAspectLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (unbreakingLevel2 > 0) {
				this.font.drawString("ub" + unbreakingLevel2, x * 2, enchantmentY, -1052689);
			}
		}
		if (stack.getItem() instanceof ItemTool) {
			final int unbreakingLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
			final int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
			final int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
			final int silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack);
			if (efficiencyLevel > 0) {
				this.font.drawString("eff" + efficiencyLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (fortuneLevel > 0) {
				this.font.drawString("fo" + fortuneLevel, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (silkTouch > 0) {
				this.font.drawString("st" + silkTouch, x * 2, enchantmentY, -1052689);
				enchantmentY += 8;
			}
			if (unbreakingLevel3 > 0) {
				this.font.drawString("ub" + unbreakingLevel3, x * 2, enchantmentY, -1052689);
			}
		}
		if (stack.getItem() == Items.golden_apple && stack.hasEffect()) {
			this.font.drawString("god", x * 2, enchantmentY, -1052689);
		}
	}

}
