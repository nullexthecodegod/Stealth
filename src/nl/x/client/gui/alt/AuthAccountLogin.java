package nl.x.client.gui.alt;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.util.Session;

public class AuthAccountLogin {
	public static String login(String name, String password) {
		YggdrasilAuthenticationService a2 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
		YggdrasilUserAuthentication b2 = (YggdrasilUserAuthentication) a2.createUserAuthentication(Agent.MINECRAFT);
		b2.setUsername(name);
		b2.setPassword(password);
		String displayText = "§aLogged in";
		if (password == "") {
			SessionUtils.setSession(new Session(name, name, "0", "legacy"));
			return "\u00a7aLogged In Cracked )";
		}
		try {
			b2.logIn();
			SessionUtils.setSession(new Session(b2.getSelectedProfile().getName(),
					b2.getSelectedProfile().getId().toString(), b2.getAuthenticatedToken(), "legacy"));
		} catch (AuthenticationException var7) {
			displayText = !var7.getMessage().contains("\u00a74LOGIN FAILED!")
					&& !var7.getMessage().toLowerCase().contains("account migrated") ? "\u00a7cNo connection!"
							: "\u00a7cWrong password! (" + name + ")";
		} catch (NullPointerException var8) {
			displayText = "\u00a7c error: This alt doesn't have a username!";
		}
		return displayText;
	}
}
