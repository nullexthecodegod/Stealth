package nl.x.api.utils.misc.reflections;

public class Connection {

	public String url, json;
	public String[] keys = new String[0];
	public String[] params = new String[0];

	public Connection setURL(String url) {
		this.url = url;
		return this;
	}

	public Connection setKeys(String... keys) {
		this.keys = keys;
		return this;
	}

	public Connection setParams(String... params) {
		this.params = params;
		return this;
	}

	public Connection setJson(String json) {
		this.json = json;
		return this;
	}

	public String getPayload() {
		if (json != null) {
			return json;
		}
		StringBuilder payload = new StringBuilder();
		for (int i = 0; i < keys.length; i++) {
			payload.append(keys[i]).append("=").append(params[i]);
		}
		return payload.toString();
	}

}
