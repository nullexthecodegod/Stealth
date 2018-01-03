package net.minecraft.client.main;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import nl.x.api.utils.misc.Logger;
import nl.x.api.utils.misc.Logger.LogType;

public class Main {
	public static Logger logger = new Logger("Misc");

	public static void main(String[] p_main_0_) {
		// whitelist();
		Minecraft mc;
		logger.log("0%", LogType.info);
		System.setProperty("java.net.preferIPv4Stack", "true");
		logger.log("Set the ipv4Stack", LogType.info);
		OptionParser var1 = new OptionParser();
		var1.allowsUnrecognizedOptions();
		var1.accepts("demo");
		var1.accepts("fullscreen");
		var1.accepts("checkGlErrors");
		ArgumentAcceptingOptionSpec var2 = var1.accepts("server").withRequiredArg();
		ArgumentAcceptingOptionSpec var3 = var1.accepts("port").withRequiredArg().ofType(Integer.class)
				.defaultsTo(Integer.valueOf(25565), new Integer[0]);
		ArgumentAcceptingOptionSpec var4 = var1.accepts("gameDir").withRequiredArg().ofType(File.class)
				.defaultsTo(new File("."), new File[0]);
		ArgumentAcceptingOptionSpec var5 = var1.accepts("assetsDir").withRequiredArg().ofType(File.class);
		ArgumentAcceptingOptionSpec var6 = var1.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
		ArgumentAcceptingOptionSpec var7 = var1.accepts("proxyHost").withRequiredArg();
		ArgumentAcceptingOptionSpec var8 = var1.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0])
				.ofType(Integer.class);
		ArgumentAcceptingOptionSpec var9 = var1.accepts("proxyUser").withRequiredArg();
		ArgumentAcceptingOptionSpec var10 = var1.accepts("proxyPass").withRequiredArg();
		/**
		 * This seems to bee where javassist kills itself with speed
		 * 
		 * (might be caused by the getSystemTime() method inside of the
		 * minecraft class)
		 * 
		 * as i suspected it was infact not javassist shiting itself but was
		 * java
		 * 
		 */
		ArgumentAcceptingOptionSpec var11 = var1.accepts("username").withRequiredArg().defaultsTo("Player",
				new String[0]);
		ArgumentAcceptingOptionSpec var12 = var1.accepts("uuid").withRequiredArg();
		ArgumentAcceptingOptionSpec var13 = var1.accepts("accessToken").withRequiredArg().required();
		ArgumentAcceptingOptionSpec var14 = var1.accepts("version").withRequiredArg().required();
		ArgumentAcceptingOptionSpec var15 = var1.accepts("width").withRequiredArg().ofType(Integer.class)
				.defaultsTo(Integer.valueOf(854), new Integer[0]);
		ArgumentAcceptingOptionSpec var16 = var1.accepts("height").withRequiredArg().ofType(Integer.class)
				.defaultsTo(Integer.valueOf(480), new Integer[0]);
		ArgumentAcceptingOptionSpec var17 = var1.accepts("userProperties").withRequiredArg().required();
		ArgumentAcceptingOptionSpec var18 = var1.accepts("assetIndex").withRequiredArg();
		ArgumentAcceptingOptionSpec var19 = var1.accepts("userType").withRequiredArg().defaultsTo("legacy",
				new String[0]);
		NonOptionArgumentSpec var20 = var1.nonOptions();
		OptionSet var21 = var1.parse(p_main_0_);
		List var22 = var21.valuesOf(var20);

		if (!var22.isEmpty()) {
			System.out.println("Completely ignored arguments: " + var22);
		}

		String var23 = (String) var21.valueOf(var7);
		Proxy var24 = Proxy.NO_PROXY;

		if (var23 != null) {
			try {
				var24 = new Proxy(Type.SOCKS, new InetSocketAddress(var23, ((Integer) var21.valueOf(var8)).intValue()));
			} catch (Exception var43) {
				;
			}
		}

		final String var25 = (String) var21.valueOf(var9);
		final String var26 = (String) var21.valueOf(var10);

		if (!var24.equals(Proxy.NO_PROXY) && isNullOrEmpty(var25) && isNullOrEmpty(var26)) {
			Authenticator.setDefault(new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(var25, var26.toCharArray());
				}
			});
		}

		int var27 = ((Integer) var21.valueOf(var15)).intValue();
		int var28 = ((Integer) var21.valueOf(var16)).intValue();
		boolean var29 = var21.has("fullscreen");
		boolean var30 = var21.has("checkGlErrors");
		boolean var31 = var21.has("demo");
		String var32 = (String) var21.valueOf(var14);
		PropertyMap var33 = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Serializer()).create()
				.fromJson((String) var21.valueOf(var17), PropertyMap.class);
		File var34 = (File) var21.valueOf(var4);
		File var35 = var21.has(var5) ? (File) var21.valueOf(var5) : new File(var34, "assets/");
		File var36 = var21.has(var6) ? (File) var21.valueOf(var6) : new File(var34, "resourcepacks/");
		String var37 = var21.has(var12) ? (String) var12.value(var21) : (String) var11.value(var21);
		String var38 = var21.has(var18) ? (String) var18.value(var21) : null;
		String var39 = (String) var21.valueOf(var2);
		Integer var40 = (Integer) var21.valueOf(var3);
		Session var41 = new Session((String) var11.value(var21), var37, (String) var13.value(var21),
				(String) var19.value(var21));
		Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
			@Override
			public void run() {
				Minecraft.stopIntegratedServer();
			}
		});
		mc = new Minecraft(new GameConfiguration(new GameConfiguration.UserInformation(var41, var33, var24),
				new GameConfiguration.DisplayInformation(var27, var28, var29, var30),
				new GameConfiguration.FolderInformation(var34, var36, var35, var38),
				new GameConfiguration.GameInformation(var31, var32),
				new GameConfiguration.ServerInformation(var39, var40.intValue())));
		logger.log("5%", LogType.info);
		Thread.currentThread().setName("Client thread");
		mc.run();
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
