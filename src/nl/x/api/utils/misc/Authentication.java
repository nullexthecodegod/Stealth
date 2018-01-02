package nl.x.api.utils.misc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import nl.x.api.utils.misc.reflections.Connection;
import nl.x.api.utils.misc.reflections.IAuthResult;
import nl.x.client.gui.alt.New.auth.AuthResponse;

public class Authentication {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final JsonParser parser = new JsonParser();

	public static void authenticate(IAuthResult result) {
		if (result.password().isEmpty()) {
			result.part();
			return;
		}
		JsonObject payload = new JsonObject();

		JsonObject agent = new JsonObject();
		agent.addProperty("name", "Minecraft");
		agent.addProperty("version", 1);

		payload.add("agent", agent);
		payload.addProperty("username", result.username());
		payload.addProperty("password", result.password());

		Connection connection = new Connection().setURL("https://authserver.mojang.com/authenticate")
				.setJson(payload.toString());
		String response = ConnectionUtil.post(connection);

		if (response != null && !response.isEmpty()) {
			JsonObject job = parser.parse(response).getAsJsonObject();
			AuthResponse authResponse = new AuthResponse(job);

			payload = new JsonObject();

			payload.addProperty("accessToken", authResponse.accessToken);
			payload.addProperty("clientToken", authResponse.clientToken);

			connection.setURL("https://authserver.mojang.com/refresh");
			connection.setJson(payload.toString());
			response = ConnectionUtil.post(connection);

			if (response != null && !response.isEmpty()) {
				job = parser.parse(response).getAsJsonObject();

				authResponse.accessToken = job.get("accessToken").getAsString();

				result.yes(authResponse);
				return;
			}
		}
		result.no();

		/*
		 * YggdrasilMinecraftSessionService service =
		 * (YggdrasilMinecraftSessionService) mc.getSessionService();
		 * UserAuthentication auth =
		 * service.getAuthenticationService().createUserAuthentication(Agent.
		 * MINECRAFT); auth.setUsername(result.username());
		 * auth.setPassword(result.password()); try { auth.logIn();
		 * result.yes(auth); } catch (AuthenticationException e) {
		 * e.printStackTrace(); result.no(); }
		 */
	}

}
