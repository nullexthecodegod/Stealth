package nl.x.api.command.impl;

import nl.x.api.command.Command;

/**
 * @author NullEX
 *
 */
public class Help extends Command {

	/**
	 * @param name
	 * @param description
	 */
	public Help() {
		super("Help", "Gives help about commands or cheats");
	}

	/*
	 * @see nl.x.api.command.Command#execute(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void execute(String message, String[] args) {
	}

}
