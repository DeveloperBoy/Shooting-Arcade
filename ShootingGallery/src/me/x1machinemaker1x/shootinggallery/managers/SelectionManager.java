package me.x1machinemaker1x.shootinggallery.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.shootinggallery.utils.Cuboid;
import me.x1machinemaker1x.shootinggallery.utils.Selection;

public class SelectionManager {
	private static HashMap<UUID, Selection> selectionmap = new HashMap<UUID, Selection>();
	
	public static void setLocOne(Player p, Location loc) {
		if (selectionmap.containsKey(p.getUniqueId())) {
			Selection sel = selectionmap.get(p.getUniqueId());
			sel.setLocone(loc);
			selectionmap.remove(p.getUniqueId());
			selectionmap.put(p.getUniqueId(), sel);
		} else {
			Selection sel = new Selection();
			sel.setLocone(loc);
			sel.setLoctwo(null);
			selectionmap.put(p.getUniqueId(), sel);
		}
	}
	
	public static void setLocTwo(Player p, Location loc) {
		if (selectionmap.containsKey(p.getUniqueId())) {
			Selection sel = selectionmap.get(p.getUniqueId());
			sel.setLoctwo(loc);
			selectionmap.remove(p.getUniqueId());
			selectionmap.put(p.getUniqueId(), sel);
		} else {
			Selection sel = new Selection();
			sel.setLocone(null);
			sel.setLoctwo(loc);
			selectionmap.put(p.getUniqueId(), sel);
		}
	}
	
	public static Cuboid getCuboidFromSelection(Player p) {
		if (!selectionmap.containsKey(p.getUniqueId())) {
			return null;
		}
		Selection sel = selectionmap.get(p.getUniqueId());
		Cuboid cub = new Cuboid(sel.getLocone(), sel.getLoctwo());
		return cub;
	}
}
