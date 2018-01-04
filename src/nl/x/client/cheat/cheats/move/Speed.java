package nl.x.client.cheat.cheats.move;

import com.google.common.collect.Lists;

import net.minecraft.potion.Potion;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.Event.State;
import nl.x.api.event.impl.EventMove;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.entity.player.MoveUtils;
import nl.x.api.utils.misc.PepsiUtils;

/**
 * @author NullEX
 *
 */
@Info(name = "Speed")
public class Speed extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode",
			Lists.newArrayList("OnGround", "Sloth", "Hop", "Fiona YPort", "Mineplex"), "OnGround");
	private int stage = 1;
	private double moveSpeed = 0.2873;
	private double lastDist;

	public Speed() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate event = (EventUpdate) e;
			boolean hack = mc.thePlayer.ticksExisted % 2 == 0;
			boolean move = Math.abs(mc.thePlayer.moveForward) + Math.abs(mc.thePlayer.moveStrafing) != 0.0;
			this.setSuffix(this.mode.getValue().toString());
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "onground":
					if (event.getState().equals(State.pre)) {
						if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f || mc.theWorld == null
								|| mc.thePlayer.isOnLiquid() || mc.thePlayer.isInLiquid() || !mc.thePlayer.onGround) {
							return;
						}
						event.setY(event.getY()
								+ (mc.thePlayer.ticksExisted % 2 == 0 ? this.getHighestOffset(0.42) : 0.0));
						mc.thePlayer.motionY -= 0.01;
					}
					break;
				case "sloth":
					if (mc.thePlayer.moveForward != 0.0 || mc.thePlayer.moveStrafing != 0.0) {
						if (event.getState().equals(State.pre)) {
							if (mc.thePlayer.onGround) {
								if (!hack) {
									event.setY(event.getY() + 0.42);
								}
								MoveUtils.setSpeed(MoveUtils.INSTANCE.getBaseMoveSpeed() * (hack ? 7 : 0.705));
							}
							if (hack) {
								mc.thePlayer.motionY = -1;
							}
						}
					}
					break;
				case "hop":
					double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
					double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
					this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
					break;
				case "fiona yport":
					break;
				case "mineplex":
					if (mc.thePlayer.hasInput()) {
						if (mc.thePlayer.onGround) {
							mc.thePlayer.jump();
						}
						MoveUtils.setSpeed(MoveUtils.INSTANCE.getBaseMoveSpeed() + (hack ? 0.1 : 0.01));
					} else {
						mc.thePlayer.motionX = 0;
						mc.thePlayer.motionZ = 0;
					}
					break;
			}
		}
		if (e instanceof EventMove) {
			EventMove event = (EventMove) e;
			switch (this.mode.getValue().toString().toLowerCase()) {
				case "hop":
					mc.timer.timerSpeed = 1.0f;
					if (PepsiUtils.round(this.mc.thePlayer.posY - ((int) this.mc.thePlayer.posY), 3) == PepsiUtils
							.round(0.138, 3)) {
						this.mc.thePlayer.motionY -= 1.0;
						event.y -= 0.0631;
						this.mc.thePlayer.posY -= 0.0631;
					}
					if (this.stage == 2
							&& (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
						this.mc.thePlayer.motionY = 0.39936;
						event.y = 0.39936;
						this.moveSpeed *= 2.1499999;
					} else if (this.stage == 3) {
						double difference = 0.66 * (this.lastDist - MoveUtils.INSTANCE.getBaseMoveSpeed());
						this.moveSpeed = this.lastDist - difference;
					} else {
						if (this.mc.theWorld
								.getCollidingBoundingBoxes(this.mc.thePlayer,
										this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0))
								.size() > 0 || this.mc.thePlayer.isCollidedVertically) {
							this.stage = 1;
						}
						this.moveSpeed = this.lastDist - this.lastDist / 159.0;
					}
					this.moveSpeed = Math.max(this.moveSpeed, MoveUtils.INSTANCE.getBaseMoveSpeed());
					float forward = this.mc.thePlayer.movementInput.moveForward;
					float strafe = this.mc.thePlayer.movementInput.moveStrafe;
					float yaw = mc.thePlayer.rotationYaw;
					if (forward == 0.0f && strafe == 0.0f) {
						event.x = 0.0;
						event.z = 0.0;
					} else if (forward != 0.0f) {
						if (strafe >= 1.0f) {
							yaw += forward > 0.0f ? -45 : 45;
							strafe = 0.0f;
						} else if (strafe <= -1.0f) {
							yaw += forward > 0.0f ? 45 : -45;
							strafe = 0.0f;
						}
						if (forward > 0.0f) {
							forward = 1.0f;
						} else if (forward < 0.0f) {
							forward = -1.0f;
						}
					}
					double mx = Math.cos(Math.toRadians(yaw + 90.0f));
					double mz = Math.sin(Math.toRadians(yaw + 90.0f));
					event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz) * 0.99;
					event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx) * 0.99;
					this.mc.thePlayer.stepHeight = 0.6f;
					if (forward == 0.0f && strafe == 0.0f) {
						event.x = 0.0;
						event.z = 0.0;
					} else if (forward != 0.0f) {
						if (strafe >= 1.0f) {
							yaw += forward > 0.0f ? -45 : 45;
							strafe = 0.0f;
						} else if (strafe <= -1.0f) {
							yaw += forward > 0.0f ? 45 : -45;
							strafe = 0.0f;
						}
						if (forward > 0.0f) {
							forward = 1.0f;
						} else if (forward < 0.0f) {
							forward = -1.0f;
						}
					}
					++this.stage;
					break;
				case "onground":
					if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f || mc.theWorld == null
							|| mc.thePlayer.isInWater() || !mc.thePlayer.onGround) {
						return;
					}
					double speed = this.getMovementSpeed() * (mc.thePlayer.ticksExisted % 2 != 0 ? 1.0 : 1.69);
					double x = -Math.sin(mc.thePlayer.getDirection()) * speed;
					double z = Math.cos(mc.thePlayer.getDirection()) * speed;
					event.x = x;
					event.z = z;
					break;
			}
		}
		super.onEvent(e);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#disable()
	 */
	@Override
	public void disable() {
		mc.timer.timerSpeed = 1.0f;
		this.moveSpeed = MoveUtils.INSTANCE.getBaseMoveSpeed();
		this.lastDist = 0.0;
		this.stage = 4;
		super.disable();
	}

	private static final double WALK_SPEED = 0.21585;
	private static final double RUN_SPEED = 0.2806;

	public double getBaseMovementSpeed() {
		return mc.thePlayer.isSprinting() ? 0.2806 : 0.21585;
	}

	public double getMovementSpeed() {
		if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			return this.getBaseMovementSpeed();
		}
		return this.getBaseMovementSpeed() * 1.0
				+ 0.06 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
	}

	public boolean willCollide(double x, double z) {
		for (int i = 1; i < 4; ++i) {
			if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
					mc.thePlayer.getEntityBoundingBox().offset(x * i, 0.0, z * i)).isEmpty())
				continue;
			return true;
		}
		return false;
	}

	public double getHighestOffset(double max) {
		for (double i = 0.0; i < max; i += 0.01) {
			for (int offset : new int[] { -2, -1, 0, 1, 2 }) {
				if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox()
						.offset(mc.thePlayer.motionX * offset, i, mc.thePlayer.motionZ * offset)).size() <= 0)
					continue;
				return i - 0.01;
			}
		}
		return max;
	}

}
