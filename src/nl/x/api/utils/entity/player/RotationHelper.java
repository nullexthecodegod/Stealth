package nl.x.api.utils.entity.player;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public enum RotationHelper {
	INSTANCE;
	public Minecraft mc = Minecraft.getMinecraft();

	public float[] getRotations(Entity ent) {
		double x = ent.posX;
		double z = ent.posZ;
		double y = ent.posY;
		return this.getRotationFromPosition(x, z, y);
	}

	public float[] getRotations(Vec3 origin, Vec3 position) {
		final Vec3 difference = position.subtract(origin);
		final double distance = difference.flat().lengthVector();
		final float yaw = (float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f;
		final float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));
		return new float[] { yaw, pitch };
	}

	public float[] getRotations(Vec3 position) {
		return this.getRotations(mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0),
				position);
	}

	public float[] getRotations(BlockPos pos) {
		return this.getRotations(mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0),
				new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
	}

	public float[] getAverageRotations(List<EntityLivingBase> targetList) {
		double posX = 0.0;
		double posY = 0.0;
		double posZ = 0.0;
		for (Entity ent : targetList) {
			posX += ent.posX;
			posY += ent.boundingBox.maxY - 2.0;
			posZ += ent.posZ;
		}
		return new float[] { this.getRotationFromPosition(posX /= targetList.size(), posZ /= targetList.size(),
				posY /= targetList.size())[0], this.getRotationFromPosition(posX, posZ, posY)[1] };
	}

	public float[] getRotationFromPosition(double x, double z, double y) {
		double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
		double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
		double yDiff = y - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
		double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793);
		return new float[] { yaw, pitch };
	}

	public float[] getRotationsNeeded(Entity entity) {
		if (entity == null) {
			return null;
		}
		double xSize = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double ySize = entity.posY + entity.getEyeHeight() / 2.0D
				- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		double zSize = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;

		double theta = MathHelper.sqrt_double(xSize * xSize + zSize * zSize);
		float yaw = (float) (Math.atan2(zSize, xSize) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(ySize, theta) * 180.0D / 3.141592653589793D);
		return new float[] {
				(Minecraft.getMinecraft().thePlayer.rotationYaw
						+ MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw))
						% 360.0F,
				(Minecraft.getMinecraft().thePlayer.rotationPitch
						+ MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch))
						% 360.0F };
	}

	public boolean isWithinFOV(Entity entity, float fov) {
		float[] rotations = this.getRotationsNeeded(entity);
		float yawDifference = this.getYawDifference(Minecraft.getMinecraft().thePlayer.rotationYaw % 360.0F,
				rotations[0]);
		return (yawDifference < fov) && (yawDifference > -fov);
	}

	public float getYawDifference(float currentYaw, float neededYaw) {
		float yawDifference = neededYaw - currentYaw;
		if (yawDifference > 180.0F) {
			yawDifference = -(360.0F - neededYaw + currentYaw);
		} else if (yawDifference < -180.0F) {
			yawDifference = 360.0F - currentYaw + neededYaw;
		}
		return yawDifference;
	}

	public float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
		float g = 0.006f;
		float sqrt = velocity * velocity * velocity * velocity
				- g * (g * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
		return (float) Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
	}

	public float getNewAngle(float angle) {
		if ((angle %= 360.0f) >= 180.0f) {
			angle -= 360.0f;
		}
		if (angle < -180.0f) {
			angle += 360.0f;
		}
		return angle;
	}

	public float getDistanceBetweenAngles(float angle1, float angle2) {
		float angle = Math.abs(angle1 - angle2) % 360.0f;
		if (angle > 180.0f) {
			angle = 360.0f - angle;
		}
		return angle;
	}

	public float getPitch(Entity ent) {
		double x2 = ent.posX - Minecraft.getMinecraft().thePlayer.posX;
		double y2 = ent.posY - Minecraft.getMinecraft().thePlayer.posY;
		double z2 = ent.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double pitch = Math.asin(y2 /= Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ent)) * 57.29577951308232;
		pitch = -pitch;
		return (float) pitch;
	}

	public float getYaw(Entity ent) {
		double x2 = ent.posX - Minecraft.getMinecraft().thePlayer.posX;
		double y2 = ent.posY - Minecraft.getMinecraft().thePlayer.posY;
		double z2 = ent.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double yaw = Math.atan2(x2, z2) * 57.29577951308232;
		yaw = -yaw;
		return (float) yaw;
	}

	public float getPitchChange(Entity entity) {
		double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double deltaY = entity.posY - 2.2 + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
		double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
		double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
		return -MathHelper
				.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float) pitchToEntity) - 2.5f;
	}

	public float getYawChange(Entity entity) {
		double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double yawToEntity = 0.0;
		yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX))
				: (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX))
						: Math.toDegrees(-Math.atan(deltaX / deltaZ)));
		return MathHelper.wrapAngleTo180_float(-Minecraft.getMinecraft().thePlayer.rotationYaw - (float) yawToEntity);
	}

}
