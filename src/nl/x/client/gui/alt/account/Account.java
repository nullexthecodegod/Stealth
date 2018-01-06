package nl.x.client.gui.alt.account;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import nl.x.api.utils.misc.Timer;
import nl.x.api.utils.misc.Utilites;
import nl.x.api.utils.render.RenderUtil;
import nl.x.client.gui.alt.GuiAltmanager;

/**
 * @author NullEX
 *
 */
public class Account {
	public boolean clicked = false;
	public Timer timer = new Timer();
	private String email, password, username, hidden;
	private int x, y;
	public Minecraft mc = Minecraft.getMinecraft();
	public int width;

	/**
	 * @param email
	 * @param password
	 * @param x
	 * @param y
	 */
	public Account(String email, String password, int x, int y) {
		this.email = email;
		this.username = email;
		this.password = password;
		this.hidden = "";
		for (int i = 0; i < this.password.length(); i++) {
			this.hidden += "*";
		}
		if (mc.fontRendererObj.getStringWidth(this.username) > width) {
			width = mc.fontRendererObj.getStringWidth(this.username + " ");
		}
		if (mc.fontRendererObj.getStringWidth(this.hidden) > width) {
			width = mc.fontRendererObj.getStringWidth(this.hidden + " ");
		}
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param partialTicks
	 */
	public void render(int mouseX, int mouseY, float partialTicks) {
		if (this.clicked) {
			if (this.timer.hasPassed(700)) {
				this.clicked = false;
			}
		} else {
			this.timer.reset();
		}
		int color = Color.DARK_GRAY.getRGB();
		RenderUtil.drawBorderedRect(this.getX(), this.getY() - 2, this.getX() + width + 4, this.getY() + 18, 1,
				new Color(163, 163, 163).getRGB(), color);
		mc.fontRendererObj.drawString(this.getUsername(), this.getX() + 3, this.getY(), -1);
		mc.fontRendererObj.drawString(this.hidden, this.getX() + 5, this.getY() + 10, -1);
	}

	/**
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param mouseButton
	 */
	public void click(int mouseX, int mouseY, int mouseButton) {
		if (this.isHoverd(mouseX, mouseY)) {
			if (mouseButton == 0) {
				if (!this.clicked) {
					this.clicked = true;
				} else {
					GuiAltmanager.info = Utilites.INSTANCE.login(this.getEmail(), this.getPassword());
					this.clicked = false;
				}
			}
		}
	}

	public boolean isHoverd(int mouseX, int mouseY) {
		return mouseX > this.getX() - 1 && mouseX < this.getX() + width + 4 && mouseY > this.getY()
				&& mouseY < this.getY() + 18;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
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
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
		this.width = 0;
		if (mc.fontRendererObj.getStringWidth(this.username) > width) {
			width = mc.fontRendererObj.getStringWidth(this.username + " ");
		}
		if (mc.fontRendererObj.getStringWidth(this.hidden) > width) {
			width = mc.fontRendererObj.getStringWidth(this.hidden + " ");
		}
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
