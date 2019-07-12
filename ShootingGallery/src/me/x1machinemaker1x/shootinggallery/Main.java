package me.x1machinemaker1x.shootinggallery;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.x1machinemaker1x.shootinggallery.events.BlockBreak;
import me.x1machinemaker1x.shootinggallery.events.InventoryClick;
import me.x1machinemaker1x.shootinggallery.events.PlayerDrop;
import me.x1machinemaker1x.shootinggallery.events.PlayerInteract;
import me.x1machinemaker1x.shootinggallery.events.PlayerLeave;
import me.x1machinemaker1x.shootinggallery.events.PlayerMove;
import me.x1machinemaker1x.shootinggallery.events.PlayerTeleport;
import me.x1machinemaker1x.shootinggallery.events.ProjectileHit;
import me.x1machinemaker1x.shootinggallery.events.SignChange;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.CommandManager;
import me.x1machinemaker1x.shootinggallery.managers.ConfigManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;
import me.x1machinemaker1x.shootinggallery.managers.ScoreManager;
import me.x1machinemaker1x.shootinggallery.managers.SignManager;
import me.x1machinemaker1x.shootinggallery.utils.Metrics;
import me.x1machinemaker1x.shootinggallery.utils.Updat3r;

public class Main extends JavaPlugin {
	
	private static Plugin pl;

	public void onEnable() {
		
		pl = this;
		
		//Load the commandmanager (for the commands)
		CommandManager cm = new CommandManager();
		cm.setup();
		
		//Load the configmanager (for the files)
		ConfigManager.getInstance().onEnable(this);
		
		//Load the arenamanager (for the arenas)
		ArenaManager.getInstance().onEnable(this);
		
		//Load the signmanager (for the join signs)
		SignManager.getInstance().onEnable();
		
		//Load the scoremanager (for the scores and highscores)
		ScoreManager.getInstance().onEnable();
		
		//Load the messagemanager (for the messages)
		MessageManager.getInstance().onEnable();
		MessageManager.getInstance().reloadMessages();

		//Register all the events
		registerEvents(Bukkit.getPluginManager());

		//Set the command
		getCommand("shootinggallery").setExecutor(cm);
		
		//Load metrics
		Metrics metrics = new Metrics(this);
		
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		metrics.addCustomChart(new Metrics.SimplePie("nms_version", () -> version));
		
		//Load updat3r
		Updat3r.getInstance().startTask();
		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onJoin(PlayerJoinEvent e) {
				Updat3r.getInstance().sendUpdateMessageLater(e.getPlayer());
			}
		}, this);
	}

	public void onDisable() {
		//Save all needed changes to the files
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
	
	public static Plugin getPlugin() {
		return pl;
	}
}
