package me.x1machinemaker1x.shootinggallery.utils;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.SoundCategory;
import net.minecraft.server.v1_12_R1.SoundEffect;

public class PacketUtils {
	public static void sendActionBar(Player p, String message) {
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}

	public static void showTitle(Player p, String message, int fadeIn, int duration, int fadeOut) {
		PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
				IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), fadeIn, duration, fadeOut);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
	}

	public static void showSubTitle(Player p, String message, int fadeIn, int duration, int fadeOut) {
		PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), fadeIn, duration, fadeOut);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
	}

	public static void cancelTitles(Player p) {
		PacketPlayOutTitle clearTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR,
				IChatBaseComponent.ChatSerializer.a(""));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(clearTitle);
		PacketPlayOutTitle resetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET,
				IChatBaseComponent.ChatSerializer.a(""));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(resetTitle);
	}

	public static void playSound(Player p, SoundEffect s) {
		PacketPlayOutNamedSoundEffect sound = new PacketPlayOutNamedSoundEffect(s, SoundCategory.BLOCKS,
				p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 1.0F, 1.0F);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(sound);
	}
}
