package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if (ArenaManager.getInstance().getArena(p) == null) return;
		e.setCancelled(true);
	}

}
