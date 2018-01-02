package nl.x.api.command.impl;

import nl.x.api.command.Command;

/**
 * @author NullEX
 *
 */
public class Plugins extends Command {

	/**
	 * @param name
	 * @param description
	 */
	public Plugins() {
		super("Plugins", "Lists all the plugins of the server");
	}

	/*
	 * @see nl.x.api.command.Command#execute(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void execute(String message, String[] args) {

	}

}
