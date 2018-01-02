package nl.x.client.cheat;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.utils.misc.ClassUtils;
import nl.x.client.cheat.cheats.none.DefaultCheat;

/**
 * @author NullEX
 *
 */
public enum CheatManager {
	// Instance used for getting the 'Cheat Manager' instance from runtime
	INSTANCE;

	// Array buffer used for getting the list of ' Cheats '
	public ArrayList<Cheat> buffer = Lists.newArrayList();

	public DefaultCheat none = new DefaultCheat();

	/*
	 * Load's the cheat class from the current class package and sub package's
	 */
	public void load() {
		System.out.println("80%");
		for (Class clazz : ClassUtils.INSTANCE.findClass(this.getClass().getPackage().getName(), Cheat.class)) {
			try {
				if (!clazz.isAnnotationPresent(Info.class)) {
					System.err.printf("Cheat class \"%s\" does not have a CheatManifest annotated.\n",
							clazz.getSimpleName());
					continue;
				}
				Info a = (Info) clazz.getAnnotation(Info.class);
				Cheat cheat = (Cheat) clazz.newInstance();
				String category = clazz.getPackage().getName().substring("nl.x.client.cheat.cheats.".length())
						.substring(0, 1).toUpperCase()
						+ clazz.getPackage().getName().substring("nl.x.client.cheat.cheats.".length()).substring(1)
								.toLowerCase();
				String name = clazz.getSimpleName();
				cheat.setInfo(a.name().equalsIgnoreCase("null") ? clazz.getSimpleName() : a.name(), a.desc(), a.bind(),
						a.shown(), (a.category().equals(Category.None) ? Category.valueOf(category) : a.category()));
				if (a.enabled()) {
					cheat.setEnabled(true);
				}

				System.out.println("Added class " + clazz.getSimpleName());
				buffer.add(cheat);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println("99%");
	}

	/**
	 * Uses java 8 to multi thread searching (only use this for simple tasks
	 * sense it eats cpu's)
	 * 
	 * @param clazz
	 * @return
	 */
	public static Cheat getCheatParallel(Class<? extends Cheat> clazz) {
		return CheatManager.INSTANCE.buffer.parallelStream().filter(c -> c.getClass().equals(clazz)).findFirst()
				.orElse(CheatManager.INSTANCE.none);
	}

	/**
	 * Uses java 8 to multi thread searching (only use this for simple tasks
	 * sense it eats cpu's)
	 * 
	 * @param name
	 * @return
	 */
	public static Cheat getCheatParallel(String name) {
		return CheatManager.INSTANCE.buffer.parallelStream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(CheatManager.INSTANCE.none);
	}

	public static Cheat getCheat(Class<? extends Cheat> clazz) {
		return CheatManager.INSTANCE.buffer.stream().filter(c -> c.getClass().equals(clazz)).findFirst()
				.orElse(CheatManager.INSTANCE.none);
	}

	public static Cheat getCheat(String name) {
		return CheatManager.INSTANCE.buffer.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(CheatManager.INSTANCE.none);
	}
}
