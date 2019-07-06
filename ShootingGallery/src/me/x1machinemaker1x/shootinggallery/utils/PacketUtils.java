package me.x1machinemaker1x.shootinggallery.utils;

import org.bukkit.entity.Player;

public class PacketUtils {
	public static void sendActionBar(Player p, String message) {
		try {
			ActionBarMessager.sendHotBarMessage(p, message);
		} catch (Exception e) {
			//Do something
		}
	}
}
