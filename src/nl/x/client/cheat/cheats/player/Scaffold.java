package nl.x.client.cheat.cheats.player;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.cheat.value.values.BooleanValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventAlways;
import nl.x.client.cheat.cheats.player.scaffold.ScaffoldMode;
import nl.x.client.cheat.cheats.player.scaffold.impl.NewScaffold;

/**
 * @author NullEX
 *
 */
@Info(name = "Scaffold")
public class Scaffold extends Cheat {
	public ArrayValue modeValue = new ArrayValue("Mode", Lists.newArrayList("New"), "New");
	public BooleanValue tower = new BooleanValue("Tower", true);
	public List<ScaffoldMode> modes = Lists.newArrayList(new NewScaffold(this));
	public ScaffoldMode mode;

	public Scaffold() {
		this.addValue(this.modeValue, this.tower);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventAlways) {
			for (ScaffoldMode t : this.modes) {
				if (t.getName().equalsIgnoreCase(this.modeValue.getValue().toString())) {
					this.mode = t;
					break;
				}
			}
		}
		if (Objects.nonNull(this.mode)) {
			this.mode.onEvent(e);
		}
		super.onEvent(e);
	}

	private boolean IsValidBlock(BlockPos pos) {
		Block block = mc.theWorld.getBlockState(pos).getBlock();
		return (!(block instanceof BlockLiquid)) && (block.getMaterial() != Material.air);
	}

}
