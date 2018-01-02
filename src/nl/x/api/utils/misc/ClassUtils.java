package nl.x.api.utils.misc;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Lists;

/**
 * @author NullEX
 *
 */
public enum ClassUtils {
	INSTANCE;

	/**
	 * Returns a array of class from a package using reflections
	 * 
	 * @param path
	 * @param extend
	 * @return Class array
	 */
	public ArrayList<Class> findClass(String path, Class extend) {
		Reflections reflections = new Reflections(path, new Scanner[0]);
		Set<Class> classes = reflections.getSubTypesOf(extend);
		ArrayList<Class> returns = Lists.newArrayList();
		for (Class clazz : classes) {
			returns.add(clazz);
		}
		return returns;
	}

}
