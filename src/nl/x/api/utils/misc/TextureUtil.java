package nl.x.api.utils.misc;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class TextureUtil {

	private static final Minecraft mc = Minecraft.getMinecraft();
	public static final Map<String, Texture> cache = new HashMap<>();

	public static Texture download(String url) {
		if (cache.containsKey(url)) {
			Texture texture = cache.get(url);
			if (texture.image != null && texture.resource == null) {
				texture.resource = mc.getTextureManager().getDynamicTextureLocation(url,
						new DynamicTexture(texture.image));
				return null;
			}
			return texture;
		}
		Texture texture = new Texture();
		cache.put(url, texture);
		download(texture, url);
		return texture;
	}

	public static void download(Texture texture, String url) {
		new Thread(() -> {
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
				con.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
				InputStream stream = con.getInputStream();
				texture.image = ImageIO.read(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

}
