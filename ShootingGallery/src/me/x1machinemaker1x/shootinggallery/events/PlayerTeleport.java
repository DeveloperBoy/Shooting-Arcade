package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;

public class PlayerTeleport implements Listener {
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) == null) {
			return;
		}
		if (e.getPlayer().hasPermission("shootingarcade.teleport")) {
			return;
		}
		e.setCancelled(true);
		e.getPlayer().sendMessage(MessageManager.getInstance().getNoTeleport());
	}
}
