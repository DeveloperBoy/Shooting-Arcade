package me.x1machinemaker1x.shootinggallery.managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

import me.x1machinemaker1x.shootinggallery.Arena;

public class SignManager {
	private static SignManager instance = new SignManager();
	private HashMap<Location, Arena> signs;

	public static SignManager getInstance() {
		return instance;
	}

	public void onEnable() {
		this.signs = new HashMap<Location, Arena>();
		this.signs.clear();
		ConfigurationSection conf = ConfigManager.getInstance().getSigns().getConfigurationSection("Signs");
		if (conf != null) {
			HashMap<Location, String> toRemove = new HashMap<Location, String>();
			for (String n : conf.getKeys(false)) {
				Location l = new Location(Bukkit.getWorld(UUID.fromString(conf.getString(n + ".Location.World"))),
						conf.getDouble(n + ".Location.X"), conf.getDouble(n + ".Location.Y"),
						conf.getDouble(n + ".Location.Z"));
				Arena a = ArenaManager.getInstance().getArena(conf.getString(n + ".ArenaID"));
				if (!(l.getWorld().getBlockAt(l).getState() instanceof Sign)) {
					toRemove.put(l, n);
				} else {
					addSign(l, a);
				}
			}
			for (Location l : toRemove.keySet()) {
				this.signs.remove(l);
				ConfigManager.getInstance().getSigns().set("Signs." + (String) toRemove.get(l), null);
			}
			ConfigManager.getInstance().saveSigns();
		}
	}

	public void reloadSignsToConfig() {
		ConfigManager.getInstance().getSigns().set("Signs", null);
		ConfigManager.getInstance().saveSigns();
		if (!this.signs.isEmpty()) {
			ConfigurationSection conf = ConfigManager.getInstance().getSigns().getConfigurationSection("Signs");
			if (conf == null) {
				conf = ConfigManager.getInstance().getSigns().createSection("Signs");
			}
			Iterator<Location> locs = this.signs.keySet().iterator();
			for (int i = 0; i < this.signs.size(); i++) {
				Location l = (Location) locs.next();
				conf.set(i + ".Location.World", l.getWorld().getUID().toString());
				conf.set(i + ".Location.X", Integer.valueOf(l.getBlockX()));
				conf.set(i + ".Location.Y", Integer.valueOf(l.getBlockY()));
				conf.set(i + ".Location.Z", Integer.valueOf(l.getBlockZ()));
				conf.set(i + ".ArenaID", ((Arena) this.signs.get(l)).getID());
			}
			ConfigManager.getInstance().saveSigns();
		}
	}

	public void addSign(Location l, Arena a) {
		this.signs.put(l, a);
		reloadSignsToConfig();
	}

	public boolean isSign(Location l) {
		if (this.signs.keySet().contains(l)) {
			return true;
		}
		return false;
	}

	public void removeSign(Location loc) {
		this.signs.remove(loc);
		reloadSignsToConfig();
	}
}
