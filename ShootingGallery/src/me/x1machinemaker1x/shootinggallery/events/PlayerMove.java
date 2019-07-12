package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.ConfigManager;

public class PlayerMove implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) == null) {
			return;
		}
		if (ConfigManager.getInstance().getConfig().getBoolean("CanWalkWhenInGame")) {
			return;
		}
		
		Location to = e.getFrom();
		to.setYaw(e.getTo().getYaw());
		to.setPitch(e.getTo().getPitch());
		e.setTo(to);
	}
}
