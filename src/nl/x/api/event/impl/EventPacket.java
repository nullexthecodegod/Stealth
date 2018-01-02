package nl.x.api.event.impl;

import net.minecraft.network.Packet;
import nl.x.api.event.Event;

public class EventPacket extends Event {
	private Type type;
	private Packet packet;

	public EventPacket(Type type, Packet packet) {
		super();
		this.type = type;
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		inbound, outbound;
	}

}
