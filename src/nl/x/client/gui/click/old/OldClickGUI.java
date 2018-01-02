package nl.x.client.gui.click.old;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiScreen;
import nl.x.api.cheat.Cheat.Category;
import nl.x.client.gui.click.old.button.Window;
import nl.x.client.gui.click.old.button.impl.CategoryWindow;

/**
 * @author NullEX
 *
 */
public class OldClickGUI extends GuiScreen {
	public static List<Window> windows = Lists.newArrayList();

	/**
	 * 
	 */
	public OldClickGUI() {
		int x = 5;
		if (this.windows.isEmpty()) {
			for (Category c : Category.values()) {
				if (c.equals(Category.None))
					continue;
				CategoryWindow w = new CategoryWindow(x, 10, c);
				this.windows.add(w);
				x += w.width + 7;
			}
		}
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#drawScreen(int, int, float)
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.windows.forEach(w -> w.draw(mouseX, mouseY));
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#keyTyped(char, int)
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.windows.forEach(w -> w.type(typedChar, keyCode));
		super.keyTyped(typedChar, keyCode);
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#mouseReleased(int, int, int)
	 */
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		this.windows.forEach(w -> w.release(mouseX, mouseY, state));
		super.mouseReleased(mouseX, mouseY, state);
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#mouseClicked(int, int, int)
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.windows.forEach(w -> w.click(mouseX, mouseY, mouseButton));
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

}
