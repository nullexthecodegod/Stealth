package nl.x.security.encoder.charAt;

import java.util.Base64;

/**
 * @author NullEX
 *
 */
public class CharEncoder {

	public String encode(String text) {
		StringBuilder tmp = new StringBuilder();
		String reverse = new StringBuffer(Base64.getEncoder().encodeToString(text.getBytes())).reverse().toString();
		for (int i = 0; i < reverse.length(); i++) {
			tmp.append((char) (reverse.charAt(i) + 5));
		}
		return tmp.toString().replace("\\", "\\" + "\\");
	}

}
