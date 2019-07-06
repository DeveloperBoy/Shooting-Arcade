package me.x1machinemaker1x.shootinggallery.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.ArenaTask;
import me.x1machinemaker1x.shootinggallery.utils.Cuboid;

public class ArenaManager {
	private static ArenaManager instance = new ArenaManager();
	private List<Arena> arenas;
	private Plugin plugin;

	public static ArenaManager getInstance() {
		return instance;
	}

	public void onEnable(Plugin plugin) {
		this.plugin = plugin;
		this.arenas = new ArrayList<Arena>();
		this.arenas.clear();
		ConfigurationSection conf = ConfigManager.getInstance().getArenas().getConfigurationSection("Arenas");
		if (conf != null) {
			Set<String> ids = conf.getKeys(false);
			for (String id : ids) {
				boolean enabled = conf.getBoolean(id + ".Enabled");
				World world = Bukkit.getWorld(UUID.fromString(conf.getString(id + ".World")));
				Location loc1 = new Location(world, conf.getDouble(id + ".Location1.X"),
						conf.getDouble(id + ".Location1.Y"), conf.getDouble(id + ".Location1.Z"));
				Location loc2 = new Location(world, conf.getDouble(id + ".Location2.X"),
						conf.getDouble(id + ".Location2.Y"), conf.getDouble(id + ".Location2.Z"));
				Cuboid selection = new Cuboid(loc1, loc2);
				ConfigurationSection spawnConf = conf.getConfigurationSection(id + ".Spawn");
				if (spawnConf != null) {
					Location spawn = new Location(world, spawnConf.getDouble("X"), spawnConf.getDouble("Y"),
							spawnConf.getDouble("Z"), (float) spawnConf.getDouble("Yaw"),
							(float) spawnConf.getDouble("Pitch"));
					addArenaFromConfig(id, selection, spawn, enabled);
				} else {
					addArena(id, selection, enabled);
				}
			}
		}
	}

	public void reloadArenasToConfig() {
		ConfigManager.getInstance().getArenas().set("Arenas", null);
		ConfigManager.getInstance().saveArenas();
		if (!this.arenas.isEmpty()) {
			ConfigurationSection conf = ConfigManager.getInstance().getArenas().createSection("Arenas");
			for (Arena a : this.arenas) {
				conf.set(a.getID() + ".Enabled", Boolean.valueOf(a.isEnabled()));
				conf.set(a.getID() + ".World", a.getSelection().getWorld().getUID().toString());
				conf.set(a.getID() + ".Location1.X", Integer.valueOf(a.getSelection().getLowerLocation().getBlockX()));
				conf.set(a.getID() + ".Location1.Y", Integer.valueOf(a.getSelection().getLowerLocation().getBlockY()));
				conf.set(a.getID() + ".Location1.Z", Integer.valueOf(a.getSelection().getLowerLocation().getBlockZ()));
				conf.set(a.getID() + ".Location2.X", Integer.valueOf(a.getSelection().getUpperLocation().getBlockX()));
				conf.set(a.getID() + ".Location2.Y", Integer.valueOf(a.getSelection().getUpperLocation().getBlockY()));
				conf.set(a.getID() + ".Location2.Z", Integer.valueOf(a.getSelection().getUpperLocation().getBlockZ()));
				if (a.getSpawn() != null) {
					conf.set(a.getID() + ".Spawn.X", Double.valueOf(a.getSpawn().getBlockX() + 0.5D));
					conf.set(a.getID() + ".Spawn.Y", Integer.valueOf(a.getSpawn().getBlockY()));
					conf.set(a.getID() + ".Spawn.Z", Double.valueOf(a.getSpawn().getBlockZ() + 0.5D));
					conf.set(a.getID() + ".Spawn.Yaw", Float.valueOf(a.getSpawn().getYaw()));
					conf.set(a.getID() + ".Spawn.Pitch", Float.valueOf(a.getSpawn().getPitch()));
				}
			}
			ConfigManager.getInstance().saveArenas();
		}
	}

	public void addArenaFromConfig(String id, Cuboid selection, Location spawn, boolean enabled) {
		Arena a = new Arena(id, selection, spawn, enabled);
		this.arenas.add(a);
	}

	public void addArena(String id, Cuboid selection, boolean enabled) {
		Arena a = new Arena(id, selection, enabled);
		this.arenas.add(a);
		ConfigurationSection conf = ConfigManager.getInstance().getArenas()
				.getConfigurationSection("Arenas." + a.getID());
		if (conf == null) {
			conf = ConfigManager.getInstance().getArenas().createSection("Arenas." + a.getID());
		}
		conf.set("Enabled", Boolean.valueOf(a.isEnabled()));
		conf.set("World", a.getSelection().getWorld().getUID().toString());
		conf.set("Location1.X", Integer.valueOf(a.getSelection().getLowerLocation().getBlockX()));
		conf.set("Location1.Y", Integer.valueOf(a.getSelection().getLowerLocation().getBlockY()));
		conf.set("Location1.Z", Integer.valueOf(a.getSelection().getLowerLocation().getBlockZ()));
		conf.set("Location2.X", Integer.valueOf(a.getSelection().getUpperLocation().getBlockX()));
		conf.set("Location2.Y", Integer.valueOf(a.getSelection().getUpperLocation().getBlockY()));
		conf.set("Location2.Z", Integer.valueOf(a.getSelection().getUpperLocation().getBlockZ()));
		ConfigManager.getInstance().saveArenas();
	}

	public void addSpawn(Arena a, Location spawn) {
		a.setSpawn(spawn);
		ConfigurationSection spawnConf = ConfigManager.getInstance().getArenas()
				.getConfigurationSection("Arenas." + a.getID() + ".Spawn");
		if (spawnConf == null) {
			spawnConf = ConfigManager.getInstance().getArenas().createSection("Arenas." + a.getID() + ".Spawn");
		}
		spawnConf.set("X", Double.valueOf(spawn.getBlockX() + 0.5D));
		spawnConf.set("Y", Integer.valueOf(spawn.getBlockY()));
		spawnConf.set("Z", Double.valueOf(spawn.getBlockZ() + 0.5D));
		spawnConf.set("Yaw", Float.valueOf(spawn.getYaw()));
		spawnConf.set("Pitch", Float.valueOf(spawn.getPitch()));
		ConfigManager.getInstance().saveArenas();
	}

	public void changeEnabled(Arena a, boolean enabled) {
		a.setEnabled(enabled);
		ConfigManager.getInstance().getArenas().set("Arenas." + a.getID() + ".Enabled", Boolean.valueOf(enabled));
		ConfigManager.getInstance().saveArenas();
	}

	public void joinArena(Player p, Arena a) {
		int roundTime = ConfigManager.getInstance().getConfig().getInt("RoundTimeInSeconds");
		ArenaTask aTask = new ArenaTask(roundTime + 10, a);
		aTask.runTaskTimer(this.plugin, 0L, 20L);
		Location pLoc = p.getLocation();
		p.teleport(new Location(a.getSpawn().getWorld(), a.getSpawn().getBlockX() + 0.5D, a.getSpawn().getBlockY(),
				a.getSpawn().getBlockZ() + 0.5D));
		a.startArena(p, p.getInventory().getContents(), pLoc, p.getLevel(), p.getExp(), aTask);
		p.setExp(0.0F);
		p.setLevel(0);
		p.getInventory().clear();
	}

	public void forceLeaveArena(Arena a) {
		Player aPlayer = a.getPlayer();
		ItemStack[] items = a.getPInvContents();
		Location loc = a.getPLoc();
		int XPLevel = a.getXPLevel();
		float XPFloat = a.getXPFloat();
		for (int x = ((Integer) a.getArenaTask().getCoords().get("minX"))
				.intValue(); x <= ((Integer) a.getArenaTask().getCoords().get("maxX")).intValue(); x++) {
			for (int y = ((Integer) a.getArenaTask().getCoords().get("minY"))
					.intValue(); y <= ((Integer) a.getArenaTask().getCoords().get("maxY")).intValue(); y++) {
				for (int z = ((Integer) a.getArenaTask().getCoords().get("minZ"))
						.intValue(); z <= ((Integer) a.getArenaTask().getCoords().get("maxZ")).intValue(); z++) {
					aPlayer.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
		a.forceStop();
		aPlayer.teleport(loc);
		aPlayer.setLevel(XPLevel);
		aPlayer.setExp(XPFloat);
		aPlayer.getInventory().clear();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				aPlayer.getInventory().setItem(i, items[i]);
			}
		}
	}

	public Arena getArena(String id) {
		for (Arena a : this.arenas) {
			if (a.getID().equalsIgnoreCase(id)) {
				return a;
			}
		}
		return null;
	}

	public Arena getArena(Player p) {
		for (Arena a : this.arenas) {
			if ((a.getPlayer() != null) && (a.getPlayer().getUniqueId().equals(p.getUniqueId()))) {
				return a;
			}
		}
		return null;
	}

	public void delArena(Arena a) {
		this.arenas.remove(a);
		reloadArenasToConfig();
	}
}
