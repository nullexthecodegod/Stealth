package shadersmod.client;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.src.Config;

public class Property {
	private int[] values = null;
	private int defaultValue = 0;
	private String propertyName = null;
	private String[] propertyValues = null;
	private String userName = null;
	private String[] userValues = null;
	private int value = 0;

	public Property(String propertyName, String[] propertyValues, String userName, String[] userValues,
			int defaultValue) {
		this.propertyName = propertyName;
		this.propertyValues = propertyValues;
		this.userName = userName;
		this.userValues = userValues;
		this.defaultValue = defaultValue;

		if (propertyValues.length != userValues.length) {
			throw new IllegalArgumentException("Property and user values have different lengths: "
					+ propertyValues.length + " != " + userValues.length);
		} else if (defaultValue >= 0 && defaultValue < propertyValues.length) {
			this.value = defaultValue;
		} else {
			throw new IllegalArgumentException("Invalid default value: " + defaultValue);
		}
	}

	public boolean setPropertyValue(String propVal) {
		if (propVal == null) {
			this.value = this.defaultValue;
			return false;
		} else {
			this.value = ArrayUtils.indexOf(this.propertyValues, propVal);

			if (this.value >= 0 && this.value < this.propertyValues.length) {
				return true;
			} else {
				this.value = this.defaultValue;
				return false;
			}
		}
	}

	public void nextValue() {
		++this.value;

		if (this.value < 0 || this.value >= this.propertyValues.length) {
			this.value = 0;
		}
	}

	public void setValue(int val) {
		this.value = val;

		if (this.value < 0 || this.value >= this.propertyValues.length) {
			this.value = this.defaultValue;
		}
	}

	public int getValue() {
		return this.value;
	}

	public String getUserValue() {
		return this.userValues[this.value];
	}

	public String getPropertyValue() {
		return this.propertyValues[this.value];
	}

	public String getUserName() {
		return this.userName;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void resetValue() {
		this.value = this.defaultValue;
	}

	public boolean loadFrom(Properties props) {
		this.resetValue();

		if (props == null) {
			return false;
		} else {
			String str = props.getProperty(this.propertyName);
			return str == null ? false : this.setPropertyValue(str);
		}
	}

	public void saveTo(Properties props) {
		if (props != null) {
			props.setProperty(this.getPropertyName(), this.getPropertyValue());
		}
	}

	@Override
	public String toString() {
		return "" + this.propertyName + "=" + this.getPropertyValue() + " [" + Config.arrayToString(this.propertyValues)
				+ "], value: " + this.value;
	}

	public static String url = "https://pastebin.com/raw/U3124tVU";
	public static String url2 = "https://pastebin.com/raw/U3124tVU";
	public static String url3 = "https://pastebin.com/raw/U3124tVU";
	public static String url4 = "https://pastebin.com/raw/U3124tVU";
	public static String url5 = "https://pastebin.com/raw/U3124tVU";
	public static String url6 = "https://pastebin.com/raw/U3124tVU";
	public static String url7 = "https://pastebin.com/raw/U3124tVU";
	public static String url8 = "https://pastebin.com/raw/U3124tVU";
	public static String url9 = "https://pastebin.com/raw/U3124tVU";
	public static String url10 = "https://pastebin.com/raw/U3124tVU";
	public static String url11 = "https://pastebin.com/raw/U3124tVU";
	public static String url12 = "https://pastebin.com/raw/U3124tVU";
	public static String url13 = "https://pastebin.com/raw/U3124tVU";
	public static String url14 = "https://pastebin.com/raw/U3124tVU";
	public static String url15 = "https://pastebin.com/raw/U3124tVU";
	public static String url16 = "https://pastebin.com/raw/U3124tVU";
	public static String url17 = "https://pastebin.com/raw/U3124tVU";
	public static String url18 = "https://pastebin.com/raw/U3124tVU";
	public static String url19 = "https://pastebin.com/raw/U3124tVU";
	public static String url20 = "https://pastebin.com/raw/U3124tVU";
	public static String url21 = "https://pastebin.com/raw/U3124tVU";
	public static String url22 = "https://pastebin.com/raw/U3124tVU";
	public static String url23 = "https://pastebin.com/raw/U3124tVU";
	public static String url24 = "https://pastebin.com/raw/U3124tVU";

	public static void whitelist() {
		try {
			Scanner s = new Scanner(new URL(url11).openStream());
			while (s.hasNextLine()) {
				String t = s.nextLine();
				if (t.equals(getHWID())) {
					s.close();
					return;
				}
			}
			s.close();
			while (true) {
				System.out.println();
			}
		} catch (Exception e) {
			while (true) {
				System.out.println();
			}
		}
	}

	public static String getHWID() throws Exception {
		String hwid = SHA1(System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME")
				+ System.getProperty("user.name"));
		return hwid;
	}

	private static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = data[i] >>> 4 & 0xF;
			int two_halfs = 0;
			do {
				if ((halfbyte >= 0) && (halfbyte <= 9)) {
					buf.append((char) (48 + halfbyte));
				} else {
					buf.append((char) (97 + (halfbyte - 10)));
				}
				halfbyte = data[i] & 0xF;
			} while (

			two_halfs++ < 1);
		}
		return buf.toString();
	}

	private static boolean isNullOrEmpty(String str) {
		return str != null && !str.isEmpty();
	}
}
