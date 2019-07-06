package me.x1machinemaker1x.shootinggallery.events;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) == null) {
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(e.getPlayer());
		ArenaManager.getInstance().forceLeaveArena(a);
	}
}
