package nl.x.api.event.impl;

import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class EventUpdate extends Event {
	private State state;
	/**
	 * {@link net.minecraft.client.Minecraft#thePlayer} angles.
	 */
	private float yaw, pitch;

	private double y;

	/**
	 * {@link net.minecraft.client.Minecraft#thePlayer} onGround state.
	 */
	private boolean onGround;

	/**
	 * Constructor for all extenders.
	 *
	 * This event is called when the game runs the update method for
	 * {@link net.minecraft.client.Minecraft#thePlayer}.
	 *
	 * @param yaw
	 *            player yaw
	 * @param pitch
	 *            player pitch
	 * @param state
	 *            event state
	 */
	public EventUpdate(float yaw, float pitch, double y, boolean onGround, State state) {
		this.state = state;
		this.y = y;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	/**
	 * Get's the players current yaw.
	 *
	 * @return player yaw
	 */
	public float getYaw() {
		return yaw;
	}

	/**
	 * Set's the players current yaw without changing it locally.
	 *
	 * @param yaw
	 *            player yaw
	 */
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	/**
	 * Get's the players current pitch.
	 *
	 * @return player pitch
	 */
	public float getPitch() {
		return pitch;
	}

	/**
	 * Set's the players current pitch without changing it locally.
	 *
	 * @param pitch
	 *            player pitch
	 */
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	/**
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Get's the players current ground relation.
	 *
	 * @return on ground
	 */
	public boolean isOnGround() {
		return onGround;
	}

	/**
	 * Set's the players current ground relation.
	 *
	 * @param onGround
	 *            on ground
	 */
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

}
