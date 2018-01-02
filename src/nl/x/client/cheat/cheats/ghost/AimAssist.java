package nl.x.client.cheat.cheats.ghost;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.NumberValue;

/**
 * @author NullEX
 *
 */
@Info(name = "AimAssist")
public class AimAssist extends Cheat {
	public NumberValue speed = new NumberValue("Speed", 20.0, 0.1, 100.0, 0.1);

	public AimAssist() {
		this.addValue(this.speed);
	}

}
