package nl.x.client.gui.tab.components.impl;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.Value;
import nl.x.api.utils.render.RenderUtil;
import nl.x.client.gui.tab.components.TabComponent;

/**
 * @author NullEX
 *
 */
public class TabCheat extends TabComponent {
	private Cheat cheat;
	private int width = 0, max;
	public List<TabValue> values = Lists.newArrayList();

	/**
	 * @param render
	 * @param x
	 * @param y
	 * @param cheat
	 */
	public TabCheat(String render, int x, int y, Cheat cheat) {
		super(render, x, y);
		this.cheat = cheat;
		int y2 = this.getY();
		for (Value v : this.cheat.getValues()) {
			this.values.add(new TabValue(v.getName(), this.getX(), y2, v));
			y2 += 12;
		}
		for (TabValue tab : this.values) {
			if (mc.fontRendererObj.getStringWidth(tab.getRender() + " ") > max) {
				max = mc.fontRendererObj.getStringWidth(tab.getRender() + " ");
			}
		}
		for (TabValue tab : this.values) {
			tab.setWidth(max);
		}
	}

	/*
	 * @see nl.x.client.gui.tab.components.TabComponent#render()
	 */
	@Override
	public void render() {
		mc.fontRendererObj.drawStringWithShadow(this.getRender(), this.getX(), this.getY(),
				(!this.cheat.isEnabled() ? Color.GRAY.getRGB() : -1));
		if (!this.cheat.getValues().isEmpty()) {
			if (this.values.get(0).getX() != this.getX() + this.getWidth() + 2) {
				for (TabValue tab : this.values) {
					tab.setX(this.getX() + this.getWidth() + 2);
				}
			}
			Gui.drawRect(this.getX() + this.getWidth() - 3, this.getY() - 1, this.getX() + this.getWidth() - 2,
					this.getY() + 11, (this.isExtended() ? 0 : new Color(163, 163, 163).getRGB()));
		}
		if (this.isExtended()) {
			int x = this.values.get(0).getX() - 2;
			RenderUtil.drawBorderedRect(x, this.values.get(0).getY() - 2, x + max,
					this.getY() + (12 * this.values.size()), 2, Color.BLACK.getRGB(), Color.DARK_GRAY.getRGB());
			Gui.drawRect(x + 1, this.values.get(this.getIndex()).getY() - 1, x + max - 1,
					this.values.get(this.getIndex()).getY() + 10, new Color(247, 84, 41).getRGB());
			this.values.forEach(TabValue::render);
		}
	}

	/*
	 * @see nl.x.client.gui.tab.components.TabComponent#action(int)
	 */
	@Override
	public void action(int key) {
		if (this.isExtended()) {
			if (!this.values.get(this.getIndex()).isExtended()) {
				switch (key) {
					case Keyboard.KEY_LEFT:
						this.setExtended(false);
						break;
					case Keyboard.KEY_DOWN:
						if (this.getIndex() + 1 >= this.values.size()) {
							this.setIndex(0);
							break;
						}
						this.setIndex(this.getIndex() + 1);
						break;
					case Keyboard.KEY_UP:
						if (this.getIndex() - 1 < 0) {
							this.setIndex(this.values.size() - 1);
							break;
						}
						this.setIndex(this.getIndex() - 1);
						break;
				}
			}
			this.values.get(this.getIndex()).action(key);
			return;
		}
		switch (key) {
			case Keyboard.KEY_RETURN:
				if (!this.cheat.getValues().isEmpty()) {
					this.setExtended(true);
				}
				break;
			case Keyboard.KEY_RIGHT:
				this.cheat.toggle();
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
	}

}
