package nl.x.client.gui.click.old.button.impl;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.client.cheat.CheatManager;
import nl.x.client.gui.click.old.button.Window;
import nl.x.client.gui.click.old.button.impl.button.impl.ModButton;

/**
 * @author NullEX
 *
 */
public class CategoryWindow extends Window {
	private boolean expaned = false, was = false, drag = false;
	public List<ModButton> buttons = Lists.newArrayList();
	private ModButton current;
	private Category category;
	public int width = 0, height = 14;
	public int dragX, dragY;

	/**
	 * @param x
	 * @param y
	 * @param category
	 */
	public CategoryWindow(int x, int y, Category category) {
		super(x, y);
		this.category = category;
		int y2 = y + 14;
		for (Cheat c : CheatManager.INSTANCE.buffer) {
			if (c.getCategory() != this.category)
				continue;
			if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(c.getName() + "  ") > width) {
				width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(c.getName() + "  ");
			}
			height += 15;
		}
		this.width += 10;
		for (Cheat c : CheatManager.INSTANCE.buffer) {
			if (c.getCategory() != this.category)
				continue;
			this.buttons.add(new ModButton(this, x + 3, y2, this.width, c));
			y2 += 15;
		}
	}

	/*
	 * @see nl.x.client.gui.click.button.Window#draw(int, int)
	 */
	@Override
	public void draw(int mouseX, int mouseY) {
		if (this.buttons.isEmpty())
			return;
		Gui.drawRect(this.getX(), this.getY(), this.getX() + width, this.getY() + (this.expaned ? height - 1 : 14),
				new Color(17, 17, 17).getRGB());
		Gui.drawRect(this.getX() + this.width - 8 - 2, this.getY() + 2, this.getX() + this.width - 3, this.getY() + 9,
				(this.expaned ? new Color(53, 46, 46).getRGB() : new Color(53, 46, 46).darker().getRGB()));
		Minecraft.getMinecraft().fontRendererObj.drawString(this.category.name(), this.getX() + 4, this.getY() + 2, -1);
		if (this.expaned) {
			this.buttons.forEach(b -> b.draw(mouseX, mouseY));
		}
		if (this.drag) {
			this.setX(mouseX - this.dragX);
			this.setY(mouseY - this.dragY);
		}
	}

	/*
	 * @see nl.x.client.gui.click.button.Window#type(char, int)
	 */
	@Override
	public void type(char typedChar, int keyCode) {
		this.buttons.forEach(b -> b.type(typedChar, keyCode));
	}

	/*
	 * @see nl.x.client.gui.click.button.Window#click(int, int)
	 */
	@Override
	public void click(int mouseX, int mouseY, int mouseButton) {
		if (this.isHover(mouseX, mouseY)) {
			if (mouseButton == 1) {
				this.expaned = !this.expaned;
			} else if (mouseButton == 0) {
				this.was = this.expaned;
				this.dragX = mouseX - this.getX();
				this.dragY = mouseY - this.getY();
				this.drag = true;
			}
			return;
		}
		if (this.expaned) {
			this.buttons.forEach(b -> b.click(mouseX, mouseY, mouseButton));
		}
	}

	/*
	 * @see nl.x.client.gui.click.button.Window#release(int, int, int)
	 */
	@Override
	public void release(int mouseX, int mouseY, int mouseButton) {
		if (this.drag) {
			this.expaned = this.was;
			this.was = false;
			this.drag = false;
		}
		this.buttons.forEach(b -> b.release(mouseX, mouseY, mouseButton));
	}

	public boolean isHover(int mouseX, int mouseY) {
		return mouseX > this.getX() && mouseX < this.getX() + this.width && mouseY > this.getY()
				&& mouseY < this.getY() + 14;
	}
}
