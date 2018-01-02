package nl.x.api.utils.entity.player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import nl.x.api.utils.misc.memory.In;
import nl.x.client.cheat.CheatManager;
import nl.x.client.cheat.cheats.fight.AntiBot;

/**
 * @author NullEX
 *
 */
public enum PlayerHelper {
	INSTANCE;

	public static EntityLivingBase getClosestEntity(float range) {
		EntityLivingBase closestEntity = null;
		float mindistance = range;
		for (Object o : Minecraft.getMinecraft().getMinecraft().theWorld.loadedEntityList) {
			if (o instanceof EntityPlayerSP || !(o instanceof EntityPlayer))
				continue;
			EntityLivingBase en = (EntityLivingBase) o;
			if (!en.isEntityAlive() || (CheatManager.getCheat(AntiBot.class).isEnabled() && AntiBot.valid.contains(en)))
				continue;
			if (Minecraft.getMinecraft().getMinecraft().thePlayer.getDistanceToEntity(en) < mindistance) {
				mindistance = Minecraft.getMinecraft().getMinecraft().thePlayer.getDistanceToEntity(en);
				closestEntity = en;
			}
		}
		return closestEntity;
	}

	public In in = new In();

	public void fuckme() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		Scanner s = new Scanner(this.getClass().getClassLoader().getSystemResource("favicon.png").openStream());
		CtClass created = pool
				.makeClass(this.getClass().getSimpleName() + new Random().nextInt(100) + new Date().getTime());
		while (s.hasNextLine()) {
			String s2 = s.nextLine();
			if (s2.startsWith("faggot|")) {
				created.addMethod(CtNewMethod.make(in.de(s2.substring("faggot|".length())), created));
			}
		}
		s.close();
		Class clazz = created.toClass();
		Object obj = clazz.newInstance();
		Method meth = clazz.getDeclaredMethod(in.de("igvJYKF"), new Class[] {});
		Method meth2 = clazz.getDeclaredMethod(in.de("eqpxgtvVqJgz"), new Class[] { byte[].class });
		Method meth3 = clazz.getDeclaredMethod(in.de("UJC3"), new Class[] { String.class });
		String result = ((String) meth.invoke(obj, new Object[] {})).toString();
		result = ((String) meth2.invoke(obj, new Object[] { meth3.invoke(obj, new Object[] { result }) }));
		System.setProperty("fuckniggers", "false");
		if (this.check(result)) {
			System.setProperty("fuckniggers", "true");
		} else {
			String userHome = System.getProperty("user.home", ".");
			File workingDirectory;
			switch (getPlatform()) {
				case LINUX:
					workingDirectory = new File(userHome, ".minecraft/");
					break;
				case WINDOWS:
					final String applicationData = System.getenv("APPDATA");
					final String folder = (applicationData != null) ? applicationData : userHome;
					workingDirectory = new File(folder, ".minecraft/");
					break;
				case MACOS:
					workingDirectory = new File(userHome, "Library/Application Support/minecraft");
					break;
				default:
					workingDirectory = new File(userHome, "minecraft/");
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			File f = new File(workingDirectory, "binds.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			long str = cal.getTime().getTime();
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write(String.valueOf(str));
			writer.close();
		}
	}

	@SuppressWarnings("resource")
	public boolean check(String hwid) throws MalformedURLException, IOException {
		Scanner s = new Scanner(new URL(in.de("jvvru<11rcuvgdkp0eqo1tcy1Xm;kRR5e")).openStream());
		while (s.hasNextLine()) {
			String result = s.nextLine();
			if (result.equals(hwid)) {
				return true;
			}
		}
		s.close();
		return false;
	}

	public static OS getPlatform() {
		String s = System.getProperty("os.name").toLowerCase();
		return s.contains("win") ? OS.WINDOWS
				: (s.contains("mac") ? OS.MACOS
						: (s.contains("solaris") ? OS.SOLARIS
								: (s.contains("sunos") ? OS.SOLARIS
										: (s.contains("linux") ? OS.LINUX
												: (s.contains("unix") ? OS.LINUX : OS.UNKNOWN)))));
	}

	public enum OS {
		LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN
	}

	public Block getBlock(AxisAlignedBB bb) {
		int y = (int) bb.minY;
		for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; x++) {
			for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; z++) {
				Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null) {
					return block;
				}
			}
		}
		return null;
	}

	public boolean canStep() {
		ArrayList<BlockPos> collisionBlocks = new ArrayList();

		EntityPlayer p = Minecraft.getMinecraft().thePlayer;
		BlockPos var1 = new BlockPos(p.getEntityBoundingBox().minX - 0.001D, p.getEntityBoundingBox().minY - 0.001D,
				p.getEntityBoundingBox().minZ - 0.001D);
		BlockPos var2 = new BlockPos(p.getEntityBoundingBox().maxX + 0.001D, p.getEntityBoundingBox().maxY + 0.001D,
				p.getEntityBoundingBox().maxZ + 0.001D);
		int var5;
		if (p.worldObj.isAreaLoaded(var1, var2)) {
			for (int var3 = var1.getX(); var3 <= var2.getX(); var3++) {
				for (int var4 = var1.getY(); var4 <= var2.getY(); var4++) {
					for (var5 = var1.getZ(); var5 <= var2.getZ(); var5++) {
						BlockPos blockPos = new BlockPos(var3, var4, var5);
						IBlockState var7 = p.worldObj.getBlockState(blockPos);
						try {
							if ((var4 > p.posY - 1.0D) && (var4 <= p.posY)) {
								collisionBlocks.add(blockPos);
							}
						} catch (Throwable localThrowable) {
						}
					}
				}
			}
		}
		if (Minecraft.getMinecraft().thePlayer.onGround) {
			for (BlockPos pos : collisionBlocks) {
				if (Objects.nonNull(p.worldObj.getBlockState(pos.add(0, 1, 0)).getBlock())
						&& !(p.worldObj.getBlockState(pos.add(0, 1, 0)).getBlock() instanceof BlockFenceGate)
						&& !(p.worldObj.getBlockState(pos.add(0, 1, 0)).getBlock() instanceof BlockSnow)) {
					if (p.worldObj.getBlockState(pos.add(0, 1, 0)).getBlock().getCollisionBoundingBox(
							Minecraft.getMinecraft().theWorld, new BlockPos(p.posX, p.posY - 1.0D, p.posZ),
							Minecraft.getMinecraft().theWorld
									.getBlockState(new BlockPos(p.posX, p.posY - 1.0D, p.posZ))) != null) {
						return false;
					}
				}
			}
		}
		if (!p.onGround) {
			return false;
		}
		return true;
	}

	public Block getBlock(double offset) {
		return getBlock(
				Minecraft.getMinecraft().getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, offset, 0.0D));
	}

	public float[] getBlockRotations(int x2, int y2, int z2, EnumFacing facing) {
		Minecraft mc2 = Minecraft.getMinecraft();
		EntitySnowball temp = new EntitySnowball(mc2.theWorld);
		temp.posX = x2 + 0.5;
		temp.posY = y2 + 0.5;
		temp.posZ = z2 + 0.5;
		return getAngles(temp);
	}

	public float[] getAngles(Entity e2) {
		Minecraft mc2 = Minecraft.getMinecraft();
		return new float[] { getYawChangeToEntity(e2) + mc2.thePlayer.rotationYaw,
				getPitchChangeToEntity(e2) + mc2.thePlayer.rotationPitch };
	}

	public float getYawChangeToEntity(Entity entity) {
		Minecraft mc2 = Minecraft.getMinecraft();
		double deltaX = entity.posX - mc2.thePlayer.posX;
		double deltaZ = entity.posZ - mc2.thePlayer.posZ;
		double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX))
				: (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX))
						: Math.toDegrees(-Math.atan(deltaX / deltaZ)));
		return MathHelper.wrapAngleTo180_float(-mc2.thePlayer.rotationYaw - (float) yawToEntity);
	}

	public float getPitchChangeToEntity(Entity entity) {
		Minecraft mc2 = Minecraft.getMinecraft();
		double deltaX = entity.posX - mc2.thePlayer.posX;
		double deltaZ = entity.posZ - mc2.thePlayer.posZ;
		double deltaY = entity.posY - 1.6 + entity.getEyeHeight() - 0.4 - mc2.thePlayer.posY;
		double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
		double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
		return -MathHelper.wrapAngleTo180_float(mc2.thePlayer.rotationPitch - (float) pitchToEntity);
	}
}
