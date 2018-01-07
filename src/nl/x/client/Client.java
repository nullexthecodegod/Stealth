package nl.x.client;

import java.awt.Font;
import java.util.HashMap;

import org.lwjgl.opengl.Display;

import com.google.common.collect.Maps;

import nl.x.api.cheat.Cheat;
import nl.x.api.command.CommandManager;
import nl.x.api.utils.misc.Logger;
import nl.x.api.utils.misc.Logger.LogType;
import nl.x.api.utils.render.cfont.CFontRenderer;
import nl.x.client.cheat.CheatManager;
import nl.x.client.gui.tab.TabGui;

/**
 * @author NullEX
 *
 */
public enum Client {
	INSTANCE;

	public static HashMap<String, String> info = Maps.newHashMap();
	public Logger logger;
	public TabGui tab;
	public CFontRenderer tahoma = new CFontRenderer(new Font("Helvetica", 0, 19), true, true);
	public int serverFinderThreads = 128;

	public void initiate() {
		/*
		 * Old protection class [ not used anymore due to me losing of pastebin
		 * account :( ]
		 * 
		 * Property.whitelist();
		 * 
		 */
		try {
			/**
			 * New protection method uses same method of protection but with a
			 * few more checks
			 * 
			 * 
			 * PlayerHelper.INSTANCE.fuckme();
			 */
		} catch (Exception e) {
			System.exit(-1);
			e.printStackTrace();
		}
		this.info.put("prefix", "-");
		this.info.put("version", "0.1");
		this.info.put("name", "Stealth");
		this.info.put("dev", "NullEX");
		String version = this.info.get("version");
		Display.setTitle(this.info.get("name") + (version.contains(".") ? " v" : " b") + version);
		this.logger = new Logger(this.info.get("name"));
		this.tab = new TabGui();
		CheatManager.INSTANCE.load();
		CommandManager.INSTANCE.init();
		tab.init();
		Runtime.getRuntime().addShutdownHook(new Thread(this.info.get("name") + " Shutdown Thread") {
			@Override
			public void run() {
				Client.INSTANCE.logger.log(info.get("name") + " is shutting down", LogType.info);
				super.run();
			}
		});
		System.out.println("100%");
	}

	public void keyPress(int key) {
		for (Cheat c : CheatManager.INSTANCE.buffer) {
			if (c.getBind() == key) {
				c.toggle();
			}
		}
		tab.keyPress(key);
	}

	public static OS getPlatform() {
		String s = System.getProperty("os.name").toLowerCase();
		return s.contains("win") ? OS.WINDOWS
				: (s.contains("mac") ? OS.MACOS
						: (s.contains("solaris") ? OS.SOLARIS
								: (s.contains("sunos") ? OS.SOLARIS
										: (s.contains("linux") ? OS.LINUX
												: (s.contains("unix") ? OS.LINUX : OS.UNKNOWN)))));
	}

	public enum OS {
		LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN
	}

}
