package nl.x.security.checks.impl;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author NullEX
 *
 */
public enum ByteCheck {
	INSTANCE;

	public long check(Class src) {
		File jf = new File(src.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		if (jf.isFile()) {
			try {
				JarFile j = new JarFile(jf);
				Enumeration<JarEntry> entries = j.entries();
				while (entries.hasMoreElements()) {
					JarEntry e = entries.nextElement();
					if (e.getName().endsWith(".class") && e.getName().equals(src.getName())) {
						j.close();
						return e.getSize();
					}
				}
				j.close();
			} catch (Exception e) {
			}
		}
		return 0;
	}

}
