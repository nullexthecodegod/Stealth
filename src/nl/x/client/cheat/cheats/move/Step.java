package nl.x.client.cheat.cheats.move;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventAlways;
import nl.x.client.cheat.cheats.move.step.StepMode;
import nl.x.client.cheat.cheats.move.step.impl.AAC;
import nl.x.client.cheat.cheats.move.step.impl.NCP;

/**
 * @author NullEX
 *
 */
@Info(name = "Step")
public class Step extends Cheat {
	public ArrayValue modeList = new ArrayValue("Mode", Lists.newArrayList("NCP", "AAC"), "NCP");
	public List<StepMode> modes = Lists.newArrayList(new NCP(this), new AAC(this));
	public StepMode mode;

	public Step() {
		this.addValue(this.modeList);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventAlways) {
			for (StepMode t : this.modes) {
				if (t.getName().equalsIgnoreCase(this.modeList.getValue().toString())) {
					this.mode = t;
					break;
				}
			}
		}
		if (Objects.nonNull(this.mode)) {
			this.setSuffix(this.mode.getName());
			this.mode.onEvent(e);
		}
		super.onEvent(e);
	}

}
