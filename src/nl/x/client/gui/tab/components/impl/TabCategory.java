package nl.x.client.gui.tab.components.impl;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.utils.render.RenderUtil;
import nl.x.client.cheat.CheatManager;
import nl.x.client.gui.tab.components.TabComponent;

/**
 * @author NullEX
 *
 */
public class TabCategory extends TabComponent {
	public Category category;
	public List<TabCheat> cheats = Lists.newArrayList();
	public int width = 0, max;

	/**
	 * @param render
	 * @param x
	 * @param y
	 * @param category
	 */
	public TabCategory(String render, int x, int y, Category category) {
		super(render, x, y);
		this.category = category;
		int y2 = this.getY();
		for (Cheat c : CheatManager.INSTANCE.buffer) {
			if (c.getCategory() != this.category)
				continue;
			this.cheats.add(new TabCheat(c.getName(), this.getX(), y2, c));
			y2 += 12;
		}
		for (TabCheat tab : this.cheats) {
			if (mc.fontRendererObj.getStringWidth(tab.getRender() + " ") > max) {
				max = mc.fontRendererObj.getStringWidth(tab.getRender() + " ");
			}
		}
		for (TabCheat tab : this.cheats) {
			tab.setWidth(max);
		}

	}

	/*
	 * @see nl.x.client.gui.tab.components.TabComponent#render()
	 */
	@Override
	public void render() {
		mc.fontRendererObj.drawStringWithShadow(this.getRender(), this.getX(), this.getY(), -1);
		if (this.isExtended()) {
			int x = this.cheats.get(0).getX() - 2;
			RenderUtil.drawBorderedRect(x, this.cheats.get(0).getY() - 2, x + max,
					this.getY() + (12 * this.cheats.size()), 2, Color.BLACK.getRGB(), Color.DARK_GRAY.getRGB());
			Gui.drawRect(x + 1, this.cheats.get(this.getIndex()).getY() - 1, x + max - 1,
					this.cheats.get(this.getIndex()).getY() + 10, new Color(247, 84, 41).getRGB());
			this.cheats.forEach(TabCheat::render);
		}
	}

	/*
	 * @see nl.x.client.gui.tab.components.TabComponent#action(int)
	 */
	@Override
	public void action(int key) {
		if (this.isExtended()) {
			if (!this.cheats.get(this.getIndex()).isExtended()) {
				switch (key) {
					case Keyboard.KEY_LEFT:
						this.setExtended(false);
						break;
					case Keyboard.KEY_DOWN:
						if (this.getIndex() + 1 >= this.cheats.size()) {
							this.setIndex(0);
							break;
						}
						this.setIndex(this.getIndex() + 1);
						break;
					case Keyboard.KEY_UP:
						if (this.getIndex() - 1 < 0) {
							this.setIndex(this.cheats.size() - 1);
							break;
						}
						this.setIndex(this.getIndex() - 1);
						break;
				}
			}
			this.cheats.get(this.getIndex()).action(key);
			return;
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
		for (TabCheat tab : this.cheats) {
			tab.setX(this.getX() + width + 5);
		}
	}

}
