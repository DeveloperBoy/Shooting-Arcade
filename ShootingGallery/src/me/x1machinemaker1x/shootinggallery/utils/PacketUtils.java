package me.x1machinemaker1x.shootinggallery.utils;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PacketUtils {
	public static void sendActionBar(Player p, String message) {
		try {
			ActionBarMessager.sendHotBarMessage(p, message);
		} catch (Exception e) {
			//Do something
		}
	}
	
	/**
	 * Send a title to player
	 * @param player Player to send the title to
	 * @param text The text displayed in the title
	 * @param fadeInTime The time the title takes to fade in
	 * @param showTime The time the title is displayed
	 * @param fadeOutTime The time the title takes to fade out
	 * @param color The color of the title
	 */
	public static void sendTitle(Player player, String text, int fadeInTime, int showTime, int fadeOutTime) {
	    try {
	        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + text + "\"}");

	        Constructor < ? > titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
	        Object packet = titleConstructor.newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle, fadeInTime, showTime, fadeOutTime);

	        sendPacket(player, packet);
	    } catch (Exception ex) {
	        //Do something
	    }
	}
	
	public static void sendSubTitle(Player player, String text, int fadeInTime, int showTime, int fadeOutTime) {
	    try {
	        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + text + "\"}");

	        Constructor < ? > titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
	        Object packet = titleConstructor.newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatTitle, fadeInTime, showTime, fadeOutTime);

	        sendPacket(player, packet);
	    } catch (Exception ex) {
	        //Do something
	    }
	}
	
	public static void cancelTitles(Player player) {
	    try {
	        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "");

	        Constructor < ? > titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
	        Object packet = titleConstructor.newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("CLEAR").get(null), chatTitle);

	        sendPacket(player, packet);
	    } catch (Exception ex) {
	        //Do something
	    }
	    
	    try {
	        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "");

	        Constructor < ? > titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
	        Object packet = titleConstructor.newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("RESET").get(null), chatTitle);

	        sendPacket(player, packet);
	    } catch (Exception ex) {
	        //Do something
	    }
	}

	private static void sendPacket(Player player, Object packet) {
	    try {
	        Object handle = player.getClass().getMethod("getHandle").invoke(player);
	        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
	        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
	    } catch (Exception ex) {
	        //Do something
	    }
	}

	/**
	 * Get NMS class using reflection
	 * @param name Name of the class
	 * @return Class
	 */
	private static Class < ? > getNMSClass(String name) {
	    try {
	        return Class.forName("net.minecraft.server" + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
	    } catch (ClassNotFoundException ex) {
	        //Do something
	    }
	    return null;
	}
}
