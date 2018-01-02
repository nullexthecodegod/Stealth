package nl.x.api.event.impl;

import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class EventVelocity extends Event {
	private double x, y, z;

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public EventVelocity(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
