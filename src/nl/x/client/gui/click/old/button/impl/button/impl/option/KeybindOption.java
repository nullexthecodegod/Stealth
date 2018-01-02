package nl.x.client.gui.click.old.button.impl.button.impl.option;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import nl.x.client.gui.click.old.button.impl.button.Button;
import nl.x.client.gui.click.old.button.impl.button.impl.ModButton;

/**
 * @author NullEX
 *
 */
public class KeybindOption extends Button {
	private ModButton parent;
	public int width;
	public boolean change = false;

	/**
	 * @param x
	 * @param y
	 * @param parent
	 */
	public KeybindOption(int x, int y, ModButton parent) {
		super(x, y);
		this.parent = parent;
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#draw(int, int)
	 */
	@Override
	public void draw(int mouseX, int mouseY) {
		if (this.isHover(mouseX, mouseY)) {
			Gui.drawRect(this.getX() - 2, this.getY() - 2, this.getX() + this.getMax() - 3, this.getY() + 9,
					Integer.MIN_VALUE);

		}
		Minecraft.getMinecraft().fontRendererObj.drawString(
				this.change ? "Press any key" : "Bind: " + Keyboard.getKeyName(this.parent.cheat.getBind()),
				this.getX(), this.getY(), -1);
		this.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(
				this.change ? "Press any key " : "Bind:  " + Keyboard.getKeyName(this.parent.cheat.getBind()));
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#type(char, int)
	 */
	@Override
	public void type(char typedChar, int keyCode) {
		if (this.change) {
			this.parent.cheat.setBind(keyCode);
			this.change = false;
		}
	}

	/*
	 * @see nl.x.client.gui.click.button.impl.button.Button#click(int, int)
	 */
	@Override
	public void click(int mouseX, int mouseY, int mouseButton) {
		if (this.isHover(mouseX, mouseY)) {
			this.change = true;
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
		return mouseX < this.getX() + this.getMax() - 2 && mouseX > this.getX() - 4 && mouseY > this.getY() - 2
				&& mouseY < this.getY() + 10;
	}

}
