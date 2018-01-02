package nl.x.api.utils.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;

/**
 * @author NullEX
 *
 */
public enum FileUtils {
	INSTANCE;

	public List<String> readFile(File file) throws Exception {
		List<String> result = Lists.newArrayList();
		Scanner s = new Scanner(file);
		while (s.hasNextLine()) {
			result.add(s.nextLine());
		}
		s.close();
		return result;
	}

	public FileWriter createWriter(File file) {
		return new FileWriter(file);
	}

	public class FileWriter {
		public File file;

		/**
		 * @param file
		 */
		public FileWriter(File file) {
			this.file = file;
		}

		/**
		 * Writes text to a file
		 * 
		 * @param text
		 */
		public void write(String text) {
			try {
				Files.write(Paths.get(file.toURI()), Arrays.asList(text));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void write(final File outputFile, final List<String> writeContent, final boolean overrideContent) {
			try {
				final Writer out = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
				for (final String outputLine : writeContent) {
					out.write(String.valueOf(outputLine) + System.getProperty("line.separator"));
				}
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
