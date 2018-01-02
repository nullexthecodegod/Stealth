package nl.x.api.command;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import nl.x.client.Client;

/**
 * @author NullEX
 *
 */
public abstract class Command {
	public String name, description;

	/**
	 * @param name
	 * @param description
	 */
	public Command(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public abstract void execute(String message, String[] args);

	public static void log(String message) {
		if (Objects.nonNull(Minecraft.getMinecraft().thePlayer)) {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
					new ChatComponentText("§e[§c" + Client.info.get("name") + "§e]§7 " + message));
		}
	}

}
