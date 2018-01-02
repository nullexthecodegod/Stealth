package nl.x.api.utils.entity.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public enum MoveUtils {
	INSTANCE;

	public static void setSpeed(final double speed) {
		final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		double yaw = player.rotationYaw;
		final boolean isMoving = player.moveForward != 0.0 || player.moveStrafing != 0.0;
		final boolean isMovingForward = player.moveForward > 0.0;
		final boolean isMovingBackward = player.moveForward < 0.0;
		final boolean isMovingRight = player.moveStrafing > 0.0;
		final boolean isMovingLeft = player.moveStrafing < 0.0;
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
			player.motionX = -Math.sin(yaw) * speed;
			player.motionZ = Math.cos(yaw) * speed;
		}
	}

	public static void multiplySpeed(final Float multiplier) {
		final EntityPlayerSP player3;
		final EntityPlayerSP entityPlayerSP2;
		final EntityPlayerSP player2 = entityPlayerSP2 = (player3 = Minecraft.getMinecraft().thePlayer);
		entityPlayerSP2.motionX *= multiplier;
		final EntityPlayerSP entityPlayerSP3;
		final EntityPlayerSP entityPlayerSP = entityPlayerSP3 = player3;
		entityPlayerSP3.motionZ *= multiplier;
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

	public static double[] getMoveToLoc(final EntityLivingBase entity, final int ticks) {
		if (entity == null) {
			return new double[] { 0.0, 0.0, 0.0 };
		}
		final double dX = entity.posX - entity.lastTickPosX;
		final double dY = 0.0;
		final double dZ = entity.posZ - entity.lastTickPosZ;
		return new double[] { entity.posX + dX * ticks, entity.posY + 0.0 * ticks, entity.posZ + dZ * ticks };
	}

	public static void toFwd(final double amount) {
		final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		double yaw = player.rotationYaw;
		yaw = Math.toRadians(yaw);
		final double dX = -Math.sin(yaw) * amount;
		final double dZ = Math.cos(yaw) * amount;
		Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX + dX,
				Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ + dZ);
	}

	public static void tpRel(double x, double y, double z) {
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		player.setPosition(player.posX + x, player.posY + y, player.posZ + z);
	}

	public static void tpPacket(double x, double y, double z) {
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		player.sendQueue.addToSendQueue(
				new C03PacketPlayer.C04PacketPlayerPosition(player.posX + x, player.posY + y, player.posZ + z, false));
		player.sendQueue.addToSendQueue(
				new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, false));
		player.sendQueue.addToSendQueue(
				new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, true));
	}

	public static void setSpeed(float speed) {
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		double yaw = player.rotationYaw;
		boolean isMoving = (player.moveForward != 0.0F) || (player.moveStrafing != 0.0F);
		boolean isMovingForward = player.moveForward > 0.0F;
		boolean isMovingBackward = player.moveForward < 0.0F;
		boolean isMovingRight = player.moveStrafing > 0.0F;
		boolean isMovingLeft = player.moveStrafing < 0.0F;
		boolean isMovingSideways = (isMovingLeft) || (isMovingRight);
		boolean isMovingStraight = (isMovingForward) || (isMovingBackward);
		if (isMoving) {
			if ((isMovingForward) && (!isMovingSideways)) {
				yaw += 0.0D;
			} else if ((isMovingBackward) && (!isMovingSideways)) {
				yaw += 180.0D;
			} else if ((isMovingForward) && (isMovingLeft)) {
				yaw += 45.0D;
			} else if (isMovingForward) {
				yaw -= 45.0D;
			} else if ((!isMovingStraight) && (isMovingLeft)) {
				yaw += 90.0D;
			} else if ((!isMovingStraight) && (isMovingRight)) {
				yaw -= 90.0D;
			} else if ((isMovingBackward) && (isMovingLeft)) {
				yaw += 135.0D;
			} else if (isMovingBackward) {
				yaw -= 135.0D;
			}
			yaw = Math.toRadians(yaw);
			player.motionX = (-Math.sin(yaw) * speed);
			player.motionZ = (Math.cos(yaw) * speed);
		}
	}

	public double getBaseMoveSpeed() {
		double baseSpeed = 0.2873;
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
			final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed)
					.getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
		}
		return baseSpeed;
	}

}
