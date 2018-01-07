package nl.x.client.gui.tab;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.utils.render.RenderUtil;
import nl.x.client.Client;
import nl.x.client.cheat.CheatManager;

public class TabGui {
	private static final int NO_COLOR = 0;
	private static final int INSIDE_COLOR = 0xFF333333;
	private static final int BORDER_COLOR = 2013265920;
	private static final int COMPONENT_HEIGHT = 14;
	private static int baseCategoryWidth;
	private static int baseCategoryHeight;
	private static int baseModWidth;
	private static int baseModHeight;
	private static Section section;
	private static int categoryTab;
	private static int modTab;
	private static int categoryPosition;
	private static int categoryTargetPosition;
	private static int modPosition;
	private static int modTargetPosition;
	private static boolean transitionQuickly;
	private static long lastUpdateTime;

	static {
		section = Section.CATEGORY;
		categoryTab = 0;
		modTab = 0;
		categoryPosition = 15;
		categoryTargetPosition = 15;
		modPosition = 15;
		modTargetPosition = 15;
	}

	public static void init() {
		int highestWidth = 0;
		Category[] values = Category.values();
		int length = values.length;
		int i = 0;
		while (i < length) {
			Category category = values[i];
			String name = TabGui.capitalize(category.name().toLowerCase());
			int stringWidth = Client.INSTANCE.tahoma.getStringWidth(name);
			highestWidth = Math.max(stringWidth, highestWidth);
			++i;
		}
		baseCategoryWidth = highestWidth + 6;
		baseCategoryHeight = Category.values().length * 14 + 2;
	}

	public static void render() {
		String name;
		TabGui.updateBars();
		RenderUtil.drawRect(2.0f, 14.0f, 2 + baseCategoryWidth, 14 + baseCategoryHeight, 0xFF333333);
		RenderUtil.drawRect(3.0f, categoryPosition, 2 + baseCategoryWidth - 1, categoryPosition + 14,
				new Color(245, 17, 85).getRGB());
		int yPos = 15;
		int yPosBottom = 29;
		int i = 0;
		while (i < Category.values().length) {
			Category category = Category.values()[i];
			name = TabGui.capitalize(category.name().toLowerCase());
			Client.INSTANCE.tahoma.drawStringWithShadow(name,
					baseCategoryWidth / 2 - Client.INSTANCE.tahoma.getStringWidth(name) / 2 + 3, yPos + 3, -1);
			yPos += 14;
			yPosBottom += 14;
			++i;
		}
		if (section == Section.MODS) {
			RenderUtil.drawRect(baseCategoryWidth + 4, categoryPosition - 1, baseCategoryWidth + 2 + baseModWidth,
					categoryPosition + TabGui.getModsInCategory(Category.values()[categoryTab]).size() * 14 + 1,
					0xFF333333);
			RenderUtil.drawRect(baseCategoryWidth + 5, modPosition, baseCategoryWidth + baseModWidth + 1,
					modPosition + 14, new Color(245, 17, 85).getRGB());
			yPos = categoryPosition;
			yPosBottom = categoryPosition + 14;
			i = 0;
			while (i < TabGui.getModsInCategory(Category.values()[categoryTab]).size()) {
				Cheat mod = TabGui.getModsInCategory(Category.values()[categoryTab]).get(i);
				name = mod.getName();
				Client.INSTANCE.tahoma.drawStringWithShadow(name,
						baseCategoryWidth + baseModWidth / 2 - Client.INSTANCE.tahoma.getStringWidth(name) / 2 + 3,
						yPos + 3, mod.isEnabled() ? -1 : -4210753);
				yPos += 14;
				yPosBottom += 14;
				++i;
			}
		}
	}

	/*
	 * Enabled force condition propagation Lifted jumps to return sites
	 */
	public static void keyPress(int key) {
		if (section == Section.CATEGORY) {
			switch (key) {
				case 200: {
					categoryTargetPosition -= 14;
					if (--categoryTab >= 0)
						return;
					transitionQuickly = true;
					categoryTargetPosition = 15 + (Category.values().length - 1) * 14;
					categoryTab = Category.values().length - 1;
					return;
				}
				case 205:

					int highestWidth = 0;
					for (Cheat Cheat : TabGui.getModsInCategory(Category.values()[categoryTab])) {
						String name = Cheat.getName();
						int stringWidth = Client.INSTANCE.tahoma.getStringWidth(name);
						highestWidth = Math.max(stringWidth, highestWidth);
					}
					baseModWidth = highestWidth + 6;
					baseModHeight = TabGui.getModsInCategory(Category.values()[categoryTab]).size() * 14 + 2;
					modTargetPosition = TabGui.modPosition = categoryTargetPosition;
					modTab = 0;
					section = Section.MODS;
					break;
				case 208: {
					categoryTargetPosition += 14;
					if (++categoryTab <= Category.values().length - 1)
						return;
					transitionQuickly = true;
					categoryTargetPosition = 15;
					categoryTab = 0;
					return;
				}
				default: {
					return;
				}
			}
		} else {
			if (section != Section.MODS)
				return;
			switch (key) {
				case 203: {
					section = Section.CATEGORY;
					return;
				}
				case 205: {
					Cheat mod = TabGui.getModsInCategory(Category.values()[categoryTab]).get(modTab);
					mod.toggle();
					return;
				}
				case 208: {
					modTargetPosition += 14;
					if (++modTab <= TabGui.getModsInCategory(Category.values()[categoryTab]).size() - 1)
						return;
					transitionQuickly = true;
					modTargetPosition = categoryTargetPosition;
					modTab = 0;
					return;
				}
				case 200: {
					modTargetPosition -= 14;
					if (--modTab >= 0)
						return;
					transitionQuickly = true;
					modTargetPosition = categoryTargetPosition
							+ (TabGui.getModsInCategory(Category.values()[categoryTab]).size() - 1) * 14;
					modTab = TabGui.getModsInCategory(Category.values()[categoryTab]).size() - 1;
				}
			}
		}
	}

	private static void updateBars() {
		long timeDifference = System.currentTimeMillis() - lastUpdateTime;
		lastUpdateTime = System.currentTimeMillis();
		int increment = transitionQuickly ? 100 : 20;
		increment = Math.max(1, Math.round(increment * timeDifference / 100));
		if (categoryPosition < categoryTargetPosition) {
			if ((categoryPosition += increment) >= categoryTargetPosition) {
				categoryPosition = categoryTargetPosition;
				transitionQuickly = false;
			}
		} else if (categoryPosition > categoryTargetPosition
				&& (categoryPosition -= increment) <= categoryTargetPosition) {
			categoryPosition = categoryTargetPosition;
			transitionQuickly = false;
		}
		if (modPosition < modTargetPosition) {
			if ((modPosition += increment) >= modTargetPosition) {
				modPosition = modTargetPosition;
				transitionQuickly = false;
			}
		} else if (modPosition > modTargetPosition && (modPosition -= increment) <= modTargetPosition) {
			modPosition = modTargetPosition;
			transitionQuickly = false;
		}
	}

	private static List<Cheat> getModsInCategory(Category category) {
		ArrayList<Cheat> modList = new ArrayList<Cheat>();
		for (Cheat mod : CheatManager.INSTANCE.buffer) {
			if (mod.getCategory() != category)
				continue;
			modList.add(mod);
		}
		return modList;
	}

	public static String capitalize(String line) {
		return String.valueOf(String.valueOf(Character.toUpperCase(line.charAt(0)))) + line.substring(1);
	}

	public static enum Section {
		MODS, CATEGORY;
	}

}
