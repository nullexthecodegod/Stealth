package nl.x.client.cheat.cheats.display;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.Cheat.Category;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.client.gui.click.New.NewClickGui;
import nl.x.client.gui.click.old.OldClickGUI;

/**
 * @author NullEX
 *
 */
@Info(name = "ClickGUI", category = Category.Display, bind = Keyboard.KEY_RSHIFT, shown = false)
public class GUI extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("Old", "New"), "Old");

	public GUI() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#enable()
	 */
	@Override
	public void enable() {
		switch (this.mode.getValue().toString().toLowerCase()) {
			case "old":
				mc.displayGuiScreen(new OldClickGUI());
				break;
			case "new":
				mc.displayGuiScreen(new NewClickGui());
				break;
			case "dev":
				break;
			case "dev2":
				break;
		}
		this.toggle();

		super.enable();
	}

}
