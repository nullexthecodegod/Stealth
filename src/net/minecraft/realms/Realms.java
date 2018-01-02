package net.minecraft.realms;

import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings;

public class Realms {

	public static boolean isTouchScreen() {
		return Minecraft.getMinecraft().gameSettings.touchscreen;
	}

	public static Proxy getProxy() {
		return Minecraft.getMinecraft().getProxy();
	}

	public static String sessionId() {
		Session var0 = Minecraft.getMinecraft().getSession();
		return var0 == null ? null : var0.getSessionID();
	}

	public static String userName() {
		Session var0 = Minecraft.getMinecraft().getSession();
		return var0 == null ? null : var0.getUsername();
	}

	public static long currentTimeMillis() {
		return Minecraft.getSystemTime();
	}

	public static String getSessionId() {
		return Minecraft.getMinecraft().getSession().getSessionID();
	}

	public static String getName() {
		return Minecraft.getMinecraft().getSession().getUsername();
	}

	public static String uuidToName(String p_uuidToName_0_) {
		return Minecraft.getMinecraft().getSessionService().fillProfileProperties(
				new GameProfile(UUIDTypeAdapter.fromString(p_uuidToName_0_), (String) null), false).getName();
	}

	public static void setScreen(RealmsScreen p_setScreen_0_) {
		Minecraft.getMinecraft().displayGuiScreen(p_setScreen_0_.getProxy());
	}

	public static String getGameDirectoryPath() {
		return Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
	}

	public static int survivalId() {
		return WorldSettings.GameType.SURVIVAL.getID();
	}

	public static int creativeId() {
		return WorldSettings.GameType.CREATIVE.getID();
	}

	public static int adventureId() {
		return WorldSettings.GameType.ADVENTURE.getID();
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
