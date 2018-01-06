package nl.x.client.gui.tab.components;

import net.minecraft.client.Minecraft;

/**
 * @author NullEX
 *
 */
public abstract class TabComponent {
	private String render;
	private int x, y, index;
	private boolean extended = false;
	public Minecraft mc = Minecraft.getMinecraft();

	/**
	 * @param render
	 * @param x
	 * @param y
	 */
	public TabComponent(String render, int x, int y) {
		this.render = render;
		this.x = x;
		this.y = y;
	}

	public abstract void render();

	/**
	 * 
	 * @param key
	 */
	public abstract void action(int key);

	/**
	 * @return the render
	 */
	public String getRender() {
		return render;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the extended
	 */
	public boolean isExtended() {
		return extended;
	}

	/**
	 * @param extended
	 *            the extended to set
	 */
	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	/**
	 * @return the mc
	 */
	public Minecraft getMc() {
		return mc;
	}

	/**
	 * @param render
	 *            the render to set
	 */
	public void setRender(String render) {
		this.render = render;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
