package nl.x.api.event.impl;

import nl.x.api.command.Command;
import nl.x.api.command.CommandManager;
import nl.x.api.event.Event;
import nl.x.client.Client;

/**
 * @author NullEX
 *
 */
public class EventChat extends Event {
	private String message;

	/**
	 * @param message
	 */
	public EventChat(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * @see nl.x.api.event.Event#fire()
	 */
	@Override
	public void fire() {
		if (this.getMessage().startsWith(Client.info.get("prefix"))) {
			for (Command cmd : CommandManager.INSTANCE.getBuffer()) {
				if (this.getMessage().split(" ")[0]
						.equalsIgnoreCase(String.valueOf(Client.info.get("prefix") + cmd.getName()))) {
					cmd.execute(this.getMessage(), this.getMessage().split(" "));
					this.setCanceled(true);
					break;
				}
			}
			if (!this.isCanceled()) {
				Command.log("§cInvalid Command.");
				this.setCanceled(true);
			}
		}
		super.fire();
	}

}
