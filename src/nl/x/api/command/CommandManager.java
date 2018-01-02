package nl.x.api.command;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import nl.x.api.utils.misc.ClassUtils;

/**
 * @author NullEX
 *
 */
public enum CommandManager {
	INSTANCE;

	private ArrayList<Command> buffer = Lists.newArrayList();

	public void init() {
		for (Class<?> clazz : ClassUtils.INSTANCE.findClass(this.getClass().getPackage().getName(), Command.class)) {
			try {
				this.buffer.add((Command) clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the buffer
	 */
	public ArrayList<Command> getBuffer() {
		return buffer;
	}

}
