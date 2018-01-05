package nl.x.api.utils.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import nl.x.client.Client;

/**
 * @author NullEX
 *
 */
public enum FileUtils {
	INSTANCE;

	public static List<String> read(File inputFile) {
		ArrayList<String> readContent;
		readContent = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			try {
				String currentReadLine2;
				reader = new BufferedReader(new FileReader(inputFile));
				while ((currentReadLine2 = reader.readLine()) != null) {
					readContent.add(currentReadLine2);
				}
			} catch (Exception e) {

			}
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException var5_9) {
				// empty catch block
			}
		}
		return readContent;
	}

	public static void write(File outputFile, List<String> writeContent, boolean overrideContent) {
		BufferedWriter writer = null;
		try {
			try {
				writer = new BufferedWriter(new FileWriter(outputFile, !overrideContent));
				for (String outputLine : writeContent) {
					writer.write(outputLine);
					writer.flush();
					writer.newLine();
				}
			} catch (IOException outputLine) {
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (IOException var7_7) {
				}
			}
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException var7_9) {
			}
		}
	}

	public static File getConfigDir() {
		File file = new File(Minecraft.getMinecraft().mcDataDir, Client.info.get("name"));
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	public static File getConfigFile(String name) {
		File file = new File(FileUtils.getConfigDir(), String.format("%s.txt", name));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException var2_2) {
				// empty catch block
			}
		}
		return file;
	}

}
