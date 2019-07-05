package me.x1machinemaker1x.shootinggallery;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.x1machinemaker1x.shootinggallery.events.BlockBreak;
import me.x1machinemaker1x.shootinggallery.events.InventoryClick;
import me.x1machinemaker1x.shootinggallery.events.PlayerDrop;
import me.x1machinemaker1x.shootinggallery.events.PlayerInteract;
import me.x1machinemaker1x.shootinggallery.events.PlayerLeave;
import me.x1machinemaker1x.shootinggallery.events.PlayerMove;
import me.x1machinemaker1x.shootinggallery.events.PlayerTeleport;
import me.x1machinemaker1x.shootinggallery.events.ProjectileHit;
import me.x1machinemaker1x.shootinggallery.events.SignChange;
import me.x1machinemaker1x.shootinggallery.utils.ArenaManager;
import me.x1machinemaker1x.shootinggallery.utils.CommandManager;
import me.x1machinemaker1x.shootinggallery.utils.ConfigManager;
import me.x1machinemaker1x.shootinggallery.utils.MessageManager;
import me.x1machinemaker1x.shootinggallery.utils.ScoreManager;
import me.x1machinemaker1x.shootinggallery.utils.SignManager;

public class Main extends JavaPlugin {
	static Plugin p;

	public void onEnable() {
		p = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if (p == null) {
			Bukkit.getLogger().severe("ShootingGallery requires WorldEdit to be installed!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		Bukkit.getLogger().info("WorldEdit detected!");

		CommandManager cm = new CommandManager();

		cm.setup();
		
		ConfigManager.getInstance().onEnable(this);
		ArenaManager.getInstance().onEnable(this);
		SignManager.getInstance().onEnable();
		ScoreManager.getInstance().onEnable();
		MessageManager.getInstance().onEnable();
		MessageManager.getInstance().reloadMessages();

		registerEvents(Bukkit.getPluginManager());

		getCommand("shootinggallery").setExecutor(cm);
	}

	public void onDisable() {
		ArenaManager.getInstance().reloadArenasToConfig();
		SignManager.getInstance().reloadSignsToConfig();
		ScoreManager.getInstance().updateScoresInConfig();
	}

	private void registerEvents(PluginManager pm) {
		pm.registerEvents(new SignChange(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new ProjectileHit(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new PlayerLeave(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new PlayerTeleport(), this);
		pm.registerEvents(new PlayerDrop(), this);
		pm.registerEvents(new InventoryClick(), this);
	}

	public static WorldEditPlugin getWorldEdit() {
		if ((p instanceof WorldEditPlugin)) {
			return (WorldEditPlugin) p;
		}
		return null;
	}
}
