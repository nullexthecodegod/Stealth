package nl.x.client.cheat.cheats.display;

import com.google.common.collect.Lists;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;

/**
 * @author NullEX
 *
 */
@Info(name = "Fullbright")
public class Fullbright extends Cheat {
	public ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("Gamma", "Potion"), "Gamma");
	public float last;
	public boolean set = false;

	public Fullbright() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#enable()
	 */
	@Override
	public void enable() {
		if (this.mode.getValue().toString().equalsIgnoreCase("gamma")) {
			this.last = mc.gameSettings.gammaSetting;
			mc.gameSettings.gammaSetting = 1000;
		} else {
			if (!mc.thePlayer.isPotionActive(Potion.nightVision.getId())) {
				mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Integer.MAX_VALUE));
				set = true;
			}
		}
		super.enable();
	}

	/*
	 * @see nl.x.api.cheat.Cheat#disable()
	 */
	@Override
	public void disable() {
		if (this.mode.getValue().toString().equalsIgnoreCase("gamma")) {
			mc.gameSettings.gammaSetting = this.last;
		} else {
			if (set) {
				mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
			}
		}
		super.disable();
	}

}
