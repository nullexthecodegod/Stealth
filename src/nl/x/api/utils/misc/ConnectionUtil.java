package nl.x.api.utils.misc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import nl.x.api.utils.misc.reflections.Connection;

public class ConnectionUtil {

	private static final String USERNAME_URL = "https://api.mojang.com/user/profiles/minecraft/%s";
	private static final String USERNAME_URL_AT = "https://api.mojang.com/user/profiles/minecraft/%s?at=0";
	private static final String HISTORY_URL = "https://api.mojang.com/user/profiles/%s/names";
	private static final String AUTH_AUTHENTICATE = "https://authserver.mojang.com/authenticate";
	private static final String AUTH_VALIDATE = "https://authserver.mojang.com/validate";
	private static final String AUTH_REFRESH = "https://authserver.mojang.com/refresh";

	private static JsonParser parser = new JsonParser();

	public static String get(Connection connection) {
		try {
			String payload = connection.getPayload();

			URL obj = new URL(connection.url + (payload.isEmpty() ? "" : ("?" + payload)));
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "application/json");

			BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = input.readLine()) != null) {
				response.append(line).append("\n");
			}
			input.close();

			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String post(Connection connection) {
		try {
			URL obj = new URL(connection.url);
			URLConnection con = obj.openConnection();
			if (con instanceof HttpsURLConnection) {
				((HttpsURLConnection) con).setRequestMethod("POST");
			} else if (con instanceof HttpURLConnection) {
				((HttpURLConnection) con).setRequestMethod("POST");
			}
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoInput(true);
			con.setDoOutput(true);

			DataOutputStream output = new DataOutputStream(con.getOutputStream());
			output.writeBytes(connection.getPayload());
			output.flush();
			output.close();

			BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = input.readLine()) != null) {
				response.append(line).append("\n");
			}
			input.close();

			return response.toString();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static String getUUID(String name) {
		Connection connection = new Connection().setURL(String.format(USERNAME_URL, name));
		String json = get(connection);

		if (json.isEmpty()) {
			Connection newConnection = new Connection().setURL(String.format(USERNAME_URL_AT, name));
			json = get(newConnection);
		}

		if (json.isEmpty()) {
			return null;
		}

		try {
			JsonElement jel = parser.parse(json);
			if (jel.isJsonObject()) {
				JsonObject job = jel.getAsJsonObject();
				return job.get("id").getAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<String[]> getHistory(String uuid) {
		List<String[]> list = new ArrayList<>();
		Connection connection = new Connection().setURL(String.format(HISTORY_URL, uuid));
		String json = get(connection);

		if (json.isEmpty()) {
			return list;
		}

		try {
			JsonElement jel = parser.parse(json);
			if (jel.isJsonArray()) {
				JsonArray jar = jel.getAsJsonArray();
				jar.forEach(e -> {
					if (e.isJsonObject()) {
						JsonObject job = e.getAsJsonObject();
						String name = job.get("name").getAsString();
						String changedToAt = "-1";
						if (job.has("changedToAt")) {
							changedToAt = String.valueOf(job.get("changedToAt").getAsLong());
						}
						list.add(new String[] { name, changedToAt });
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static boolean validateToken(String accessToken, String clientToken) {
		JsonObject job = JsonUtil.build("accessToken", accessToken, "clientToken", clientToken);

		Connection connection = new Connection().setURL(AUTH_VALIDATE).setJson(job.toString());
		String response = post(connection);

		return response != null && response.isEmpty();
	}

}
