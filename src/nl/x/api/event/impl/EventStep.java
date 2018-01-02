package nl.x.api.event.impl;

import net.minecraft.entity.Entity;
import nl.x.api.event.Event;

/**
 * @author NullEX
 *
 */
public class EventStep extends Event {

	private float stepHeight;
	private Entity entity;
	private State State;

	/**
	 * @param stepHeight
	 * @param entity
	 * @param state
	 */
	public EventStep(float stepHeight, Entity entity, nl.x.api.event.Event.State state) {
		this.stepHeight = stepHeight;
		this.entity = entity;
		State = state;
	}

	/**
	 * @return the stepHeight
	 */
	public float getStepHeight() {
		return stepHeight;
	}

	/**
	 * @param stepHeight
	 *            the stepHeight to set
	 */
	public void setStepHeight(float stepHeight) {
		this.stepHeight = stepHeight;
	}

	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return State;
	}

}
