package nl.x.client.cheat.cheats.move;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventAlways;
import nl.x.api.utils.misc.Timer;
import nl.x.client.cheat.cheats.move.flight.FlightMode;
import nl.x.client.cheat.cheats.move.flight.impl.Hypixel;
import nl.x.client.cheat.cheats.move.flight.impl.Mineplex;
import nl.x.client.cheat.cheats.move.flight.impl.Normal;
import nl.x.client.cheat.cheats.move.flight.impl.Test;

/**
 * @author NullEX
 *
 */
@Info(name = "Flight")
public class Flight extends Cheat {
	public ArrayValue modeValue = new ArrayValue("Mode", Lists.newArrayList("Hypixel", "Normal", "Mineplex", "Test"),
			"Normal");
	public FlightMode mode;
	public List<FlightMode> modes = Lists.newArrayList(new Normal(this), new Mineplex(this), new Hypixel(this),
			new Test(this));
	public Timer timer = new Timer();

	public Flight() {
		this.addValue(this.modeValue);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 * 
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventAlways) {
			/*
			 * Removed the break method that might have caused errors with the
			 * for loop
			 */
			for (FlightMode t : this.modes) {
				if (t.getName().equalsIgnoreCase(this.modeValue.getValue().toString())) {
					this.mode = t;
				} else {
					continue;
				}
			}
		}
		if (Objects.nonNull(this.mode)) {
			this.setSuffix(this.mode.getName());
			this.mode.onEvent(e);
		}
		super.onEvent(e);
	}

	@Override
	public void enable() {
		mc.timer.timerSpeed = 1f;
		super.enable();
	}

	@Override
	public void disable() {
		mc.timer.timerSpeed = 1f;
		super.disable();
	}

}
