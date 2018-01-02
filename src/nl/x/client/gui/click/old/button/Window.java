package nl.x.client.gui.click.old.button;

/**
 * @author NullEX
 *
 */
public abstract class Window {
	private int x, y;

	/**
	 * @param x
	 * @param y
	 */
	public Window(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void draw(int mouseX, int mouseY);

	public abstract void type(char typedChar, int keyCode);

	public abstract void click(int mouseX, int mouseY, int mouseButton);

	public abstract void release(int mouseX, int mouseY, int mouseButton);

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

}
