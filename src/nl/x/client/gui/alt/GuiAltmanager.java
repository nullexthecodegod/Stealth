package nl.x.client.gui.alt;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import nl.x.api.utils.misc.FileUtils;
import nl.x.api.utils.misc.SessionUtils;
import nl.x.api.utils.render.RenderUtil;
import nl.x.client.gui.alt.account.Account;
import nl.x.client.gui.alt.impl.GuiAddAlt;

/**
 * @author NullEX
 *
 */
public class GuiAltmanager extends GuiScreen {
	public static File alts = FileUtils.getConfigFile("Alts");
	public GuiButton remove;
	public static ArrayList<Account> accounts = Lists.newArrayList();
	public static Account selected;
	public GuiScreen parent;
	public static String info = "";

	/**
	 * 
	 * @param parent
	 */
	public GuiAltmanager(GuiScreen parent) {
		this.parent = parent;
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#initGui()
	 */
	@Override
	public void initGui() {
		if (accounts.isEmpty()) {
			List<String> read = FileUtils.read(this.alts);
			int y = 30;
			for (String s : read) {
				String[] split = s.split(":");
				Account set = new Account(split[0], split[1], 40, y);
				if (split.length == 3) {
					set.setUsername(split[2]);
				}
				this.accounts.add(set);
				y += 25;
			}
		}
		this.buttonList.add(new GuiButton(10, this.width / 2, this.height - 30, 100, 20, "Add Account"));
		this.buttonList.add(new GuiButton(11, this.width / 2 - 100, this.height - 30, 100, 20, "Back"));
		this.remove = new GuiButton(12, this.width / 2 - 200, this.height - 30, 100, 20, "Remove");
		this.buttonList.add(this.remove);
		super.initGui();
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#onGuiClosed()
	 */
	@Override
	public void onGuiClosed() {
		this.saveAlts();
		super.onGuiClosed();
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
				mc.displayGuiScreen(new GuiAddAlt(this));
				break;
			case 11:
				mc.displayGuiScreen(this.parent);
				break;
			case 12:
				if (this.selected != null) {
					this.accounts.remove(this.selected);
					this.selected = null;
				}
				break;
		}
		super.actionPerformed(button);
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#updateScreen()
	 */
	@Override
	public void updateScreen() {
		this.remove.enabled = this.selected != null;
		super.updateScreen();
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#drawScreen(int, int, float)
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(mc.fontRendererObj, this.info, this.width / 3 + ((this.width / 3) / 2), 5, -1);
		mc.fontRendererObj.drawString(SessionUtils.getSession().getUsername(), 5, 5, -1);
		RenderUtil.drawBorderedRect(this.width / 20, 20, this.width - (this.width / 20), this.height - 40, 2,
				Color.black.getRGB(), Integer.MIN_VALUE);
		this.accounts.forEach(a -> a.render(mouseX, mouseY, partialTicks));
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/*
	 * @see net.minecraft.client.gui.GuiScreen#mouseClicked(int, int, int)
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.accounts.stream().filter(a -> a != null && a.isHoverd(mouseX, mouseY)).forEach(a -> {
			if (mouseButton == 0) {
				selected = a;
				a.click(mouseX, mouseY, mouseButton);
			}
		});
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public static void saveAlts() {
		List<String> content = Lists.newArrayList();
		for (Account account : accounts) {
			content.add(account.getEmail() + ":" + account.getPassword() + ":" + account.getUsername());
		}
		FileUtils.write(alts, content, true);
	}

}
