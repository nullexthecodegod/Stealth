package nl.x.api.utils.misc;

import java.awt.image.BufferedImage;

import net.minecraft.util.ResourceLocation;

public class Texture {

	public ResourceLocation resource;
	public BufferedImage image;
	public int width, height;

	public Texture() {
	}

	public Texture(BufferedImage image) {
		this.image = image;
	}

}
