package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.utils.ArenaManager;
import me.x1machinemaker1x.shootinggallery.utils.MessageManager;
import me.x1machinemaker1x.shootinggallery.utils.SignManager;

public class PlayerInteract implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		if (!e.getPlayer().hasPermission("shootinggallery.use")) {
			return;
		}
		if (!(e.getClickedBlock().getState() instanceof Sign)) {
			return;
		}
		Sign s = (Sign) e.getClickedBlock().getState();
		if (!SignManager.getInstance().isSign(s.getLocation())) {
			return;
		}
		if (ArenaManager.getInstance().getArena(s.getLine(1).substring(s.getLine(1).indexOf(":") + 4)) == null) {
			e.getPlayer().sendMessage(s.getLine(1).substring(s.getLine(1).indexOf(":") + 4));
			e.getPlayer().sendMessage(
					MessageManager.getInstance().getErrorMessage("Sign does not point to an valid arena!"));
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(s.getLine(1).substring(s.getLine(1).indexOf(":") + 4));
		if (!a.isEnabled()) {
			e.getPlayer().sendMessage(MessageManager.getInstance().getNotEnabled(a.getID()));
			return;
		}
		if (a.inUse()) {
			e.getPlayer().sendMessage(MessageManager.getInstance().getArenaInUse(a.getID()));
			return;
		}
		ArenaManager.getInstance().joinArena(e.getPlayer(), a);
		e.getPlayer().sendMessage(MessageManager.getInstance().getJoinArena(a.getID()));
	}
}
