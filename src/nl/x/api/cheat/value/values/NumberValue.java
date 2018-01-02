package nl.x.api.cheat.value.values;

import nl.x.api.cheat.value.Value;

/**
 * @author NullEX
 *
 */
public class NumberValue extends Value {
	private Number value, min, max, inc;
	private boolean integer;

	/**
	 * @param name
	 * @param value
	 * @param min
	 * @param max
	 * @param inc
	 * @param integer
	 */
	public NumberValue(String name, double value, double min, double max, double inc) {
		super(name);
		this.value = value;
		this.min = min;
		this.max = max;
		this.inc = inc;
		this.integer = false;
	}

	/**
	 * @param name
	 * @param value
	 * @param min
	 * @param max
	 * @param inc
	 * @param integer
	 */
	public NumberValue(String name, int value, int min, int max, int inc) {
		super(name);
		this.value = value;
		this.min = min;
		this.max = max;
		this.inc = inc;
		this.integer = true;
	}

	/**
	 * @return the value
	 */
	public Number getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Number value) {
		this.value = value;
	}

	/**
	 * @return the min
	 */
	public Number getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(Number min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public Number getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(Number max) {
		this.max = max;
	}

	/**
	 * @return the inc
	 */
	public Number getInc() {
		return inc;
	}

	/**
	 * @param inc
	 *            the inc to set
	 */
	public void setInc(Number inc) {
		this.inc = inc;
	}

	/**
	 * @return the integer
	 */
	public boolean isInteger() {
		return integer;
	}

}
