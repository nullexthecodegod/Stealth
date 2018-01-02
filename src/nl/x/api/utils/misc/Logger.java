package nl.x.api.utils.misc;

/**
 * @author NullEX
 *
 */
public class Logger {
	private String name;

	/**
	 * @param message
	 */
	public Logger(String name) {
		this.name = name;
	}

	/**
	 * @return the message
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * Sends a message in the console using the parameters. (message, type)
	 * 
	 * @param message
	 * @param type
	 */
	public void log(String message, LogType type) {
		while (true) {
			if (type.equals(LogType.error)) {
				System.err.println("[" + type.name().toUpperCase() + "] [" + this.getName() + "] " + message);
				break;
			}
			System.out.println("[" + type.name().toUpperCase() + "] [" + this.getName() + "] " + message);
			break;
		}
	}

	public enum LogType {
		info, warning, error;
	}
}
