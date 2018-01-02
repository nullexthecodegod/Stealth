package nl.x.client.gui.alt.New;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import nl.x.api.utils.misc.Texture;
import nl.x.api.utils.misc.TextureUtil;
import nl.x.client.Client;

public class GuiListAccountSelectionEntry implements GuiListExtended.IGuiListEntry {

	private static final ResourceLocation ICON_MISSING = new ResourceLocation("client/textures/default_account.png");
	private static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation(
			"textures/gui/world_selection.png");
	private final Minecraft client;
	public final Account account;
	private final GuiListAccountSelection containingListSel;
	private long lastClickTime;

	public GuiListAccountSelectionEntry(GuiListAccountSelection listWorldSelIn, Account p_i46591_2_) {
		this.containingListSel = listWorldSelIn;
		this.account = p_i46591_2_;
		this.client = Minecraft.getMinecraft();
	}

	@Override
	public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY,
			boolean isSelected) {
		String s = this.account.login;
		String s1 = Client.INSTANCE.accountManager.showPasswords ? this.account.password
				: this.account.password.replaceAll(".", "*");
		String s2 = this.account.username;
		String s3 = (this.account.session != null ? "(Cached) " : "") + s2;

		if (s.contains("@")) {
			String[] parts = s.split("@");
			String pre = parts[0];
			if (pre.length() > 2) {
				pre = pre.substring(0, 1) + pre.substring(1, pre.length() - 1).replaceAll(".", "*")
						+ pre.substring(pre.length() - 1);
				s = pre + "@" + parts[1];
			}
		}

		this.client.fontRendererObj.drawString(s, x + 32 + 3, y + 1, 16777215);
		this.client.fontRendererObj.drawString(s1, x + 32 + 3, y + this.client.fontRendererObj.FONT_HEIGHT + 3,
				8421504);
		this.client.fontRendererObj.drawString(s3, x + 32 + 3,
				y + this.client.fontRendererObj.FONT_HEIGHT + this.client.fontRendererObj.FONT_HEIGHT + 3, 8421504);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		Texture texture = TextureUtil.download(
				String.format("https://crafatar.com/avatars/%s?size=32&overlay=true", s2.isEmpty() ? "Steve" : s2));
		this.client.getTextureManager()
				.bindTexture(texture != null && texture.resource != null ? texture.resource : ICON_MISSING);

		GlStateManager.enableBlend();
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
		GlStateManager.disableBlend();

		if (this.client.gameSettings.touchscreen || isSelected) {
			this.client.getTextureManager().bindTexture(ICON_OVERLAY_LOCATION);
			Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			int j = mouseX - x;
			int i = j < 32 ? 32 : 0;

			Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, i, 32, 32, 256.0F, 256.0F);
		}
	}

	/**
	 * Called when the mouse is clicked within this entry. Returning true means
	 * that something within this entry was clicked and the list should not be
	 * dragged.
	 */
	@Override
	public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
		this.containingListSel.selectAccount(slotIndex);

		if (relativeX <= 32 && relativeX < 32) {
			this.authenticateAccount();
			return true;
		} else if (Minecraft.getSystemTime() - this.lastClickTime < 250L) {
			this.authenticateAccount();
			return true;
		} else {
			this.lastClickTime = Minecraft.getSystemTime();
			return false;
		}
	}

	public void authenticateAccount() {
		this.account.authenticate();
	}

	public void deleteAccount() {
		Client.INSTANCE.accountManager.remove(GuiListAccountSelectionEntry.this.account);
	}

	/**
	 * Fired when the mouse button is released. Arguments: index, x, y,
	 * mouseEvent, relativeX, relativeY
	 */
	@Override
	public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
	}

	@Override
	public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
	}
}
