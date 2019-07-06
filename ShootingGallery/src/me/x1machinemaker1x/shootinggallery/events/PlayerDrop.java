package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;

public class PlayerDrop implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) == null) return;
		e.setCancelled(true);
	}
}
