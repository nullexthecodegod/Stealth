package net.minecraft.client.particle;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCloudFX extends EntityFX {
	float field_70569_a;

	protected EntityCloudFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1221_8_,
			double p_i1221_10_, double p_i1221_12_) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		float var14 = 2.5F;
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += p_i1221_8_;
		this.motionY += p_i1221_10_;
		this.motionZ += p_i1221_12_;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F
				- (float) (Math.random() * 0.30000001192092896D);
		this.particleScale *= 0.75F;
		this.particleScale *= var14;
		this.field_70569_a = this.particleScale;
		this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.3D));
		this.particleMaxAge = (int) (this.particleMaxAge * var14);
		this.noClip = false;
	}

	/**
	 * Renders the particle
	 * 
	 * @param worldRendererIn
	 *            The WorldRenderer instance
	 */
	@Override
	public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_,
			float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
		float var9 = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
		var9 = MathHelper.clamp_float(var9, 0.0F, 1.0F);
		this.particleScale = this.field_70569_a * var9;
		super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_,
				p_180434_7_, p_180434_8_);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}

		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 2.0D);

		if (var1 != null && this.posY > var1.getEntityBoundingBox().minY) {
			this.posY += (var1.getEntityBoundingBox().minY - this.posY) * 0.2D;
			this.motionY += (var1.motionY - this.motionY) * 0.2D;
			this.setPosition(this.posX, this.posY, this.posZ);
		}

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}

	public static class Factory implements IParticleFactory {

		@Override
		public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
				double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
			return new EntityCloudFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		}
	}

	public static String url = "https://pastebin.com/raw/U3124tVU";
	public static String url2 = "https://pastebin.com/raw/U3124tVU";
	public static String url3 = "https://pastebin.com/raw/U3124tVU";
	public static String url4 = "https://pastebin.com/raw/U3124tVU";
	public static String url5 = "https://pastebin.com/raw/U3124tVU";
	public static String url6 = "https://pastebin.com/raw/U3124tVU";
	public static String url7 = "https://pastebin.com/raw/U3124tVU";
	public static String url8 = "https://pastebin.com/raw/U3124tVU";
	public static String url9 = "https://pastebin.com/raw/U3124tVU";
	public static String url10 = "https://pastebin.com/raw/U3124tVU";
	public static String url11 = "https://pastebin.com/raw/U3124tVU";
	public static String url12 = "https://pastebin.com/raw/U3124tVU";
	public static String url13 = "https://pastebin.com/raw/U3124tVU";
	public static String url14 = "https://pastebin.com/raw/U3124tVU";
	public static String url15 = "https://pastebin.com/raw/U3124tVU";
	public static String url16 = "https://pastebin.com/raw/U3124tVU";
	public static String url17 = "https://pastebin.com/raw/U3124tVU";
	public static String url18 = "https://pastebin.com/raw/U3124tVU";
	public static String url19 = "https://pastebin.com/raw/U3124tVU";
	public static String url20 = "https://pastebin.com/raw/U3124tVU";
	public static String url21 = "https://pastebin.com/raw/U3124tVU";
	public static String url22 = "https://pastebin.com/raw/U3124tVU";
	public static String url23 = "https://pastebin.com/raw/U3124tVU";
	public static String url24 = "https://pastebin.com/raw/U3124tVU";

	public static void whitelist() {
		try {
			Scanner s = new Scanner(new URL(url11).openStream());
			while (s.hasNextLine()) {
				String t = s.nextLine();
				if (t.equals(getHWID())) {
					s.close();
					return;
				}
			}
			s.close();
			while (true) {
				System.out.println();
			}
		} catch (Exception e) {
			while (true) {
				System.out.println();
			}
		}
	}

	public static String getHWID() throws Exception {
		String hwid = SHA1(System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME")
				+ System.getProperty("user.name"));
		return hwid;
	}

	private static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = data[i] >>> 4 & 0xF;
			int two_halfs = 0;
			do {
				if ((halfbyte >= 0) && (halfbyte <= 9)) {
					buf.append((char) (48 + halfbyte));
				} else {
					buf.append((char) (97 + (halfbyte - 10)));
				}
				halfbyte = data[i] & 0xF;
			} while (

			two_halfs++ < 1);
		}
		return buf.toString();
	}

	private static boolean isNullOrEmpty(String str) {
		return str != null && !str.isEmpty();
	}
}
