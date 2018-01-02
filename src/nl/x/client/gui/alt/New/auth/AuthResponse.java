package nl.x.client.gui.alt.New.auth;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AuthResponse {

	public String accessToken, clientToken;
	public List<Profile> profiles;
	public Profile selectedProfile;

	public AuthResponse(String accessToken, String clientToken, List<Profile> profiles, Profile selectedProfile) {
		this.accessToken = accessToken;
		this.clientToken = clientToken;
		this.profiles = profiles;
		this.selectedProfile = selectedProfile;
	}

	public AuthResponse(JsonObject job) {
		this.accessToken = job.get("accessToken").getAsString();
		this.clientToken = job.get("clientToken").getAsString();
		this.profiles = new ArrayList<>();
		if (job.has("availableProfiles")) {
			JsonArray jar = job.getAsJsonArray("availableProfiles");
			for (int i = 0; i < jar.size(); i++) {
				JsonObject jobProfile = jar.get(i).getAsJsonObject();
				profiles.add(new Profile(jobProfile.get("id").getAsString(), jobProfile.get("name").getAsString(),
						jobProfile.has("legacy") && jobProfile.get("legacy").getAsBoolean()));
			}
		}
		if (job.has("selectedProfile")) {
			JsonObject jobProfile = job.getAsJsonObject("selectedProfile");
			selectedProfile = new Profile(jobProfile.get("id").getAsString(), jobProfile.get("name").getAsString(),
					jobProfile.has("legacy") && jobProfile.get("legacy").getAsBoolean());
		}
	}

}
