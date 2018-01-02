package nl.x.api.event.impl;

import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class EventSafewalk extends Event {
	private boolean isSafe = false;
	private State state;

	/**
	 * @param state
	 */
	public EventSafewalk(State state) {
		this.state = state;
	}

	/**
	 * @return the isSafe
	 */
	public boolean isSafe() {
		return isSafe;
	}

	/**
	 * @param isSafe
	 *            the isSafe to set
	 */
	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

}
