package nl.x.api.utils.misc;

import com.google.gson.JsonObject;

public class JsonUtil {

	public static JsonObject build(String... values) throws ArrayIndexOutOfBoundsException {
		JsonObject object = new JsonObject();
		for (int i = 0; i < values.length; i += 2) {
			object.addProperty(values[i], values[i + 1]);
		}
		return object;
	}

}
