package nl.x.client.gui.click.old.button.impl.button;

/**
 * @author NullEX
 *
 */
public abstract class Button {
	private int x, y;
	private int max;

	public Button(int x, int y) {
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

}
