package nl.x.client.gui.click.old.button.impl.button.impl;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.Value;
import nl.x.client.gui.click.old.button.impl.CategoryWindow;
import nl.x.client.gui.click.old.button.impl.button.Button;
import nl.x.client.gui.click.old.button.impl.button.impl.option.KeybindOption;
import nl.x.client.gui.click.old.button.impl.button.impl.option.OptionButton;

/**
 * @author NullEX
 *
 */
public class ModButton extends Button {
	public Cheat cheat;
	private List<Button> options = Lists.newArrayList();
	public int width, max;
	public boolean expand = false;
	public CategoryWindow parent;

	/**
	 * @param x
	 * @param y
	 * @param cheat
	 */
	public ModButton(CategoryWindow parent, int x, int y, int width, Cheat cheat) {
		super(x, y);
		this.parent = parent;
		this.width = width;
		this.cheat = cheat;
		this.max = -1;
		int y2 = 0;
		if (!this.cheat.values.isEmpty()) {
			for (Value v : this.cheat.getValues()) {
				this.options.add(new OptionButton(x + width + 5, y + y2, v));
				y2 += 12;
			}
		}
		this.options.add(new KeybindOption(x + width + 5, y + y2, this));
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#draw(int, int)
	 */
	@Override
	public void draw(int mouseX, int mouseY) {
		this.setX(this.parent.getX() + 3);
		this.setY(this.parent.getY() + 14 + (15 * this.parent.buttons.indexOf(this)));
		Gui.drawRect(this.getX() - 3, this.getY() - 3, this.getX() + this.width - 3, this.getY() + 13,
				new Color(198, 41, 41).getRGB());
		Gui.drawRect(this.getX() - 2, this.getY() - 2, this.getX() + this.width - 4, this.getY() + 12,
				new Color(40, 38, 38).getRGB());
		int color = Color.LIGHT_GRAY.getRGB();
		if (this.isHover(mouseX, mouseY)) {
			color = Color.GRAY.getRGB();
		} else if (this.cheat.isEnabled()) {
			color = -1;
		}
		Minecraft.getMinecraft().fontRendererObj.drawString(this.cheat.getName(), this.getX(), this.getY() + 1.2f,
				color, false);
		if (!this.cheat.values.isEmpty() && !this.expand) {
			Minecraft.getMinecraft().fontRendererObj.drawString(">", this.getX() + this.width - 10, this.getY() + 1.2f,
					-1, false);
		}
		if (this.options.size() - 1 != this.cheat.getValues().size()) {
			this.options.clear();
			int y2 = 0;
			if (!this.cheat.values.isEmpty()) {
				for (Value v : this.cheat.getValues()) {
					this.options.add(new OptionButton(this.getX() + width + 5, this.getY() + y2, v));
					y2 += 12;
				}
			}
			this.options.add(new KeybindOption(this.getX() + width + 5, this.getY() + y2, this));
		}
		if (this.expand) {
			boolean set = false;
			for (Button b : this.options) {
				if (b instanceof OptionButton) {
					OptionButton o = (OptionButton) b;
					if (o.width > max) {
						max = o.width;
						set = true;
					}
				}
				if (b instanceof KeybindOption) {
					KeybindOption o = (KeybindOption) b;
					if (o.width > max) {
						max = o.width;
						set = true;
					}
				}
			}
			if (set) {
				max += 5;
				set = false;
			}
			int y = 0;
			for (Button b : this.options) {
				b.setX(this.getX() + this.width + 5);
				b.setY(this.getY() + y);
				b.setMax(this.max);
				y += 12;
			}
			Gui.drawRect(this.getX() + this.width + 2, this.getY() - 3, this.getX() + this.width + max + 3,
					this.getY() + (this.options.size() * 14), new Color(198, 41, 41).getRGB());
			Gui.drawRect(this.getX() + this.width + 3, this.getY() - 2, this.getX() + this.width + max + 2,
					this.getY() + (this.options.size() * 14) - 1, new Color(40, 38, 38).getRGB());
			this.options.forEach(b -> b.draw(mouseX, mouseY));
		} else {
			if (this.max != -1) {
				this.max = -1;
			}
		}
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#type(char, int)
	 */
	@Override
	public void type(char typedChar, int keyCode) {
		if (this.expand) {
			this.options.forEach(b -> b.type(typedChar, keyCode));
		}
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#click(int, int)
	 */
	@Override
	public void click(int mouseX, int mouseY, int mouseButton) {
		if (this.isHover(mouseX, mouseY)) {
			switch (mouseButton) {
				case 0:
					this.cheat.toggle();
					break;
				case 1:
					this.expand = !this.expand;
					break;
			}
		}
		if (this.expand) {
			this.options.forEach(b -> b.click(mouseX, mouseY, mouseButton));
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
		return mouseX > this.getX() - 4 && mouseX < this.getX() + this.width - 3 && mouseY > this.getY() - 3
				&& mouseY < this.getY() + 13;
	}

}
