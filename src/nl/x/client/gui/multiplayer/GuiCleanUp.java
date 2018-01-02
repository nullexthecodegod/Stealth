package nl.x.client.gui.multiplayer;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;

public class GuiCleanUp extends GuiScreen {
	private GuiMultiplayer prevMenu;
	private boolean removeAll, cleanupRename, setSelectedSlotIndexcleanupFailed, setSelectedSlotIndexcleanupGriefMe,
			setSelectedSlotIndexcleanupRename, setSelectedSlotIndexcleanupUnknown, setSelectedSlotIndexcleanupOutdated;
	private String[] toolTips;

	public GuiCleanUp(final GuiMultiplayer prevMultiplayerMenu) {
		this.toolTips = new String[] { "",
				"Start the Clean Up with the settings\nyou specified above.\nIt might look like the game is not\nreacting for a couple of seconds.",
				"Servers that clearly don't exist.", "Servers that run a different Minecraft\nversion than you.",
				"All servers that failed the last ping.\nMake sure that the last ping is complete\nbefore you do this. That means: Go back,\npress the refresh button and wait until\nall servers are done refreshing.",
				"All servers where the name starts with \"Fuck me\"\nUseful for removing servers found by ServerFinder.",
				"This will completely clear your server\nlist. §cUse with caution!§r",
				"Renames your servers to \"Fuck me #1\",\n\"Fuck me #2\", etc." };
		this.prevMenu = prevMultiplayerMenu;
	}

	@Override
	public void updateScreen() {
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 144 + 12, "Cancel"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 168 + 12, "Clean Up"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 - 24 + 12,
				"Unknown Hosts: " + this.removeOrKeep(setSelectedSlotIndexcleanupUnknown)));
		this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 0 + 12,
				"Outdated Servers: " + this.removeOrKeep(setSelectedSlotIndexcleanupOutdated)));
		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + 12,
				"Failed Ping: " + this.removeOrKeep(setSelectedSlotIndexcleanupFailed)));
		this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + 12,
				"\"Fuck me\" Servers: " + this.removeOrKeep(setSelectedSlotIndexcleanupGriefMe)));
		this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height / 4 + 72 + 12,
				"§cRemove all Servers: " + this.yesOrNo(this.removeAll)));
		this.buttonList.add(new GuiButton(7, this.width / 2 - 100, this.height / 4 + 96 + 12,
				"Rename all Servers: " + this.yesOrNo(setSelectedSlotIndexcleanupRename)));
	}

	private String yesOrNo(final boolean bool) {
		return bool ? "Yes" : "No";
	}

	private String removeOrKeep(final boolean bool) {
		return bool ? "Remove" : "Keep";
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.enabled) {
			if (clickedButton.id == 0) {
				mc.displayGuiScreen(this.prevMenu);
			} else if (clickedButton.id == 1) {
				if (this.removeAll) {
					this.prevMenu.savedServerList.clearServerList();
					this.prevMenu.savedServerList.saveServerList();
					this.prevMenu.serverListSelector.setSelectedSlotIndex(-1);
					this.prevMenu.serverListSelector.func_148195_a(this.prevMenu.savedServerList);
					mc.displayGuiScreen(this.prevMenu);
					return;
				}
				for (int i = this.prevMenu.savedServerList.countServers() - 1; i >= 0; --i) {
					final ServerData server = this.prevMenu.savedServerList.getServerData(i);
					if ((setSelectedSlotIndexcleanupUnknown
							&& server.serverMOTD.equals(EnumChatFormatting.DARK_RED + "Can't resolve hostname"))
							|| (setSelectedSlotIndexcleanupOutdated && server.version != 47)
							|| (setSelectedSlotIndexcleanupFailed && server.pingToServer != -2L
									&& server.pingToServer < 0L)
							|| (setSelectedSlotIndexcleanupGriefMe && server.serverName.startsWith("Fuck me"))) {
						this.prevMenu.savedServerList.removeServerData(i);
						this.prevMenu.savedServerList.saveServerList();
						this.prevMenu.serverListSelector.setSelectedSlotIndex(-1);
						this.prevMenu.serverListSelector.func_148195_a(this.prevMenu.savedServerList);
					}
				}
				if (setSelectedSlotIndexcleanupRename) {
					for (int i = 0; i < this.prevMenu.savedServerList.countServers(); ++i) {
						final ServerData server = this.prevMenu.savedServerList.getServerData(i);
						server.serverName = "Fuck me #" + (i + 1);
						this.prevMenu.savedServerList.saveServerList();
						this.prevMenu.serverListSelector.setSelectedSlotIndex(-1);
						this.prevMenu.serverListSelector.func_148195_a(this.prevMenu.savedServerList);
					}
				}
				mc.displayGuiScreen(this.prevMenu);
			} else if (clickedButton.id == 2) {
				setSelectedSlotIndexcleanupUnknown = !setSelectedSlotIndexcleanupUnknown;
				clickedButton.displayString = "Unknown Hosts: " + this.removeOrKeep(setSelectedSlotIndexcleanupUnknown);

			} else if (clickedButton.id == 3) {
				setSelectedSlotIndexcleanupOutdated = !setSelectedSlotIndexcleanupOutdated;
				clickedButton.displayString = "Outdated Servers: "
						+ this.removeOrKeep(setSelectedSlotIndexcleanupOutdated);
			} else if (clickedButton.id == 4) {
				setSelectedSlotIndexcleanupFailed = !setSelectedSlotIndexcleanupFailed;
				clickedButton.displayString = "Failed Ping: " + this.removeOrKeep(setSelectedSlotIndexcleanupFailed);

			} else if (clickedButton.id == 5) {
				setSelectedSlotIndexcleanupGriefMe = !setSelectedSlotIndexcleanupGriefMe;

				clickedButton.displayString = "\"Fuck me\" Servers: "
						+ this.removeOrKeep(setSelectedSlotIndexcleanupGriefMe);
			} else if (clickedButton.id == 6) {
				this.removeAll = !this.removeAll;
				clickedButton.displayString = "§cRemove all Servers: " + this.yesOrNo(this.removeAll);
			} else if (clickedButton.id == 7) {
				setSelectedSlotIndexcleanupRename = !setSelectedSlotIndexcleanupRename;
				clickedButton.displayString = "Rename all Servers: " + this.yesOrNo(setSelectedSlotIndexcleanupRename);
			}
		}
	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		if (par2 == 28 || par2 == 156) {
			this.actionPerformed(this.buttonList.get(0));
		}
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Clean Up", this.width / 2, 20, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Please select the servers you want to remove:", this.width / 2,
				36, 10526880);
		super.drawScreen(par1, par2, par3);
		for (int i = 0; i < this.buttonList.size(); ++i) {
			final GuiButton button = this.buttonList.get(i);
			if (button.isMouseOver() && !this.toolTips[button.id].isEmpty()) {
				final ArrayList toolTip = Lists.newArrayList((Object[]) this.toolTips[button.id].split("\n"));
				this.drawHoveringText(toolTip, par1, par2);
				break;
			}
		}
	}
}
