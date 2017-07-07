package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.x1machinemaker1x.shootinggallery.utils.ArenaManager;
import me.x1machinemaker1x.shootinggallery.utils.SignManager;

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
		if (e.getPlayer().hasPermission("shootinggallery.createsign")) {
			SignManager.getInstance().removeSign(e.getBlock().getLocation());
			return;
		}
		e.setCancelled(true);
	}
}
