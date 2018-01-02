package nl.x.api.event.impl;

import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class EventMove extends Event {
	public double x;
	public double z;
	public double y;

	/**
	 * @param x
	 * @param z
	 * @param y
	 */
	public EventMove(double x, double z, double y) {
		this.x = x;
		this.z = z;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
