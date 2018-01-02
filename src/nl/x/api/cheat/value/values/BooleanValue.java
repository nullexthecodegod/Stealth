package nl.x.api.cheat.value.values;

import nl.x.api.cheat.value.Value;

/**
 * @author NullEX
 *
 */
public class BooleanValue extends Value {

	private boolean value;

	/**
	 * @param name
	 * @param value
	 */
	public BooleanValue(String name, boolean value) {
		super(name);
		this.value = value;
	}

	public void toggle() {
		this.setValue(!this.getValue());
	}

	/**
	 * @return the value
	 */
	public boolean getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(boolean value) {
		this.value = value;
	}

}
