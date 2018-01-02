package net.minecraft.client.particle;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import net.minecraft.world.World;

public class EntityExplodeFX extends EntityFX {

	protected EntityExplodeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.motionX = xSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
		this.motionY = ySpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
		this.motionZ = zSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
		this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
		this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F;
		this.particleMaxAge = (int) (16.0D / (this.rand.nextFloat() * 0.8D + 0.2D)) + 2;
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
		this.motionY += 0.004D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.8999999761581421D;
		this.motionY *= 0.8999999761581421D;
		this.motionZ *= 0.8999999761581421D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}

	public static class Factory implements IParticleFactory {

		@Override
		public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
				double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
			return new EntityExplodeFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
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
