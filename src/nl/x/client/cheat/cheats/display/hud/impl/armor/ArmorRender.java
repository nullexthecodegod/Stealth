package nl.x.client.cheat.cheats.display.hud.impl.armor;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import nl.x.api.utils.misc.Utilites;
import nl.x.api.utils.render.cfont.CFontRenderer;

/**
 * @author NullEX
 *
 */
public class ArmorRender {
	public CFontRenderer font = new CFontRenderer(new Font("Adobe Courier", Font.PLAIN, 18), true, true);
	public int[] colors = new int[] { new Color(24, 173, 61).getRGB(), new Color(233, 244, 17).getRGB(),
			new Color(216, 0, 0).getRGB() };
	public Minecraft mc = Minecraft.getMinecraft();
	public int y, x, index;
	public ItemStack stack;

	/**
	 * @param stack
	 * @param y
	 */
	public ArmorRender(int y, int index) {
		this.y = y;
		this.index = index;
		this.x = 0;
	}

	public void render() {
		stack = mc.thePlayer.inventory.armorInventory[index];
		Gui.drawRect(Display.getWidth() / 2 - this.x, y, Display.getWidth() / 2, y + 19,
				new Color(0, 0, 0, 200).getRGB());
		if (Objects.nonNull(stack)) {
			this.x += 2;
			if (this.x > 18) {
				x = 18;
			}
			double ratio = 1;
			if (stack.getMaxDamage() > 0) {
				ratio = (stack.getMaxDamage() - stack.getItemDamage()) / (stack.getMaxDamage() / 3);
			}
			Gui.drawRect(Display.getWidth() / 2 - this.x, y,
					Display.getWidth() / 2 - x + 1, y
							+ 19,
					Utilites.INSTANCE.interpolate(ratio,
							(stack.getMaxDamage() > 0 ? (stack.getItemDamage() >= stack.getMaxDamage() / 2
									? this.colors[1] : this.colors[0]) : this.colors[2]),
							(stack.getMaxDamage() > 0 ? (stack.getItemDamage() >= stack.getMaxDamage() / 2
									? this.colors[2] : this.colors[1]) : this.colors[2])));
			GlStateManager.pushMatrix();
			GlStateManager.translate(1.5, 0.3, 0);
			this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, Display.getWidth() / 2 - this.x, y + 1);
			GlStateManager.popMatrix();
		} else {
			x -= 2;
			if (x < 2) {
				x = 2;
			}
		}
	}

}
