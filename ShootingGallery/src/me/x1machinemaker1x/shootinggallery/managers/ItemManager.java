package me.x1machinemaker1x.shootinggallery.managers;

import java.util.HashMap;
import me.x1machinemaker1x.shootinggallery.utils.ItemSerializer;
import me.x1machinemaker1x.shootinggallery.utils.XMaterial;

public class ItemManager {
	
	private static ItemManager instance = new ItemManager();
	HashMap<XMaterial, Integer> blocks;
	Integer changemax;

	public static ItemManager getInstance() {
		return instance;
	}

	public void onEnable() {
		if (ConfigManager.getInstance().getConfig().getString("GameType").equalsIgnoreCase("RANDOM")) {
			blocks = new HashMap<XMaterial, Integer>();
			for (String name : ConfigManager.getInstance().getConfig().getStringList("Random.BlockMaterials")) {
				
				blocks.put(ItemSerializer.getXMaterial(name), ItemSerializer.getPoints(name));
				changemax = changemax + ItemSerializer.getPoints(name);
			}
		} else if (ConfigManager.getInstance().getConfig().getString("GameType").equalsIgnoreCase("CUSTOM")) {
			blocks = new HashMap<XMaterial, Integer>();
			for (String name : ConfigManager.getInstance().getConfig().getStringList("Custom.BlockMaterials")) {
				
				blocks.put(ItemSerializer.getXMaterial(name), ItemSerializer.getPoints(name));
				changemax = changemax + ItemSerializer.getPoints(name);
			}
		}
	}
	
	public HashMap<XMaterial, Integer> getBlocks() {
		return blocks;
	}
	
	public int getMaxChange() {
		return changemax;
	}
	
	public int getPointsByBlock(XMaterial mat) {
		if (getBlocks().containsKey(mat)) {
			return getBlocks().get(mat);
		} else {
			return 0;
		}
	}

}
