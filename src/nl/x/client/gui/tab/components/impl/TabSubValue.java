package nl.x.client.gui.tab.components.impl;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import nl.x.api.cheat.value.Value;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.cheat.value.values.NumberValue;
import nl.x.client.gui.tab.components.TabComponent;

/**
 * @author NullEX
 *
 */
public class TabSubValue extends TabComponent {
	public Value value;

	/**
	 * @param x
	 * @param y
	 * @param value
	 */
	public TabSubValue(int x, int y, Value value) {
		super("", x, y);
		this.value = value;
	}

	/*
	 * @see nl.x.client.gui.tab.components.TabComponent#render()
	 */
	@Override
	public void render() {
		String name = this.value.getName();
		boolean change = false;
		if (this.value instanceof BooleanValue) {
			change = ((BooleanValue) this.value).getValue();
		} else if (this.value instanceof ArrayValue) {
			name = this.value.getName() + ":§7 " + ((ArrayValue) this.value).getValue();
		} else if (value instanceof NumberValue) {
			NumberValue v = (NumberValue) value;
			name += ":§7 " + (v.isInteger() ? v.getValue().intValue() : v.getValue().doubleValue());
		}
		this.setRender(name);
		mc.fontRendererObj.drawStringWithShadow(this.getRender(), this.getX() - 1, this.getY(),
				(change ? Color.LIGHT_GRAY.getRGB() : -1));
	}

	/*
	 * @see nl.x.client.gui.tab.components.TabComponent#action(int)
	 */
	@Override
	public void action(int key) {
		switch (key) {
			case Keyboard.KEY_RIGHT:
				if (this.value instanceof BooleanValue) {
					BooleanValue v = (BooleanValue) this.value;
					v.setValue(!v.getValue());
					return;
				} else if (this.value instanceof ArrayValue) {
					ArrayValue m = (ArrayValue) this.value;
					int next = 0;
					for (int i = 0; i < m.getValues().size(); i++) {
						if (m.getValue() == m.getValues().get(i)) {
							next = (i + 1 >= m.getValues().size() ? 0 : i + 1);
							break;
						}
					}
					m.setValue(m.getValues().get(next));
				} else if (this.value instanceof NumberValue) {
					NumberValue n = (NumberValue) this.value;
					if (n.getValue().doubleValue() + n.getInc().doubleValue() < n.getMax().doubleValue()) {
						n.setValue(n.getValue().doubleValue() + n.getInc().doubleValue());
					} else if (n.getValue().doubleValue() - n.getInc().doubleValue() > n.getMin().doubleValue()) {
						n.setValue(n.getValue().doubleValue() - n.getInc().doubleValue());
					}
				}
				break;
			case Keyboard.KEY_RETURN:
				if (this.value instanceof BooleanValue) {
					BooleanValue v = (BooleanValue) this.value;
					v.setValue(!v.getValue());
					return;
				} else if (this.value instanceof ArrayValue) {
					ArrayValue m = (ArrayValue) this.value;
					int next = 0;
					for (int i = 0; i < m.getValues().size(); i++) {
						if (m.getValue() == m.getValues().get(i)) {
							next = (i + 1 >= m.getValues().size() ? 0 : i + 1);
							break;
						}
					}
					m.setValue(m.getValues().get(next));
				} else if (this.value instanceof NumberValue) {
					NumberValue n = (NumberValue) this.value;
					if (n.getValue().doubleValue() + n.getInc().doubleValue() < n.getMax().doubleValue()) {
						n.setValue(n.getValue().doubleValue() + n.getInc().doubleValue());
					} else if (n.getValue().doubleValue() - n.getInc().doubleValue() > n.getMin().doubleValue()) {
						n.setValue(n.getValue().doubleValue() - n.getInc().doubleValue());
					}
				}
				break;
		}
	}

}
