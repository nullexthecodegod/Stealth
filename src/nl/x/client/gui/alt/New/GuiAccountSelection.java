package nl.x.client.gui.alt.New;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import nl.x.client.Client;

public class GuiAccountSelection extends GuiScreen {

	protected GuiScreen prevScreen;
	protected String title = "Select account";
	private GuiButton addButton, directButton, deleteButton, editButton, sortButton, clearButton, showPassButton;
	private GuiTextField usernameText, passwordText;
	private GuiListAccountSelection selectionList;

	public GuiAccountSelection(GuiScreen screenIn) {
		this.prevScreen = screenIn;
	}

	/**
	 * Adds the buttons (and other controls) to the windowManager in question.
	 * Called when the GUI is displayed and when the window resizes, the
	 * buttonList is cleared beforehand.
	 */
	@Override
	public void initGui() {
		this.selectionList = new GuiListAccountSelection(this, this.mc, this.width, this.height, 32, this.height - 64,
				36);
		this.postInit();
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.selectionList.handleMouseInput();
	}

	public void postInit() {
		int w = 68;
		int p = 2;
		int wp = w - (p * 2);

		this.sortButton = this
				.func_189646_b(new GuiButton(7, this.width / 2 - (w * 3) + p, this.height - 56, wp, 20, "Sort"));
		usernameText = new GuiTextField(1, mc.fontRendererObj, this.width / 2 - (w * 2) + p + 1, this.height - 55,
				(w * 2) - (p * 2) - 2, 18);
		passwordText = new GuiTextField(2, mc.fontRendererObj, this.width / 2 + p + 1, this.height - 55,
				(w * 2) - (p * 2) - 2, 18);
		this.showPassButton = this
				.func_189646_b(new GuiButton(8, this.width / 2 + (w * 2) + p, this.height - 56, wp, 20, "Show Pass"));

		this.clearButton = this
				.func_189646_b(new GuiButton(9, this.width / 2 - (w * 3) + p, this.height - 28, wp, 20, "Clear"));
		this.addButton = this
				.func_189646_b(new GuiButton(3, this.width / 2 - (w * 2) + p, this.height - 28, wp, 20, "Add"));
		this.directButton = this
				.func_189646_b(new GuiButton(4, this.width / 2 - w + p, this.height - 28, wp, 20, "Direct"));
		this.editButton = this.func_189646_b(new GuiButton(5, this.width / 2 + p, this.height - 28, wp, 20, "Edit"));
		this.deleteButton = this
				.func_189646_b(new GuiButton(6, this.width / 2 + w + p, this.height - 28, wp, 20, "Delete"));
		this.func_189646_b(
				new GuiButton(0, this.width / 2 + (w * 2) + p, this.height - 28, wp, 20, I18n.format("gui.done")));

		sortButton.enabled = false;
		usernameText.setFocused(true);
		passwordText.setFocused(false);
		showPassButton.enabled = true;

		clearButton.enabled = false;
		addButton.enabled = false;
		directButton.enabled = false;
		editButton.enabled = false;
		deleteButton.enabled = false;
	}

	/**
	 * @param guiButton
	 * @return
	 */
	private GuiButton func_189646_b(GuiButton guiButton) {
		this.buttonList.add(guiButton);
		return guiButton;
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed
	 * for buttons)
	 */

	@Override
	public void updateScreen() {
		usernameText.updateCursorCounter();
		passwordText.updateCursorCounter();

		boolean empty = Client.INSTANCE.accountManager.accounts.isEmpty();
		sortButton.enabled = !empty;
		clearButton.enabled = !empty;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			GuiListAccountSelectionEntry guilistaccountselectionentry = this.selectionList.getSelectedAccount();

			switch (button.id) {
				case 0:

					mc.displayGuiScreen(prevScreen);
					break;
				case 3:
					if (!usernameText.getText().isEmpty()) {
						Client.INSTANCE.accountManager.add(
								new Account().setLogin(usernameText.getText()).setPassword(passwordText.getText()));
						selectionList.refreshList();

					}
					break;
				case 4:
					if (!usernameText.getText().isEmpty()) {
						new Account().setLogin(usernameText.getText()).setPassword(passwordText.getText())
								.authenticate();
					}
					break;
				case 5:
					if (guilistaccountselectionentry != null && !usernameText.getText().isEmpty()) {
						Account account = guilistaccountselectionentry.account;
						account.setLogin(usernameText.getText());
						account.setPassword(passwordText.getText());

					}
					break;
				case 6:
					if (guilistaccountselectionentry != null) {
						guilistaccountselectionentry.deleteAccount();
						selectionList.refreshList();

					}
					break;
				case 7:
					if (!Client.INSTANCE.accountManager.accounts.isEmpty()) {
						Client.INSTANCE.accountManager.sort();
						selectionList.refreshList();

					}
					break;
				case 8:
					Client.INSTANCE.accountManager.showPasswords = !Client.INSTANCE.accountManager.showPasswords;
					showPassButton.displayString = (Client.INSTANCE.accountManager.showPasswords ? "Hide" : "Show")
							+ " Pass";
					break;
				case 9:
					if (!Client.INSTANCE.accountManager.accounts.isEmpty()) {
						Client.INSTANCE.accountManager.accounts.clear();
						selectionList.refreshList();

					}
					break;
			}
		}
	}

	/**
	 * Draws the windowManager and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.selectionList.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
		this.drawString(this.fontRendererObj, mc.session.getUsername(), 2, 5, 16777215);
		usernameText.drawTextBox();
		passwordText.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.selectionList.mouseClicked(mouseX, mouseY, mouseButton);
		usernameText.mouseClicked(mouseX, mouseY, mouseButton);
		passwordText.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Called when a mouse button is released.
	 */
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		this.selectionList.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		usernameText.textboxKeyTyped(typedChar, keyCode);
		passwordText.textboxKeyTyped(typedChar, keyCode);

		if (keyCode == 15) {
			usernameText.setFocused(!usernameText.isFocused());
			passwordText.setFocused(!passwordText.isFocused());
		}

		if (keyCode == 28 || keyCode == 156) {
			this.actionPerformed((GuiButton) this.buttonList.get(3));
		}

		GuiListAccountSelectionEntry guilistaccountselectionentry = this.selectionList.getSelectedAccount();
		boolean flag = !usernameText.getText().isEmpty();
		addButton.enabled = flag;
		directButton.enabled = flag;
		editButton.enabled = flag && guilistaccountselectionentry != null;
	}

	public void selectAccount(GuiListAccountSelectionEntry entry) {
		boolean flag = entry != null;
		editButton.enabled = flag && !usernameText.getText().isEmpty();
		deleteButton.enabled = flag;
	}

}
