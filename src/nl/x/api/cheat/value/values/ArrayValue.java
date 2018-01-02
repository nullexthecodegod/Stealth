package nl.x.api.cheat.value.values;

import java.util.ArrayList;

import nl.x.api.cheat.value.Value;

public class ArrayValue extends Value {
	private ArrayList<Object> values;
	private Object value;

	public ArrayValue(String name, ArrayList<Object> values, Object value) {
		super(name);
		this.values = values;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ArrayList<Object> getValues() {
		return values;
	}

}
