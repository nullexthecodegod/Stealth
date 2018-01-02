package nl.x.api.utils.misc.memory;

/**
 * @author NullEX
 *
 */
public class In {

	public String en(String data) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < data.length(); i++) {

			result.append((char) (data.charAt(i) + 2));
		}
		return result.toString();
	}

	public String de(String data) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < data.length(); i++) {
			result.append((char) (data.charAt(i) - 2));
		}
		return result.toString();
	}

}
