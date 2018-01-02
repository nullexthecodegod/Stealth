package nl.x.client.gui.click.old.button.impl.button.impl.option;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.value.Value;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.cheat.value.values.NumberValue;
import nl.x.api.utils.misc.PepsiUtils;
import nl.x.api.utils.render.RenderUtil;
import nl.x.client.gui.click.old.button.impl.button.Button;

/**
 * @author NullEX
 *
 */
public class OptionButton extends Button {
	public int width;

	public Value value;

	/**
	 * @param x
	 * @param y
	 * @param value
	 */
	public OptionButton(int x, int y, Value value) {
		super(x, y);
		this.value = value;
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#draw(int, int)
	 */
	@Override
	public void draw(int mouseX, int mouseY) {
		this.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.value.getName());
		String name = this.value.getName();
		boolean change = false;
		if (this.value instanceof BooleanValue) {
			change = ((BooleanValue) this.value).getValue();
		} else if (this.value instanceof ArrayValue) {
			name = this.value.getName() + ": " + ((ArrayValue) this.value).getValue();
		} else if (value instanceof NumberValue) {
			NumberValue v = (NumberValue) value;
			name += ": " + (v.isInteger() ? v.getValue().intValue() : v.getValue().doubleValue());
		}
		this.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(name + " ");
		if (mouseX < this.getX() + this.getMax() - 2 && mouseX > this.getX() - 2 && mouseY < this.getY() + 9
				&& mouseY > this.getY() - 4 && !(value instanceof NumberValue)) {
			Gui.drawRect(this.getX() - 2, this.getY() - 2, this.getX() + this.getMax() - 3, this.getY() + 9,
					Integer.MIN_VALUE);
		}
		if (value instanceof NumberValue) {
			NumberValue v = (NumberValue) value;
			int drag = (int) (v.getValue().doubleValue() / v.getMax().doubleValue() * this.getMax()) - 3;
			RenderUtil.INSTANCE.drawBorderedRect(this.getX() - 2f, this.getY() - 2f, this.getX() + (float) drag,
					this.getY() + 9f, this.isHover(mouseX, mouseY) ? 0.5f : 0.1f, -12303292, -44976);
			if (Mouse.isButtonDown(0) && this.isHover(mouseX, mouseY)) {
				double offset = PepsiUtils.round(
						(double) (mouseX - this.getX()) / (double) (this.getMax() - 3) * v.getMax().doubleValue(),
						v.isInteger() ? 1 : 2);
				if (offset > v.getMax().doubleValue()) {
					offset = v.getMax().doubleValue();
				} else if (offset < v.getMin().doubleValue()) {
					offset = v.getMin().doubleValue();
				}
				v.setValue(offset);

			}
		}
		Minecraft.getMinecraft().fontRendererObj.drawString(name, this.getX(), this.getY(),
				(change ? new Color(198, 41, 41).getRGB() : -1));
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#type(char, int)
	 */
	@Override
	public void type(char typedChar, int keyCode) {
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#click(int, int, int)
	 */
	@Override
	public void click(int mouseX, int mouseY, int mouseButton) {
		if (this.isHover(mouseX, mouseY)) {
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
			}
		}
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#release(int, int,
	 * int)
	 */
	@Override
	public void release(int mouseX, int mouseY, int mouseButton) {
	}

	public boolean isHover(int mouseX, int mouseY) {
		return mouseX < this.getX() + this.getMax() - 2 && mouseX > this.getX() - 2 && mouseY < this.getY() + 9
				&& mouseY > this.getY() - 4;
	}

}
