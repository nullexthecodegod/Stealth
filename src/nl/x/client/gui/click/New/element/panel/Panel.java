package nl.x.client.gui.click.New.element.panel;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import net.minecraft.util.StringUtils;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.utils.render.RenderUtil;
import nl.x.api.utils.render.cfont.CFontRenderer;
import nl.x.client.gui.click.New.element.Element;

public class Panel {
	public CFontRenderer font = new CFontRenderer(new Font("Adobe Courier", Font.PLAIN, 18), true, true);
	private Category category;
	private int x;
	private int y;
	private int x2;
	private int y2;
	private int width;
	private int height;
	private int scroll;
	private int dragged;
	private boolean open;
	private boolean drag;
	private final ArrayList<Element> elements = new ArrayList();
	private boolean scrollbar = false;

	public Panel(Category category, int x, int y, int width, int height, boolean open) {
		this.category = category;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.open = open;
		this.setupItems();
	}

	public void setupItems() {
	}

	public int getElementsHeight() {
		int height = 0;
		int count = 0;
		for (Element element : this.elements) {
			if (count >= 10)
				continue;
			height += element.getHeight() + 1;
			++count;
		}
		return height;
	}

	public boolean isHovering(int mouseX, int mouseY) {
		float textWidth = this.font.getStringWidth(StringUtils.stripControlCodes(
				String.valueOf(String.valueOf(this.category.name())) + " [\u00a78" + this.elements.size() + "\u00a7f]"))
				- 100.0f;
		if (mouseX >= this.x - textWidth / 2.0f - 4.0f
				&& mouseX <= this.x - textWidth / 2.0f
						+ this.font.getStringWidth(
								StringUtils.stripControlCodes(String.valueOf(String.valueOf(this.category.name()))
										+ " [\u00a78" + this.elements.size() + "\u00a7f]"))
						+ 4.0f
				&& mouseY >= this.y && mouseY <= this.y + this.height - (this.open ? 2 : 0)) {
			return true;
		}
		return false;
	}

	public void drag(int mouseX, int mouseY) {
		if (this.drag) {
			this.x = this.x2 + mouseX;
			this.y = this.y2 + mouseY;
		}
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
			this.x2 = this.x - mouseX;
			this.y2 = this.y - mouseY;
			this.drag = true;
			return;
		}
		if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
			this.open = !this.open;
			return;
		}
		if (!this.open) {
			return;
		}
		for (Element element : this.elements) {
			element.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		if (state == 0) {
			this.drag = false;
		}
		if (!this.open) {
			return;
		}
		for (Element element : this.elements) {
			element.mouseReleased(mouseX, mouseY, state);
		}
	}

	public void drawScreen(int mouseX, int mouseY, float button) {
		boolean scrollbar;
		this.drag(mouseX, mouseY);
		float elementsHeight = this.open ? this.getElementsHeight() - 1 : 0;
		float textWidth = this.font
				.getStringWidth(StringUtils.stripControlCodes(String.valueOf(String.valueOf(this.category.name()))
						+ " [\u00a78" + this.elements.size() + "\u00a7f]"));
		boolean bl = scrollbar = this.elements.size() >= 10;
		if (this.scrollbar != scrollbar) {
			this.scrollbar = scrollbar;
		}
		RenderUtil.drawBorderedRect(this.getX() - (textWidth - 100.0f) / 2.0f - 4.0f, this.getY(),
				this.getX() - (textWidth - 100.0f) / 2.0f + textWidth + 4.0f, this.getY() + 16, 1.0f, -16777216,
				this.open ? -2142711348 : Integer.MIN_VALUE);
		this.font.drawString(this.getTitle(), this.getX() - (textWidth - 100.0f) / 2.0f, this.getY() + 4, -1);
		if (Mouse.hasWheel() && mouseX >= this.getX() && mouseX <= this.getX() + 100 && mouseY >= this.getY()
				&& mouseY <= this.getY() + 19 + elementsHeight) {
			int wheel = Mouse.getDWheel();
			if (wheel < 0 && this.scroll < this.elements.size() - 10) {
				++this.scroll;
				if (this.scroll < 0) {
					this.scroll = 0;
				}
			} else if (wheel > 0) {
				--this.scroll;
				if (this.scroll < 0) {
					this.scroll = 0;
				}
			}
			if (wheel < 0) {
				if (this.dragged < this.elements.size() - 10) {
					++this.dragged;
				}
			} else if (wheel > 0 && this.dragged >= 1) {
				--this.dragged;
			}
		}
		if (this.open) {
			RenderUtil.drawBorderedRect(this.getX() - (scrollbar ? 4 : 0), this.getY() + 18, this.getX() + 100,
					this.getY() + 19 + elementsHeight, 1.5f, -587202560, Integer.MIN_VALUE);
			if (scrollbar) {
				RenderUtil.drawBorderedRect(this.getX() - 2, this.getY() + 21, this.getX(),
						this.getY() + 16 + elementsHeight, 1.5f, -587202560, -2142711348);
				RenderUtil.INSTANCE.drawRect((double) this.getX() - 2,
						this.getY() + 30 + (elementsHeight - 24.0f) / (this.elements.size() - 10) * this.dragged
								- 10.0f,
						this.getX(),
						this.getY() + 40 + (elementsHeight - 24.0f) / (this.elements.size() - 10) * this.dragged,
						-587202560);
			}
			int y = this.y + this.height - 2;
			int count = 0;
			this.elements.size();
			for (Element element : this.elements) {
				if (++count <= this.scroll || count >= this.scroll + 11 || this.scroll >= this.elements.size())
					continue;
				element.setLocation(this.x + 2, y);
				element.setWidth(this.getWidth() - 4);
				element.drawScreen(mouseX, mouseY, button);
				y += element.getHeight() + 1;
			}
		}
	}

	public boolean getScrollbar() {
		return this.scrollbar;
	}

	public Category getCategory() {
		return this.category;
	}

	public int getX() {
		return this.x;
	}

	public String getTitle() {
		return String.valueOf(String.valueOf(this.category.name())) + " [\u00a78" + this.elements.size() + "\u00a7f]";
	}

	public int getY() {
		return this.y;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean getOpen() {
		return this.open;
	}

	public ArrayList<Element> getElements() {
		return this.elements;
	}

	public void setX(int dragX) {
		this.x = dragX;
	}

	public void setY(int dragY) {
		this.y = dragY;
	}
}
