package nl.x.client.gui.tab.components.impl;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.value.Value;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.utils.render.RenderUtil;
import nl.x.client.gui.tab.components.TabComponent;

/**
 * @author NullEX
 *
 */
public class TabValue extends TabComponent {
	public Value value;
	public TabSubValue subValue;
	public int width;

	/**
	 * @param render
	 * @param x
	 * @param y
	 * @param value
	 */
	public TabValue(String render, int x, int y, Value value) {
		super(render, x, y);
		this.value = value;
		this.subValue = new TabSubValue(x, y, value);
	}

	/*
	 * @see nl.x.client.gui.tab.components.TabComponent#render()
	 */
	@Override
	public void render() {
		boolean change = false;
		if (this.value instanceof BooleanValue) {
			change = ((BooleanValue) this.value).getValue();
		}
		mc.fontRendererObj.drawStringWithShadow(this.getRender(), this.getX(), this.getY(),
				(change ? Color.LIGHT_GRAY.getRGB() : -1));
		if (this.isExtended()) {
			this.subValue.setX(this.getX() + this.getWidth() + 3);
			int max = mc.fontRendererObj.getStringWidth(this.subValue.getRender() + " ");
			int x = this.getX() + this.getWidth();
			RenderUtil.drawBorderedRect(x, subValue.getY() - 2, x + max, this.getY() + (12 * 1), 2,
					Color.BLACK.getRGB(), Color.DARK_GRAY.getRGB());
			Gui.drawRect(x + 1, subValue.getY() - 1, x + max - 1, subValue.getY() + 10,
					new Color(247, 84, 41).getRGB());
			this.subValue.render();
		}
	}

	/*
	 * @see nl.x.client.gui.tab.components.TabComponent#action(int)
	 */
	@Override
	public void action(int key) {
		if (this.isExtended()) {
			if (key == Keyboard.KEY_LEFT) {
				this.setExtended(false);
				return;
			}
			this.subValue.action(key);
			return;
		}
		switch (key) {
			case Keyboard.KEY_RIGHT:
				this.setExtended(true);
				break;
		}
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
		this.subValue.setX(this.getX() + this.getWidth());
	}

}
