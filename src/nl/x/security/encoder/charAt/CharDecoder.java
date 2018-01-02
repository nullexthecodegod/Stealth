package nl.x.security.encoder.charAt;

import java.util.Base64;

/**
 * @author NullEX
 *
 */
public class CharDecoder {

	public String decode(String text) {
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			tmp.append((char) (text.charAt(i) - 5));
		}
		String reversed = new StringBuffer(tmp.toString()).reverse().toString();
		return new String(Base64.getDecoder().decode(reversed));
	}

}
