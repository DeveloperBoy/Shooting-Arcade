package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;
import me.x1machinemaker1x.shootinggallery.managers.SignManager;

public class SignChange implements Listener {
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (!e.getPlayer().hasPermission("shootinggallery.createsign")) {
			return;
		}
		if (!e.getLine(0).contains("[ShootingG]")) {
			return;
		}
		String id = e.getLine(1);
		if (ArenaManager.getInstance().getArena(id) == null) {
			e.getPlayer().sendMessage(MessageManager.getInstance().getNotCreated(id));
			e.setCancelled(true);
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(id);
		e.setLine(0, ChatColor.DARK_BLUE + "[ShootingG]");
		e.setLine(1, ChatColor.DARK_BLUE + "Arena: " + ChatColor.DARK_RED + a.getID());
		SignManager.getInstance().addSign(e.getBlock().getLocation(), a);
		e.getPlayer().sendMessage(MessageManager.getInstance().getSignCreated(id));
	}
}
