package me.x1machinemaker1x.shootinggallery.utils;

public class ItemSerializer {
	
	public static XMaterial getXMaterial(String string) {
		String[] split = string.split(":");
		
		return XMaterial.matchXMaterial(split[0]);
	}
	
	public static Integer getPoints(String string) {
		String[] split = string.split(":");
		
		return Integer.valueOf(split[1]);
	}
	
	public static String serialize(XMaterial mat, Integer points) {
		 return XMaterial.parseMaterial(mat.parseMaterial().name(), mat.getData()) + ":" + points;
	}
	
	public static double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }

}
