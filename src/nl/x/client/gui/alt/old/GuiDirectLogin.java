package nl.x.client.gui.alt.old;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import nl.x.client.gui.alt.AuthAccountLogin;

public class GuiDirectLogin extends GuiScreen {
	public GuiScreen parent;
	public String display = "";
	public GuiTextField usernameBox;
	public GuiPasswordField passwordBox;
	public GuiTextField sessionBox;

	public static String excutePost(String s, String s1) {
		HttpURLConnection httpsurlconnection = null;
		try {
			String s2;
			String s3;
			URL url = new URL(s);
			httpsurlconnection = (HttpsURLConnection) url.openConnection();
			httpsurlconnection.setRequestMethod("POST");
			httpsurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpsurlconnection.setRequestProperty("Content-Type", Integer.toString(s1.getBytes().length));
			httpsurlconnection.setRequestProperty("Content-Language", "en-US");
			httpsurlconnection.setUseCaches(false);
			httpsurlconnection.setDoInput(true);
			httpsurlconnection.setDoOutput(true);
			httpsurlconnection.connect();
			DataOutputStream dataoutputstream = new DataOutputStream(httpsurlconnection.getOutputStream());
			dataoutputstream.writeBytes(s1);
			dataoutputstream.flush();
			dataoutputstream.close();
			InputStream inputstream = httpsurlconnection.getInputStream();
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
			StringBuffer stringbuffer = new StringBuffer();
			while ((s2 = bufferedreader.readLine()) != null) {
				stringbuffer.append(s2);
				stringbuffer.append('\r');
			}
			bufferedreader.close();
			String string = s3 = stringbuffer.toString();
			return string;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		} finally {
			if (httpsurlconnection != null) {
				httpsurlconnection.disconnect();
			}
		}
	}

	public GuiDirectLogin(GuiScreen paramScreen) {
		this.parent = paramScreen;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 1) {
			if (!this.usernameBox.getText().trim().isEmpty() && !this.passwordBox.getText().trim().isEmpty()) {
				this.display = AuthAccountLogin.login(this.usernameBox.getText().trim(),
						this.passwordBox.getText().trim());
			} else if (!this.usernameBox.getText().trim().isEmpty() && !this.sessionBox.getText().trim().isEmpty()) {
				this.display = AuthAccountLogin.login(this.usernameBox.getText().trim(), "");
			} else if (!this.usernameBox.getText().trim().isEmpty()) {
				this.display = AuthAccountLogin.login(this.usernameBox.getText().trim(), "");
			}
		} else if (button.id == 2) {
			this.mc.displayGuiScreen(this.parent);
		}
	}

	@Override
	public void drawScreen(int x2, int y2, float f2) {
		this.drawDefaultBackground();
		this.drawString(mc.fontRendererObj, "§c*§rUsername", this.width / 2 - 100 - 6, 38, 10526880);
		this.drawString(mc.fontRendererObj, "Password", this.width / 2 - 100, 79, 10526880);
		this.drawString(mc.fontRendererObj, "Session ID (Advanced)", this.width / 2 - 100, 119, 10526880);
		this.drawString(mc.fontRendererObj, this.display, 1, 2, 10526880);
		this.drawString(mc.fontRendererObj, mc.getSession().getUsername(), 1, (this.display == "" ? 2 : 14), -1);
		this.drawString(mc.fontRendererObj, "Sorry bleach dev i cant make a alt login :(", 0, this.height - 13,
				10526880);
		try {
			this.usernameBox.drawTextBox();
			this.passwordBox.drawTextBox();
			this.sessionBox.drawTextBox();
		} catch (Exception err) {
			err.printStackTrace();
		}
		super.drawScreen(x2, y2, f2);
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, height / 4 + 96 + 12, "Login"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, height / 4 + 96 + 36, "Back"));
		this.usernameBox = new GuiTextField(-1, mc.fontRendererObj, this.width / 2 - 100, 51, 200, 20);
		this.passwordBox = new GuiPasswordField(mc.fontRendererObj, this.width / 2 - 100, 91, 200, 20);
		this.sessionBox = new GuiTextField(-1, mc.fontRendererObj, this.width / 2 - 100, 131, 200, 20);
		this.usernameBox.setMaxStringLength(200);
		this.passwordBox.setMaxStringLength(128);
		this.sessionBox.setMaxStringLength(257);
	}

	@Override
	protected void keyTyped(char c2, int i2) {
		this.usernameBox.textboxKeyTyped(c2, i2);
		this.passwordBox.textboxKeyTyped(c2, i2);
		this.sessionBox.textboxKeyTyped(c2, i2);
		if (c2 == '\t') {
			if (this.usernameBox.isFocused()) {
				this.usernameBox.setFocused(false);
				this.passwordBox.setFocused(true);
				this.sessionBox.setFocused(false);
			} else if (this.passwordBox.isFocused()) {
				this.usernameBox.setFocused(false);
				this.passwordBox.setFocused(false);
				this.sessionBox.setFocused(true);
			} else if (this.sessionBox.isFocused()) {
				this.usernameBox.setFocused(true);
				this.passwordBox.setFocused(false);
				this.sessionBox.setFocused(false);
			}
		}
		if (c2 == '\r') {
			this.actionPerformed((GuiButton) this.buttonList.get(0));
		}
	}

	@Override
	public void mouseClicked(int x2, int y2, int b2) {
		this.usernameBox.mouseClicked(x2, y2, b2);
		this.passwordBox.mouseClicked(x2, y2, b2);
		this.sessionBox.mouseClicked(x2, y2, b2);
		try {
			super.mouseClicked(x2, y2, b2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		this.usernameBox.updateCursorCounter();
		this.passwordBox.updateCursorCounter();
		this.sessionBox.updateCursorCounter();
	}
}
