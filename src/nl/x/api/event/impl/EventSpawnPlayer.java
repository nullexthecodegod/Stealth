package nl.x.api.event.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class EventSpawnPlayer extends Event {
	private S0CPacketSpawnPlayer packet;
	private double x, y, z;
	private State state;
	private EntityPlayer player;

	/**
	 * @param packet
	 * @param x
	 * @param y
	 * @param z
	 * @param state
	 * @param player
	 */
	public EventSpawnPlayer(S0CPacketSpawnPlayer packet, double x, double y, double z, State state,
			EntityPlayer player) {
		this.packet = packet;
		this.x = x;
		this.y = y;
		this.z = z;
		this.state = state;
		this.player = player;
	}

	/**
	 * @return the packet
	 */
	public S0CPacketSpawnPlayer getPacket() {
		return packet;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @return the player
	 */
	public EntityPlayer getPlayer() {
		return player;
	}

}
