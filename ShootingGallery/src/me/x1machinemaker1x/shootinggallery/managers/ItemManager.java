package me.x1machinemaker1x.shootinggallery.managers;

import org.bukkit.Material;

import me.x1machinemaker1x.shootinggallery.api.Target;
import me.x1machinemaker1x.shootinggallery.utils.Util;
import me.x1machinemaker1x.shootinggallery.utils.XMaterial;

public class ItemManager {
	
	/**
	 * Get the material of a target.
	 * @param Target
	 * @return Material
	 */
	public static Material getMaterial(Target tar) {
		String tarname = tar.getConfigName();
		if (!ConfigManager.getInstance().getConfig().getBoolean("Target" + tarname + ".Enabled")) {
			return null;
		}
		
		return XMaterial.matchXMaterial(ConfigManager.getInstance().getConfig().getString("Target" + tarname + ".Material"), (byte) ConfigManager.getInstance().getConfig().getInt("Target" + tarname + ".Data")).parseMaterial();
	}
	
	/**
	 * Get the data of a target.
	 * @deprecated
	 * This method is no longer in use, because most materials aren't using a data byte now.
	 * <p> Use {@link ItemManager#getMaterial(Target)} instead. This will just return the good material.
	 * 
	 * @param Target
	 * @return Byte
	 */
	@Deprecated
	public static byte getData(Target tar) {
		String tarname = tar.getConfigName();
		if (!ConfigManager.getInstance().getConfig().getBoolean("Target" + tarname + ".Enabled")) {
			return 0;
		}
		
		//RETURN 0 because data is only used for version under 1.13.x
		if (Util.is113orUp()) {
			return 0;
		}
		
		return XMaterial.matchXMaterial(ConfigManager.getInstance().getConfig().getString("Target" + tarname + ".Material"), (byte) ConfigManager.getInstance().getConfig().getInt("Target" + tarname + ".Data")).getData();
	}
	
	/**
	 * Get the points of a target.
	 * @param Target
	 * @return Integer
	 */
	public static int getPoins(Target tar) {
		String tarname = tar.getConfigName();
		if (!ConfigManager.getInstance().getConfig().getBoolean("Target" + tarname + ".Enabled")) {
			return 0;
		}
		
		return ConfigManager.getInstance().getConfig().getInt("Target" + tarname + ".Points");
	}
	
}
