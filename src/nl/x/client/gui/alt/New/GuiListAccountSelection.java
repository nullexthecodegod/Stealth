package nl.x.client.gui.alt.New;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import nl.x.client.Client;

public class GuiListAccountSelection extends GuiListExtended {
	private static final Logger LOGGER = LogManager.getLogger();
	private final GuiAccountSelection accountSelectionObj;
	private final List<GuiListAccountSelectionEntry> entries = Lists.newArrayList();

	private int selectedIdx = -1;

	public GuiListAccountSelection(GuiAccountSelection p_i46590_1_, Minecraft clientIn, int p_i46590_3_,
			int p_i46590_4_, int p_i46590_5_, int p_i46590_6_, int p_i46590_7_) {
		super(clientIn, p_i46590_3_, p_i46590_4_, p_i46590_5_, p_i46590_6_, p_i46590_7_);
		this.accountSelectionObj = p_i46590_1_;
		this.refreshList();
	}

	public void refreshList() {
		this.entries.clear();
		Client.INSTANCE.accountManager.sort();
		Client.INSTANCE.accountManager.accounts
				.forEach((v) -> this.entries.add(new GuiListAccountSelectionEntry(this, v)));
	}

	/**
	 * Gets the IGuiListEntry object for the given index
	 */
	@Override
	public GuiListAccountSelectionEntry getListEntry(int index) {
		return this.entries.get(index);
	}

	@Override
	protected int getSize() {
		return this.entries.size();
	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 20;
	}

	/**
	 * Gets the width of the list
	 */
	@Override
	public int getListWidth() {
		return super.getListWidth() + 50;
	}

	public void selectAccount(int idx) {
		this.selectedIdx = idx;
		this.accountSelectionObj.selectAccount(this.getSelectedAccount());
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	@Override
	protected boolean isSelected(int slotIndex) {
		return slotIndex == this.selectedIdx;
	}

	public GuiListAccountSelectionEntry getSelectedAccount() {
		return this.selectedIdx >= 0 && this.selectedIdx < this.getSize() ? this.getListEntry(this.selectedIdx) : null;
	}

	public GuiAccountSelection getGuiAccountSelection() {
		return this.accountSelectionObj;
	}
}
