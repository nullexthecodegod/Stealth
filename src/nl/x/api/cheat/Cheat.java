package nl.x.api.cheat;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import nl.x.api.cheat.value.Value;
import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class Cheat {
	public Minecraft mc = Minecraft.getMinecraft();
	private String name, suffix = "", desc;
	public List<Value> values = Lists.newArrayList();
	private int bind;
	private boolean shown, enabled;
	private Category category;

	/*
	 * Toggles the current cheat
	 */
	public void toggle() {
		enabled = !enabled;
		if (enabled) {
			enable();
		} else {
			disable();
		}
	}

	public void enable() {
	}

	public void disable() {
	}

	public void onEvent(Event e) {
	}

	public void setInfo(String name, String desc, int bind, boolean shown, Category category) {
		this.name = name;
		this.bind = bind;
		this.shown = shown;
		this.desc = desc;
		this.category = category;
	}

	public void addValue(Value value) {
		this.values.add(value);
	}

	public void addValue(Value... values) {
		for (Value v : values) {
			this.values.add(v);
		}
	}

	public List<Value> getValues() {
		return this.values;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @return the bind
	 */
	public int getBind() {
		return bind;
	}

	/**
	 * @return the shown
	 */
	public boolean isShown() {
		return shown;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param suffix
	 *            the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @param bind
	 *            the bind to set
	 */
	public void setBind(int bind) {
		this.bind = bind;
	}

	/**
	 * @param shown
	 *            the shown to set
	 */
	public void setShown(boolean shown) {
		this.shown = shown;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	public enum Category {
		Fight, Move, Player, Display;
	}
}
