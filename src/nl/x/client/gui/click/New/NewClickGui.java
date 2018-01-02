package nl.x.client.gui.click.New;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.client.cheat.CheatManager;
import nl.x.client.gui.click.New.element.elements.ElementModuleButton;
import nl.x.client.gui.click.New.element.panel.Panel;

public class NewClickGui extends GuiScreen {
	private static List<Panel> panels = new CopyOnWriteArrayList<Panel>();

	public List<Panel> getPanels() {
		return this.panels;
	}

	public NewClickGui() {
		width = 100;
		height = 18;
		if (this.panels.isEmpty()) {
			int y = 20;
			for (Category c : Category.values()) {
				this.panels.add(new Panel(c, 120, y, width, height, false) {

					@Override
					public void setupItems() {
						for (Cheat c : CheatManager.INSTANCE.buffer) {
							if (c.getCategory() != this.getCategory())
								continue;
							this.getElements().add(new ElementModuleButton(c, this.getCategory()));
						}
					}
				});
				y += 25;
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float button) {
		GL11.glPushAttrib(1048575);
		for (Panel panel : this.panels) {
			panel.drawScreen(mouseX, mouseY, button);
		}
		GL11.glPopAttrib();
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		for (Panel panel : this.panels) {
			panel.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		for (Panel panel : this.panels) {
			panel.mouseReleased(mouseX, mouseY, state);
		}
	}

	@Override
	public void onGuiClosed() {
	}

}
