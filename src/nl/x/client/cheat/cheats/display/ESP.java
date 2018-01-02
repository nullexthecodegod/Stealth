package nl.x.client.cheat.cheats.display;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
@Info(name = "ESP")
public class ESP extends Cheat {
	public static ArrayValue mode = new ArrayValue("Mode", Lists.newArrayList("Outline", "2D"), "Outline");

	public ESP() {
		this.addValue(this.mode);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
	}

}
