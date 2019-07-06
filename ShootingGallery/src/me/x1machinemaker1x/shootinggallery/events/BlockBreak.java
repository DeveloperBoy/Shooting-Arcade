package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.SignManager;

public class BlockBreak implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (ArenaManager.getInstance().getArena(e.getPlayer()) != null) {
			e.setCancelled(true);
		}
		if (!SignManager.getInstance().isSign(e.getBlock().getLocation())) {
			return;
		}
		if (!(e.getBlock().getState() instanceof Sign)) {
			SignManager.getInstance().removeSign(e.getBlock().getLocation());
			return;
		}
		if (e.getPlayer().hasPermission("shootingarcade.createsign")) {
			SignManager.getInstance().removeSign(e.getBlock().getLocation());
			return;
		}
		e.setCancelled(true);
	}
}
