package nl.x.client.gui.alt.New;

import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import nl.x.api.utils.misc.Authentication;
import nl.x.api.utils.misc.ConnectionUtil;
import nl.x.api.utils.misc.reflections.IAuthResult;
import nl.x.client.gui.alt.SessionUtils;
import nl.x.client.gui.alt.New.auth.AuthResponse;

public class Account {

	private final Minecraft mc = Minecraft.getMinecraft();
	private final JsonParser parser = new JsonParser();

	public String accessToken, clientToken, login, password, username = "";
	public boolean pending;
	public Session session;

	public Account setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	public Account setClientToken(String clientToken) {
		this.clientToken = clientToken;
		return this;
	}

	public Account setLogin(String login) {
		this.login = login;
		return this;
	}

	public Account setPassword(String password) {
		this.password = password;
		return this;
	}

	public Account setUsername(String username) {
		this.username = username;
		return this;
	}

	public Account setSession(Session session) {
		this.session = session;
		return this;
	}

	public void authenticate() {
		if (!pending) {
			pending = true;
			System.out.println("Attempting to authenticate " + login);
			new Thread() {
				@Override
				public void run() {
					if (accessToken != null && clientToken != null && session != null) {
						boolean validated = ConnectionUtil.validateToken(accessToken, clientToken);
						if (!validated) {
							accessToken = null;
							clientToken = null;
							session = null;
						}
					}

					if (session == null) {
						Authentication.authenticate(new IAuthResult() {
							@Override
							public String username() {
								return login;
							}

							@Override
							public String password() {
								return password;
							}

							@Override
							public void yes(AuthResponse auth) {
								SessionUtils.setSession(session = new Session(auth.selectedProfile.name,
										auth.selectedProfile.id, auth.accessToken, "legacy"));
								setAccessToken(auth.accessToken);
								setClientToken(auth.clientToken);
								setUsername(mc.getSession().getUsername());
								System.out.println("Successfully logged into " + login);
							}

							@Override
							public void no() {
								System.out.println("Failed to authenticate " + login);
							}

							@Override
							public void part() {
								SessionUtils.setSession(new Session(username(), username(), null, "legacy"));
								setUsername(mc.getSession().getUsername());
								System.out.println("Changed username to " + login);
							}
						});
					} else {
						SessionUtils.setSession(session);
						setUsername(mc.getSession().getUsername());
						System.out.println("Successfully logged into " + login);
					}
					pending = false;
				}
			}.start();
		}
	}

}
