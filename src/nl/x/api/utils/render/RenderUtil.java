package nl.x.api.utils.render;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import feather.render.FactorTess;
import feather.render.Tessellator;
import feather.render.Vbo;
import feather.util.BufferUtils;
import feather.util.TextureUtils;
import feather.util.Utils;
import feather.util.texture.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Util;

/**
 * @author I think arrow, might be someone else
 *         <p>
 *         sorry for taking it
 *         </p>
 */
public enum RenderUtil implements Utils {
	INSTANCE;

	public final Shapes shapes = new Shapes();
	public float factor = 0.5f;
	public static final FactorTess tess;

	public void enableBlending() {
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
	}

	public void disableBlending() {
		GlStateManager.disableBlend();
		GL11.glDisable(2848);
	}

	public void enableLineBlending() {
		this.enableBlending();
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
	}

	public void enableDepth() {
		GlStateManager.enableDepth();
		GL11.glDepthMask(true);
	}

	public void disableDepth() {
		GlStateManager.disableDepth();
		GL11.glDepthMask(false);
	}

	public void enableLighting() {
		GlStateManager.enableAlpha();
		GlStateManager.enableLighting();
	}

	public void disableLighting() {
		GlStateManager.disableLighting();
		GlStateManager.disableAlpha();
	}

	public void enableArray(int array) {
		GL11.glEnableClientState(array);
	}

	public void enableGradient() {
		this.enableArray(32886);
		GL11.glShadeModel(7425);
	}

	public void disableGradient() {
		this.disableArray(32886);
		GL11.glShadeModel(7424);
	}

	public void disableArray(int array) {
		GL11.glDisableClientState(array);
	}

	public void bindBuffer(int buffer) {
		GL15.glBindBuffer(34962, buffer);
	}

	public void setColor(int color) {
		final float alpha = (color >> 24 & 0xFF) / 255.0f;
		final float red = (color >> 16 & 0xFF) / 255.0f;
		final float green = (color >> 8 & 0xFF) / 255.0f;
		float blue = (color & 0xFF) / 255.0f;
		GlStateManager.color(red, green, blue, alpha);
	}

	public void setColor(int color, float opacity) {
		final float alpha = (color >> 24 & 0xFF) / 255.0f;
		final float red = (color >> 16 & 0xFF) / 255.0f;
		final float green = (color >> 8 & 0xFF) / 255.0f;
		final float blue = (color & 0xFF) / 255.0f;
		GlStateManager.color(red, green, blue, alpha * opacity);
	}

	public void drawTessRect(int mode, float x, float y, float x1, float y1) {
		this.bindRect(RenderUtil.tess, x, y, x1, y1);
		RenderUtil.tess.draw(mode);
	}

	public static void drawRect(double left, double top, double right, double bottom, final int color) {
		if (left < right) {
			final double var5 = left;
			left = right;
			right = var5;
		}
		if (top < bottom) {
			final double var5 = top;
			top = bottom;
			bottom = var5;
		}
		final float var6 = (color >> 24 & 0xFF) / 255.0f;
		final float var7 = (color >> 16 & 0xFF) / 255.0f;
		final float var8 = (color >> 8 & 0xFF) / 255.0f;
		final float var9 = (color & 0xFF) / 255.0f;
		final WorldRenderer worldRenderer = net.minecraft.client.renderer.Tessellator.getInstance().getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(var7, var8, var9, var6);
		worldRenderer.startDrawingQuads();
		worldRenderer.addVertex(left, bottom, 0.0);
		worldRenderer.addVertex(right, bottom, 0.0);
		worldRenderer.addVertex(right, top, 0.0);
		worldRenderer.addVertex(left, top, 0.0);
		net.minecraft.client.renderer.Tessellator.getInstance().draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1,
			final int outer, final int inner) {
		drawRect(x, y, x2, y2, inner);
		final float f = (outer >> 24 & 0xFF) / 255.0f;
		final float f2 = (outer >> 16 & 0xFF) / 255.0f;
		final float f3 = (outer >> 8 & 0xFF) / 255.0f;
		final float f4 = (outer & 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(f2, f3, f4, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(1);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public static Point calculateMouseLocation() {
		final Minecraft minecraft = Minecraft.getMinecraft();
		int scale = minecraft.gameSettings.guiScale;
		if (scale == 0) {
			scale = 1000;
		}
		int scaleFactor;
		for (scaleFactor = 0; scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320
				&& minecraft.displayHeight / (scaleFactor + 1) >= 240; ++scaleFactor) {
		}
		return new Point(Mouse.getX() / scaleFactor,
				minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1);
	}

	public static void drawGear(final int x, final int y, final double r, final double ri, final int teeth) {
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glDisable(2884);
		GL11.glBlendFunc(770, 771);
		double da = 6.283185307179586 / teeth / 4.0;
		final double r2 = r - 0.5;
		final double r3 = r + 0.5;
		GL11.glBegin(8);
		for (int i = 0; i <= teeth; ++i) {
			final double angle = i * 2.0 * 3.141592653589793 / teeth;
			GL11.glVertex2d(ri * Math.cos(angle) + x, ri * Math.sin(angle) + y);
			GL11.glVertex2d(r2 * Math.cos(angle) + x, r2 * Math.sin(angle) + y);
			GL11.glVertex2d(ri * Math.cos(angle) + x, ri * Math.sin(angle) + y);
			GL11.glVertex2d(r2 * Math.cos(angle + 3.0 * da) + x, r2 * Math.sin(angle + 3.0 * da) + y);
		}
		GL11.glEnd();
		GL11.glBegin(7);
		da = 6.283185307179586 / teeth / 4.0;
		for (int i = 0; i < teeth; ++i) {
			final double angle = i * 2.0 * 3.141592653589793 / teeth;
			GL11.glVertex2d(r2 * Math.cos(angle) + x, r2 * Math.sin(angle) + y);
			GL11.glVertex2d(r3 * Math.cos(angle + da) + x, r3 * Math.sin(angle + da) + y);
			GL11.glVertex2d(r3 * Math.cos(angle + 2.0 * da) + x, r3 * Math.sin(angle + 2.0 * da) + y);
			GL11.glVertex2d(r2 * Math.cos(angle + 3.0 * da) + x, r2 * Math.sin(angle + 3.0 * da) + y);
		}
		GL11.glEnd();
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glEnable(2884);
	}

	public void bindRect(Tessellator rend, float x, float y, float x1, float y1) {
		rend.vertex(x, y, 0.0f).vertex(x, y1, 0.0f).vertex(x1, y1, 0.0f).vertex(x1, y, 0.0f).bind();
	}

	public void drawGrad(int mode, float x, float y, float x1, float y1, int topColor, int bottomColor) {
		this.bindGrad(RenderUtil.tess, x, y, x1, y1, topColor, bottomColor);
		this.enableGradient();
		RenderUtil.tess.draw(mode);
		this.disableGradient();
	}

	public void bindGrad(Tessellator rend, float x, float y, float x1, float y1, int topColor, int bottomColor) {
		rend.color(topColor).vertex(x1, y, 0.0f).vertex(x, y, 0.0f);
		rend.color(bottomColor).vertex(x, y1, 0.0f).vertex(x1, y1, 0.0f).bind();
	}

	public void bindHorGrad(Tessellator rend, int x, int y, int x1, int y1, int leftColor, int rightColor) {
		rend.color(leftColor).vertex(x, y, 0.0f).vertex(x, y1, 0.0f);
		rend.color(rightColor).vertex(x1, y1, 0.0f).vertex(x1, y, 0.0f).bind();
	}

	public void drawHealthCircle(Tessellator tess, float x, float y, float radius, float quality, float health) {
		GlStateManager.color(1 - health + 0.2F, health + 0.2F, 0.2F, 1);
		drawCircle(tess, GL11.GL_TRIANGLE_FAN, x, y, radius, health, quality);
		GlStateManager.color(0, 0, 0, 1);
		GL11.glLineWidth(1);
		drawCircle(tess, GL11.GL_LINE_LOOP, x, y, radius, 1, quality);
	}

	public void drawCircle(Tessellator tess, int mode, float x, float y, float radius, float degree, float quality) {
		final float twicePi = 2F * (float) Math.PI;
		if (degree != 1)
			tess.vertex(x, y, 0);
		for (int i = MathHelper.ceiling_float_int(quality * degree); i > -1; i--)
			tess.vertex(x - (radius * MathHelper.cos(i * twicePi / quality + (float) Math.PI / 2F)),
					y - (radius * MathHelper.sin(i * twicePi / quality + (float) Math.PI / 2F)), 0);
		tess.end(mode);
	}

	public void drawTex(int mode, float x, float y, float u, float v, float width, float height) {
		this.bindTex(RenderUtil.tess, x, y, u, v, width, height, 256.0f);
		GlStateManager.scale(this.factor, this.factor, this.factor);
		RenderUtil.tess.draw(mode);
		GlStateManager.scale(1.0f / this.factor, 1.0f / this.factor, 1.0f / this.factor);
	}

	public void bindTex(Tessellator rend, float x, float y, float u, float v, float width, float height, float img) {
		x /= this.factor;
		y /= this.factor;
		rend.texture((u + width) / img, v / img).vertex(x + width, y, 0.0f).texture(u / img, v / img).vertex(x, y,
				0.0f);
		rend.texture(u / img, (v + height) / img).vertex(x, y + height, 0.0f)
				.texture((u + width) / img, (v + height) / img).vertex(x + width, y + height, 0.0f).bind();
	}

	public void bindColor(Tessellator tess, int r, int g, int b, int a) {
		if (BufferUtils.isBigEndian) {
			tess.color(r << 24 | g << 16 | b << 8 | a);
		} else {
			tess.color(a << 24 | b << 16 | g << 8 | r);
		}
	}

	public void bindColor(Tessellator tess, float r, float g, float b, float a) {
		this.bindColor(tess, (int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
	}

	public float getEntityRatio(Entity e) {
		return Math.min(e.ticksExisted, 20.0f) / 20.0f;
	}

	public boolean isOver(int i, int j, int x, int y, int x1, int y1) {
		return i >= x && i <= x1 && j >= y && j <= y1;
	}

	public int loadTexture(String location) {
		try {
			int tex = GL11.glGenTextures();
			BufferedImage img = ImageIO.read(RenderUtil.class.getResourceAsStream("/assets/minecraft/" + location));
			GL11.glBindTexture(3553, tex);
			GL11.glTexParameteri(3553, 10240, 9729);
			GL11.glTexParameteri(3553, 10241, 9987);
			GL11.glTexParameteri(3553, 33169, 1);
			GL11.glTexImage2D(3553, 0, 6408, 256, 256, 0, 32993, 5121, (IntBuffer) null);
			int[] data = new int[65536];
			img.getRGB(0, 0, 256, 256, data, 0, 256);
			TextureUtils tex2 = Texture.tex;
			TextureUtils.buffer.clear();
			TextureUtils tex3 = Texture.tex;
			TextureUtils.buffer.put(data);
			TextureUtils tex4 = Texture.tex;
			TextureUtils.buffer.flip();
			int n = 3553;
			int n2 = 0;
			int n3 = 0;
			int n4 = 0;
			int n5 = 256;
			int n6 = 256;
			int n7 = 32993;
			int n8 = 5121;
			TextureUtils tex5 = Texture.tex;
			GL11.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, TextureUtils.buffer);
			return tex;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String getHiddenString(String str) {
		return StringUtils.repeat('*', str.length());
	}

	public void setIcon(String path) {
		Util.EnumOS var1 = Util.getOSType();
		if (var1 != Util.EnumOS.OSX) {
			InputStream var2 = null;
			InputStream var3 = null;
			InputStream var4 = null;
			try {
				var2 = RenderUtil.class.getResourceAsStream(path + "icon_16x16.png");
				var3 = RenderUtil.class.getResourceAsStream(path + "icon_32x32.png");
				var4 = RenderUtil.class.getResourceAsStream(path + "icon_64x64.png");
				if (var2 != null && var3 != null) {
					Display.setIcon(new ByteBuffer[] { this.readImageToBuffer(var2), this.readImageToBuffer(var3),
							this.readImageToBuffer(var4) });
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(var2);
				IOUtils.closeQuietly(var3);
				IOUtils.closeQuietly(var4);
			}
		}
	}

	private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
		BufferedImage var2 = ImageIO.read(imageStream);
		int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[]) null, 0, var2.getWidth());
		ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
		int[] var5 = var3;
		int var6 = var3.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			int var8 = var5[var7];
			var4.putInt(var8 << 8 | var8 >> 24 & 255);
		}

		var4.flip();
		return var4;
	}

	public int createShader(String shaderCode, int shaderType) {
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
			if (shader == 0) {
				return 0;
			}
			ARBShaderObjects.glShaderSourceARB(shader, shaderCode);
			ARBShaderObjects.glCompileShaderARB(shader);
			if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
				throw new RuntimeException("Error creating shader: " + this.getShaderLogInfo(shader));
			}
			return shader;
		} catch (Exception exc) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			return 0;
		}
	}

	public String getShaderLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
	}

	static {
		tess = new FactorTess(4);
	}

	public static class Shapes {
		public Shape cube;
		public Shape pyramid;
		public Shape octosquare;

		private Shapes() {
			(this.cube = new Shape(3,
					new byte[] { 0, 3, 2, 1, 2, 5, 6, 1, 6, 7, 0, 1, 0, 7, 4, 3, 4, 7, 6, 5, 2, 3, 4, 5 }))
							.compile(this.getBox(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
			float root = (float) (Math.sqrt(3.0) / 2.0);
			float a = 0.5f / root;
			float b = a / 2.0f;
			float c = (float) Math.sqrt(root * root - b * b) / 2.0f;
			(this.pyramid = new Shape(3, new byte[] { 3, 1, 0, 3, 0, 2, 0, 1, 2, 3, 2, 1 })).compile(0.0f, -c, a, -0.5f,
					-c, -b, 0.5f, -c, -b, 0.0f, c, 0.0f);
			(this.octosquare = new Shape(2, new byte[] { 0, 9, 6, 3 })).compile(-0.5f, -0.5f, -0.3f, -0.5f, 0.3f, -0.5f,
					0.5f, -0.5f, 0.5f, -0.3f, 0.5f, 0.3f, 0.5f, 0.5f, 0.3f, 0.5f, -0.3f, 0.5f, -0.5f, 0.5f, -0.5f, 0.3f,
					-0.5f, -0.3f);
		}

		private FloatBuffer getBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
			FloatBuffer vertices = BufferUtils.createDirectBuffer(96).asFloatBuffer();
			vertices.put(new float[] { minX, minY, minZ, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, minX,
					maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, minX, minY, maxZ }).flip();
			return vertices;
		}

		public class Shape extends Vbo {
			private FloatBuffer points;
			private ByteBuffer order;

			private Shape(int dimensions, byte[] order) {
				super(dimensions, GL15.glGenBuffers());
				this.points = null;
				this.order = BufferUtils.createDirectBuffer(order.length);
				this.order.put(order).flip();
			}

			@Override
			public void compile(FloatBuffer buffer) {
				super.compile(buffer);
				this.points = buffer;
			}

			@Override
			public void draw(int mode) {
				this.draw(mode, this.order);
			}
		}
	}
}
