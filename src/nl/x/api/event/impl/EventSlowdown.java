package nl.x.api.event.impl;

import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class EventSlowdown extends Event {
	private From from;
	private double modifier;

	/**
	 * @param from
	 * @param modifier
	 */
	public EventSlowdown(From from, double modifier) {
		this.from = from;
		this.modifier = modifier;
	}

	/**
	 * @return the modifier
	 */
	public double getModifier() {
		return modifier;
	}

	/**
	 * @param modifier
	 *            the modifier to set
	 */
	public void setModifier(double modifier) {
		this.modifier = modifier;
	}

	/**
	 * @return the from
	 */
	public From getFrom() {
		return from;
	}

	public enum From {
		item, water, web, soulsand
	}
}
