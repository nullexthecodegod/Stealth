/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes: org.lwjgl.input.Mouse
 */
package nl.x.client.gui.click.New.element.elements;

import java.awt.Font;

import org.lwjgl.input.Mouse;

import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.cheat.value.Value;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.cheat.value.values.NumberValue;
import nl.x.api.utils.misc.PepsiUtils;
import nl.x.api.utils.misc.Timer;
import nl.x.api.utils.render.RenderUtil;
import nl.x.api.utils.render.cfont.CFontRenderer;
import nl.x.client.gui.click.New.element.Element;

public class ElementModuleButton extends Element {
	public CFontRenderer font = new CFontRenderer(new Font("Adobe Courier", Font.PLAIN, 18), true, true);
	private Cheat module;
	private Category category;
	private boolean values;
	private float stringWidth;
	private float stringHeight;
	private final Timer time = new Timer();

	public ElementModuleButton(Cheat module, Category category) {
		this.setModule(module);
		this.setCategory(category);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
			this.getModule().toggle();
		}
		if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
			this.values = !this.values;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float button) {
		float width = this.font.getStringWidth(this.getText());
		RenderUtil.drawBorderedRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + 17, 1.0f,
				-16777216, this.getEnabled() ? -2142711348 : -2145049307);
		this.font.drawString(this.getText(), this.getX() - (width - 100.0f) / 2.0f, this.getY() + 6, -1);
		if (this.values) {
			int yPos = this.getY() + 2;
			int count = 0;
			for (Value value : this.getModule().getValues()) {
				if (value == null)
					continue;
				String text = value.getName();
				float stringWidth = this.font
						.getStringWidth(text + (value instanceof ArrayValue ? ": " + ((ArrayValue) value).getValue()
								: value instanceof NumberValue ? ": " + (((NumberValue) value).isInteger()
										? ((NumberValue) value).getValue().intValue()
										: ((NumberValue) value).getValue().floatValue()) : ""));
				if (this.stringWidth < stringWidth) {
					this.stringWidth = stringWidth;
				}
				if (this.stringHeight < yPos) {
					this.stringHeight = yPos;
				}
				if (value instanceof BooleanValue) {
					RenderUtil.drawRect(this.getX() + this.width + 5, yPos + 2,
							this.getX() + this.width + 9 + this.stringWidth + 2.0f, yPos + 14,
							((BooleanValue) value).getValue() ? -2142711348 : Integer.MIN_VALUE);
					if (mouseX >= this.getX() + this.width + 6
							&& mouseX <= this.getX() + this.width + 8 + this.stringWidth + 2.0f && mouseY >= yPos + 2
							&& mouseY <= yPos + 14) {
						RenderUtil.drawRect(this.getX() + this.width + 5, yPos + 2,
								this.getX() + this.width + 9 + this.stringWidth + 2.0f, yPos + 14, -2130706433);
						if (Mouse.isButtonDown(0) && this.time.hasPassed(500)) {
							((BooleanValue) value).setValue(!((BooleanValue) value).getValue());
							this.time.reset();
						}
					}
				} else if (value instanceof ArrayValue) {
					RenderUtil.drawRect(this.getX() + this.width + 5, yPos + 2,
							this.getX() + this.width + 9 + this.stringWidth + 2.0f, yPos + 14, Integer.MIN_VALUE);
					if (mouseX >= this.getX() + this.width + 6
							&& mouseX <= this.getX() + this.width + 8 + this.stringWidth + 2.0f && mouseY >= yPos + 2
							&& mouseY <= yPos + 14) {
						RenderUtil.drawRect(this.getX() + this.width + 5, yPos + 2,
								this.getX() + this.width + 9 + this.stringWidth + 2.0f, yPos + 14, -2130706433);
						if (Mouse.isButtonDown(0) && this.time.hasPassed(500)) {
							ArrayValue m = (ArrayValue) value;
							int next = 0;
							for (int i = 0; i < m.getValues().size(); i++) {
								if (m.getValue() == m.getValues().get(i)) {
									next = (i + 1 >= m.getValues().size() ? 0 : i + 1);
									break;
								}
							}
							m.setValue(m.getValues().get(next));
							this.time.reset();
						}
					}
				} else if (value instanceof NumberValue) {
					NumberValue v = (NumberValue) value;
					RenderUtil.drawRect(this.getX() + this.width + 5, yPos + 2,
							this.getX() + this.width + 9 + this.stringWidth + 2.0f, yPos + 14, Integer.MIN_VALUE);
					RenderUtil.drawRect(this.getX() + this.width + 5, yPos + 2,
							this.getX() + this.width
									+ (v.getValue().doubleValue() / v.getMax().doubleValue() * stringWidth),
							yPos + 14, -44976);
					if (mouseX >= this.getX() + this.width + 6
							&& mouseX <= this.getX() + this.width + 8 + this.stringWidth + 2.0f && mouseY >= yPos + 2
							&& mouseY <= yPos + 14 && Mouse.isButtonDown(0)) {
						double offset = PepsiUtils.round((double) (mouseX - (this.getX() * 2))
								/ (double) this.getWidth() * v.getMax().doubleValue(), 1);
						v.setValue(offset);
					}

				}
				this.font.drawString(
						text + (value instanceof ArrayValue ? ": " + ((ArrayValue) value).getValue()
								: value instanceof NumberValue ? ": " + (((NumberValue) value).isInteger()
										? ((NumberValue) value).getValue().intValue()
										: ((NumberValue) value).getValue().floatValue()) : ""),
						this.getX() + this.width + 8, yPos + 4, -1);
				yPos += 12;
				++count;
			}
			if (this.stringWidth > 0.0f && this.stringHeight > 0.0f) {
				RenderUtil.drawBorderedRect(this.getX() + this.width + 5, this.getY() + 4,
						this.getX() + this.width + this.stringWidth + 11.0f, this.getY() + 4 + count * 12, 1.0f,
						-587202560, 0);
			}
		}
		if (this.isHovering(mouseX, mouseY)) {
			RenderUtil.drawRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + 17,
					1627389951);
		}
	}

	@Override
	public int getHeight() {
		return 16;
	}

	public Cheat getModule() {
		return this.module;
	}

	public String getText() {
		return this.getModule().getName();
	}

	public Category getCategory() {
		return this.category;
	}

	public boolean getEnabled() {
		return this.getModule().isEnabled();
	}

	public boolean isHovering(int mouseX, int mouseY) {
		if (mouseX >= this.getX() && mouseX <= this.getX() + this.width && mouseY >= this.getY()
				&& mouseY <= this.getY() + 16) {
			return true;
		}
		return false;
	}

	public void setModule(Cheat module) {
		this.module = module;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
