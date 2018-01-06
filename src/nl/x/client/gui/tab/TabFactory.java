package nl.x.client.gui.tab;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.utils.render.RenderUtil;
import nl.x.client.gui.tab.components.impl.TabCategory;

/**
 * @author NullEX
 *
 */
public class TabFactory {
	public List<TabCategory> categorys = Lists.newArrayList();
	public int index = 0;

	public void init() {
		int max = 0;
		int y = 25;
		for (Category c : Category.values()) {
			if (c.equals(Category.None))
				continue;
			if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(c.name() + " ") > max) {
				max = Minecraft.getMinecraft().fontRendererObj.getStringWidth(c.name() + " ");
			}
			this.categorys.add(new TabCategory(c.name(), 2, y, c));
			y += 11;
		}
		for (TabCategory tab : this.categorys) {
			tab.setWidth(max);
		}
	}

	public void render() {
		RenderUtil.drawBorderedRect(1, this.categorys.get(0).getY() - 2, 1 + this.categorys.get(0).getWidth(),
				25 + (12 * this.categorys.size()), 2, Color.BLACK.getRGB(), Color.DARK_GRAY.getRGB());
		Gui.drawRect(2, this.categorys.get(this.index).getY() - 1, this.categorys.get(0).getWidth(),
				this.categorys.get(this.index).getY() + 10, new Color(247, 84, 41).getRGB());
		this.categorys.forEach(TabCategory::render);
	}

	public void action(int key) {
		if (!this.categorys.get(this.index).isExtended()) {
			switch (key) {
				case Keyboard.KEY_RIGHT:
					this.categorys.get(this.index).setExtended(true);
					break;
				case Keyboard.KEY_DOWN:
					if (this.index + 1 >= this.categorys.size()) {
						this.index = 0;
						break;
					}
					this.index = this.index + 1;
					break;
				case Keyboard.KEY_UP:
					if (this.index - 1 < 0) {
						this.index = this.categorys.size() - 1;
						break;
					}
					this.index = this.index - 1;
					break;
			}
			return;
		}
		this.categorys.get(this.index).action(key);
	}

}
