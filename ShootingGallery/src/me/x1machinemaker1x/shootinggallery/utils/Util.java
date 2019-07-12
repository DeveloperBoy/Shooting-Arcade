package me.x1machinemaker1x.shootinggallery.utils;

import org.bukkit.Bukkit;

public class Util {
	
	public static boolean is113orUp() {
		String nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		return !nmsver.startsWith("v1_7_") && !nmsver.startsWith("v1_8_") && !nmsver.startsWith("v1_9_")
				&& !nmsver.startsWith("v1_10_") && !nmsver.startsWith("v1_11_") && !nmsver.startsWith("v1_12_");
	}
	
	public static double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }

}
