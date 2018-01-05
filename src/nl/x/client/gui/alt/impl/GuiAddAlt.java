package nl.x.client.gui.alt.impl;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import nl.x.client.gui.alt.GuiAltmanager;
import nl.x.client.gui.alt.account.Account;

/**
 * @author NullEX
 *
 */
public class GuiAddAlt extends GuiScreen {
	public GuiTextField email, password;
	public GuiScreen parent;
	public String error = "";

	/**
	 * @param parent
	 */
	public GuiAddAlt(GuiScreen parent) {
		this.parent = parent;
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#initGui()
	 */
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.email = new GuiTextField(999, mc.fontRendererObj, this.width / 3, (this.height / 2) - 30, this.width / 3,
				15);
		this.password = new GuiTextField(999, mc.fontRendererObj, this.width / 3, (this.height / 2), this.width / 3,
				15);
		this.email.setMaxStringLength(200);
		this.password.setMaxStringLength(200);
		this.buttonList.add(new GuiButton(10, this.width / 2, this.height - 30, 100, 20, "Add"));
		this.buttonList.add(new GuiButton(11, this.width / 2 - 100, this.height - 30, 100, 20, "Back"));
		super.initGui();
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	/*
	 * @see
	 * net.minecraft.client.gui.GuiScreen#actionPerformed(net.minecraft.client.
	 * gui.GuiButton)
	 */
	@Override
	public void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 10:
				if (this.email.getText().isEmpty() || this.email.getText().length() < 1) {
					this.error = "§cInvalid email/Username";
					break;
				}
				int y = 30;
				for (Account c : GuiAltmanager.accounts) {
					y += 25;
				}
				GuiAltmanager.accounts.add(new Account(this.email.getText(), this.password.getText(), 40, y));
				GuiAltmanager.saveAlts();
				mc.displayGuiScreen(this.parent);
				break;
			case 11:
				mc.displayGuiScreen(this.parent);
				break;
		}
		super.actionPerformed(button);
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#drawScreen(int, int, float)
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		mc.fontRendererObj.drawString(this.error, this.email.xPosition + (this.email.xPosition / 3), 5, -1);
		this.password.drawTextBox();
		this.email.drawTextBox();
		mc.fontRendererObj.drawString("Password", this.password.xPosition, this.password.yPosition - 11, -1);
		mc.fontRendererObj.drawString("Email | Username", this.email.xPosition, this.email.yPosition - 11, -1);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#updateScreen()
	 */
	@Override
	public void updateScreen() {
		this.password.updateCursorCounter();
		this.email.updateCursorCounter();
		super.updateScreen();
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#mouseClicked(int, int, int)
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.password.mouseClicked(mouseX, mouseY, mouseButton);
		this.email.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#keyTyped(char, int)
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.password.textboxKeyTyped(typedChar, keyCode);
		this.email.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}

}
