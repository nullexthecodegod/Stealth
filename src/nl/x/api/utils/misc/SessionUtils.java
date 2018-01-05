package nl.x.api.utils.misc;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class SessionUtils {
	private static Minecraft mc = Minecraft.getMinecraft();

	public static Session getSession() {
		try {
			Field session = mc.getClass().getDeclaredField("session");
			session.setAccessible(true);
			return (Session) session.get(mc);
		} catch (Exception e) {
			return null;
		}
	}

	public static void setSession(Session session) {
		try {
			Field f2 = mc.getClass().getDeclaredField("session");
			f2.setAccessible(true);
			f2.set(mc, session);
		} catch (Exception e) {
			System.out.println("WARNING! Could not set session.");
		}
	}
}
