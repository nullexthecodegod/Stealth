package nl.x.client.cheat.cheats.fight.aura.impl;

import java.util.ArrayList;
import java.util.Objects;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.pathfinder.WalkNodeProcessor;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventUpdate;
import nl.x.api.utils.entity.player.PlayerHelper;
import nl.x.api.utils.entity.player.nodes.Node;
import nl.x.api.utils.entity.player.nodes.NodeProcessor;
import nl.x.api.utils.misc.Timer;
import nl.x.client.cheat.cheats.fight.Aura;
import nl.x.client.cheat.cheats.fight.aura.AuraMode;

/**
 * @author NullEX
 *
 */
public class TPAura extends AuraMode {
	public EntityLivingBase target;
	public Timer timer = new Timer();
	private PathFinder path = new PathFinder(new WalkNodeProcessor());
	private NodeProcessor processor = new NodeProcessor();
	public ArrayList<Vec3> back = Lists.newArrayList();

	/**
	 * @param parent
	 */
	public TPAura(Aura parent) {
		super("TPAura", parent);
	}

	/*
	 * @see nl.x.client.cheat.cheats.fight.aura.AuraMode#onEvent(nl.x.api.event.
	 * Event)
	 */
	@Override
	public void onEvent(Event ev) {
		if (ev instanceof EventUpdate) {
			EventUpdate e = (EventUpdate) ev;
			switch (e.getState()) {
				case pre:
					this.target = PlayerHelper.getClosestEntity(500);
					if (Objects.nonNull(this.target) && this.timer.hasPassed(70)) {
						processor.getPath(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
								new BlockPos(this.target.posX, this.target.posY, this.target.posZ));
					}
					break;
				case post:
					if (Objects.nonNull(this.target) && this.timer.hasPassed(75)) {
						for (Node node : processor.path) {
							BlockPos pos = node.getBlockpos();
							mc.thePlayer.sendQueue.addToSendQueue(
									new C03PacketPlayer.C04PacketPlayerPosition(node.getBlockpos().getX() + 0.5,
											node.getBlockpos().getY(), node.getBlockpos().getZ() + 0.5, true));
							back.add(new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
						}
						this.getParent().attack(this.target);
						for (int i = back.size() - 1; i > -1; i--) {
							mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
									back.get(i).xCoord, back.get(i).yCoord, back.get(i).zCoord, true));
						}
						back.clear();
						this.timer.reset();
					}
					break;
			}
		}
		super.onEvent(ev);
	}

}
