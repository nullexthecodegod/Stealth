/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2017 Team Pepsi
 *
 * Permission is hereby granted to any persons and/or organizations using this
 * software to copy, modify, merge, publish, and distribute it. Said persons
 * and/or organizations are not allowed to use the software or any derivatives
 * of the work for commercial use or any other means to generate income, nor are
 * they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing
 * and/or trademarking this software without explicit permission from Team
 * Pepsi.
 *
 * Any persons and/or organizations using this software must disclose their
 * source code and have it publicly available, include this license, provide
 * sufficient credit to the original authors of the project (IE: Team Pepsi), as
 * well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package nl.x.api.utils.misc;

import java.awt.Color;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Timer;

import javax.vecmath.Vector3d;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import nl.x.api.utils.entity.other.TargettingTranslator;

public enum PepsiUtils {
	INSTANCE;
	public static final char COLOR_ESCAPE = '\u00A7';
	public static final String[] colorCodes = new String[] { "c", "9", "f", "1", "4" };
	public static final Timer timer = new Timer();
	public final static KeyBinding[] controls = new KeyBinding[] { Minecraft.getMinecraft().gameSettings.keyBindForward,
			Minecraft.getMinecraft().gameSettings.keyBindBack, Minecraft.getMinecraft().gameSettings.keyBindRight,
			Minecraft.getMinecraft().gameSettings.keyBindLeft, Minecraft.getMinecraft().gameSettings.keyBindJump,
			Minecraft.getMinecraft().gameSettings.keyBindSneak };
	private static final Random random = new Random(System.currentTimeMillis());
	public static String buttonPrefix = COLOR_ESCAPE + "c";
	public static Color RAINBOW_COLOR = new Color(0, 0, 0);
	public static Field block_pepsimod_id = null;
	public static GuiButton reconnectButton, autoReconnectButton;
	public static int autoReconnectWaitTime = 5;
	public static String lastIp;
	public static int lastPort;

	/**
	 * Returns a random number between min (inkl.) and max (excl.) If you want a
	 * number between 1 and 4 (inkl) you need to call rand (1, 5)
	 *
	 * @param min
	 *            min inklusive value
	 * @param max
	 *            max exclusive value
	 * @return
	 */
	public static int rand(int min, int max) {
		if (min == max) {
			return max;
		}
		return min + random.nextInt(max - min);
	}

	/**
	 * Returns random boolean
	 *
	 * @return a boolean random value either <code>true</code> or
	 *         <code>false</code>
	 */
	public static boolean rand() {
		return random.nextBoolean();
	}

	public static int ceilDiv(int x, int y) {
		return Math.floorDiv(x, y) + (x % y == 0 ? 0 : 1);
	}

	public static int ensureRange(int value, int min, int max) {
		int toReturn = Math.min(Math.max(value, min), max);
		return toReturn;
	}

	public static boolean canEntityBeSeen(Entity entityIn, EntityPlayer player, TargettingTranslator.TargetBone bone) {
		return entityIn.worldObj.rayTraceBlocks(new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ),
				new Vec3(entityIn.posX, getTargetHeight(entityIn, bone), entityIn.posZ), false, true, false) == null;
	}

	public static double getTargetHeight(Entity entity, TargettingTranslator.TargetBone bone) {
		double targetHeight = entity.posY;
		if (bone == TargettingTranslator.TargetBone.HEAD) {
			targetHeight = entity.getEyeHeight();
		} else if (bone == TargettingTranslator.TargetBone.MIDDLE) {
			targetHeight = entity.getEyeHeight() / 2;
		}
		return targetHeight;
	}

	/**
	 * this is important because getting a block id normally involves iterating
	 * through every object in the block registry in a modded environment there
	 * might be 1000s of entrys there so if we can get the id like this, it
	 * saves us lots of Time™
	 */
	public static int getBlockId(Block block) {
		try {
			return (int) block_pepsimod_id.get(block);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static Vector3d vector3d(BlockPos pos) {
		Vector3d v3d = new Vector3d();
		v3d.x = pos.getX();
		v3d.y = pos.getY();
		v3d.z = pos.getZ();
		return v3d;
	}

	public static Vector3d sub(Vector3d in, Vector3d with) {
		in.x -= with.x;
		in.y -= with.y;
		in.z -= with.z;
		return in;
	}

	public static int toRGBA(int r, int g, int b, int a) {
		return (r << 16) + (g << 8) + (b << 0) + (a << 24);
	}

	public static Vec3 getInterpolatedPos(Entity entity, float ticks) {
		return new Vec3(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)
				.add(getInterpolatedAmount(entity, ticks));
	}

	public static Vec3 getInterpolatedAmount(Entity entity, double x, double y, double z) {
		return new Vec3((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y,
				(entity.posZ - entity.lastTickPosZ) * z);
	}

	public static Vec3 getInterpolatedAmount(Entity entity, Vec3 vec) {
		return getInterpolatedAmount(entity, vec.yCoord, vec.yCoord, vec.zCoord);
	}

	public static Vec3 getInterpolatedAmount(Entity entity, double ticks) {
		return getInterpolatedAmount(entity, ticks, ticks, ticks);
	}

	public static boolean isThrowable(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg
				|| item instanceof ItemEnderPearl || item instanceof ItemPotion || item instanceof ItemFishingRod;
	}

	public static float round(float input, float step) {
		return ((Math.round(input / step)) * step);
	}

	public static float ensureRange(float value, float min, float max) {
		float toReturn = Math.min(Math.max(value, min), max);
		return toReturn;
	}

	public static String roundFloatForSlider(float f) {
		return String.format("%.2f", f);
	}

	public static String roundCoords(double d) {
		return String.format("%.2f", d);
	}

	public static String getFacing() {
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		EnumFacing enumfacing = entity.getHorizontalFacing();
		String s = "Invalid";

		switch (enumfacing) {
			case NORTH:
				s = "-Z";
				break;
			case SOUTH:
				s = "+Z";
				break;
			case WEST:
				s = "-X";
				break;
			case EAST:
				s = "+X";
			default:
				break;
		}

		return s;
	}

	public static double getDimensionCoord(double coord) {
		if (Minecraft.getMinecraft().thePlayer.dimension == 0) {
			return coord / 8;
		} else {
			return coord * 8;
		}
	}

	public static boolean isAttackable(EntityLivingBase entity) {
		return entity != null && entity != Minecraft.getMinecraft().thePlayer && entity.isEntityAlive();
	}

	public static double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static EntityLivingBase getClosestEntityWithoutReachFactor() {
		EntityLivingBase closestEntity = null;
		double distance = 9999.0D;
		for (Object object : Minecraft.getMinecraft().theWorld.loadedEntityList) {
			if ((object instanceof EntityLivingBase)) {
				EntityLivingBase entity = (EntityLivingBase) object;
				if (isAttackable(entity)) {
					double newDistance = Minecraft.getMinecraft().thePlayer.getDistanceSqToEntity(entity);
					if (closestEntity != null) {
						if (distance > newDistance) {
							closestEntity = entity;
							distance = newDistance;
						}
					} else {
						closestEntity = entity;
						distance = newDistance;
					}
				}
			}
		}
		return closestEntity;
	}

	public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd,
			int paramColor) {
		float alpha = (paramColor >> 24 & 0xFF) / 255F;
		float red = (paramColor >> 16 & 0xFF) / 255F;
		float green = (paramColor >> 8 & 0xFF) / 255F;
		float blue = (paramColor & 0xFF) / 255F;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(paramXEnd, paramYStart);
		GL11.glVertex2d(paramXStart, paramYStart);
		GL11.glVertex2d(paramXStart, paramYEnd);
		GL11.glVertex2d(paramXEnd, paramYEnd);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
	}
}
