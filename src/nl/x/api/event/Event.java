package nl.x.api.event;

import nl.x.api.cheat.Cheat;
import nl.x.client.cheat.CheatManager;

/**
 * @author NullEX
 *
 */
public class Event {
	private boolean canceled;

	/**
	 * @return the canceled
	 */
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * @param canceled
	 *            the canceled to set
	 */
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public void fire() {
		CheatManager.INSTANCE.buffer.stream().filter(Cheat::isEnabled).forEach(c -> c.onEvent(this));
	}

	public enum State {
		pre, post;
	}

}
