package nl.x.api.utils.misc;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Proxy;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Session;
import nl.x.client.gui.alt.GuiAltmanager;
import nl.x.client.gui.alt.account.Account;

/**
 * @author NullEX (like only 4-5 things)
 *
 */
public enum Utilites {
	INSTANCE;

	public static Minecraft mc = Minecraft.getMinecraft();
	public static Gson gson = new Gson();
	public static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	public static JsonParser jsonParser = new JsonParser();

	public double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public int interpolate(double ratio, int from, int to) {
		return (int) ((ratio * from) + (1 - ratio) * to);
	}

	public static String[] getWebsite(final String url) throws IOException {
		final URL u = new URL(url);
		final BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
		final ArrayList<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = in.readLine()) != null) {
			lines.add(line);
		}
		in.close();
		return lines.toArray(new String[lines.size()]);
	}

	public static int reAlpha(int color, float alpha) {
		Color c = new Color(color);
		float r = ((float) 1 / 255) * c.getRed();
		float g = ((float) 1 / 255) * c.getGreen();
		float b = ((float) 1 / 255) * c.getBlue();
		return new Color(r, g, b, alpha).getRGB();
	}

	public String login(String name, String password) {
		GuiAltmanager.info = "§aLoggin in";
		YggdrasilAuthenticationService a = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
		YggdrasilUserAuthentication b = (YggdrasilUserAuthentication) a.createUserAuthentication(Agent.MINECRAFT);
		b.setUsername(name);
		b.setPassword(password);
		String displayText = "";
		if (password == "") {
			SessionUtils.setSession(new Session(name, name, "0", "legacy"));
			for (Account c : GuiAltmanager.accounts) {
				if (c.getEmail().equals(name)) {
					c.setUsername(SessionUtils.getSession().getUsername());
				} else {
					continue;
				}
			}
			GuiAltmanager.saveAlts();
			return "§aLogged In (Cracked)";
		}
		try {
			b.logIn();
			SessionUtils.setSession(new Session(b.getSelectedProfile().getName(),
					b.getSelectedProfile().getId().toString(), b.getAuthenticatedToken(), "legacy"));
			for (Account c : GuiAltmanager.accounts) {
				if (c.getEmail().equals(name)) {
					c.setUsername(SessionUtils.getSession().getUsername());
				} else {
					continue;
				}
			}
			GuiAltmanager.saveAlts();
			displayText = "§aLogged In §8(§7" + SessionUtils.getSession().getUsername() + "§8)";
		} catch (AuthenticationException var7) {
			if (!var7.getMessage().contains("Invalid username or password.")
					&& !var7.getMessage().toLowerCase().contains("account migrated")) {
				displayText = "§cCannot contact authentication server!";
			} else {
				displayText = "§cWrong password! (" + name + ")";
			}
		} catch (NullPointerException var8) {
			displayText = "§cWeird error: This alt doesn't have a username!";
		}
		return displayText;
	}

	public static double[] getMoveToLoc(final EntityLivingBase entity) {
		if (entity == null) {
			return new double[] { 0.0, 0.0, 0.0 };
		}
		final double dX = entity.posX - entity.lastTickPosX;
		final double dY = 0.0;
		final double dZ = entity.posZ - entity.lastTickPosZ;
		return new double[] { entity.posX + dX, entity.posY + 0.0, entity.posZ + dZ };
	}

	public static float[] getRotationsNeeded(final EntityLivingBase entity) {
		final double posX = entity.posX - mc.thePlayer.posX;
		final double posY = entity.posY + entity.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		final double posZ = entity.posZ - mc.thePlayer.posZ;
		final double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
		final float yaw = (float) (Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-(Math.atan2(posY, var14) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static float getDistFromMouseSq(final EntityLivingBase entity) {
		final double diffX = entity.posX - mc.thePlayer.posX;
		final double diffY = entity.posY + entity.getEyeHeight() * 0.9
				- (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		final double diffZ = entity.posZ - mc.thePlayer.posZ;
		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
		final float[] neededRotations = {
				mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw),
				mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch) };
		final float neededYaw = mc.thePlayer.rotationYaw - neededRotations[0];
		final float neededPitch = mc.thePlayer.rotationPitch - neededRotations[1];
		return neededYaw * neededYaw + neededPitch * neededPitch;
	}

	public static void setSpeed(final float speed) {
		double yaw = mc.thePlayer.rotationYaw;
		final boolean isMoving = mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
		final boolean isMovingForward = mc.thePlayer.moveForward > 0.0f;
		final boolean isMovingBackward = mc.thePlayer.moveForward < 0.0f;
		final boolean isMovingRight = mc.thePlayer.moveStrafing > 0.0f;
		final boolean isMovingLeft = mc.thePlayer.moveStrafing < 0.0f;
		final boolean isMovingSideways = isMovingLeft || isMovingRight;
		final boolean isMovingStraight = isMovingForward || isMovingBackward;
		if (isMoving) {
			if (isMovingForward && !isMovingSideways) {
				yaw += 0.0;
			} else if (isMovingBackward && !isMovingSideways) {
				yaw += 180.0;
			} else if (isMovingForward && isMovingLeft) {
				yaw += 45.0;
			} else if (isMovingForward) {
				yaw -= 45.0;
			} else if (!isMovingStraight && isMovingLeft) {
				yaw += 90.0;
			} else if (!isMovingStraight && isMovingRight) {
				yaw -= 90.0;
			} else if (isMovingBackward && isMovingLeft) {
				yaw += 135.0;
			} else if (isMovingBackward) {
				yaw -= 135.0;
			}
			yaw = Math.toRadians(yaw);
			mc.thePlayer.motionX = -Math.sin(yaw) * speed;
			mc.thePlayer.motionZ = Math.cos(yaw) * speed;
		}
	}


	public static void deleteFolder(final Path path) throws IOException {
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(final Path file, final IOException e) {
				return this.handleException(e);
			}

			private FileVisitResult handleException(final IOException e) {
				e.printStackTrace();
				return FileVisitResult.TERMINATE;
			}

			@Override
			public FileVisitResult postVisitDirectory(final Path dir, final IOException e) throws IOException {
				if (e != null) {
					return this.handleException(e);
				}
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	private static String convertToHex(final byte[] data) {
		final StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; ++i) {
			int halfbyte = data[i] >>> 4 & 0xF;
			int two_halfs = 0;
			do {
				if (halfbyte >= 0 && halfbyte <= 9) {
					buf.append((char) (48 + halfbyte));
				} else {
					buf.append((char) (97 + (halfbyte - 10)));
				}
				halfbyte = (data[i] & 0xF);
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	private static String SHA1(final String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		final MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	public static String generateHWID() {
		try {
			return SHA1(String.valueOf(System.getProperty("user.name")) + System.getProperty("os.version")
					+ System.getProperty("os.name") + System.getProperty("user.name"));
		} catch (NoSuchAlgorithmException e) {
			return "UNKNOWN";
		} catch (UnsupportedEncodingException e2) {
			return "UNKNOWN";
		}
	}

	public static void dropFirst(final Item item) {
		for (int index = 0; index <= 8; ++index) {
			final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(index);
			if (stack != null && stack.getItem() == item) {
				mc.playerController.windowClick(0, 36 + index, 1, 4, mc.thePlayer);
				break;
			}
		}
	}

	public static boolean hotbarIsFull() {
		for (int index = 0; index <= 36; ++index) {
			final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(index);
			if (stack == null) {
				return false;
			}
		}
		return true;
	}

	public static boolean hotbarHas(final Item item) {
		for (int index = 0; index <= 8; ++index) {
			final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(index);
			if (stack != null && stack.getItem() == item) {
				return true;
			}
		}
		return false;
	}

	public static void shiftClick(final Item item) {
		for (int index = 9; index <= 36; ++index) {
			final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
			if (stack != null && stack.getItem() == item) {
				mc.playerController.windowClick(0, index, 0, 1, mc.thePlayer);
				break;
			}
		}
	}

	public static void useFirst(final Item item) {
		for (int index = 0; index <= 8; ++index) {
			final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(index);
			if (stack != null && stack.getItem() == item) {
				final int oldItem = mc.thePlayer.inventory.currentItem;
				mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
				mc.getNetHandler()
						.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
				mc.thePlayer.stopUsingItem();
				mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
				break;
			}
		}
	}

	public static void swap(final int slot, final int hotbarNum) {
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
	}

	public static int getBestWeapon(final Entity target) {
		final int originalSlot = mc.thePlayer.inventory.currentItem;
		int weaponSlot = -1;
		float weaponDamage = 1.0f;
		for (byte slot = 0; slot < 9; ++slot) {
			mc.thePlayer.inventory.currentItem = slot;
			final ItemStack itemStack = mc.thePlayer.getHeldItem();
			if (itemStack != null) {
				float damage = getItemDamage(itemStack);
				damage += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED);
				if (damage > weaponDamage) {
					weaponDamage = damage;
					weaponSlot = slot;
				}
			}
		}
		if (weaponSlot != -1) {
			return weaponSlot;
		}
		return originalSlot;
	}

	private static float getItemDamage(final ItemStack itemStack) {
		final Multimap multimap = itemStack.getAttributeModifiers();
		if (!multimap.isEmpty()) {
			final Iterator iterator = multimap.entries().iterator();
			if (iterator.hasNext()) {
				final Map.Entry entry = (Entry) iterator.next();
				final AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
				double damage;
				if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
					damage = attributeModifier.getAmount();
				} else {
					damage = attributeModifier.getAmount() * 100.0;
				}
				if (attributeModifier.getAmount() > 1.0) {
					return 1.0f + (float) damage;
				}
				return 1.0f;
			}
		}
		return 1.0f;
	}

}
