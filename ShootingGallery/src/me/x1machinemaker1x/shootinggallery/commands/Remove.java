package me.x1machinemaker1x.shootinggallery.commands;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;

import org.bukkit.entity.Player;

public class Remove extends SubCommand {
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(args[0]) == null) {
			p.sendMessage(MessageManager.getInstance().getNotCreated(args[0]));
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		if (a.inUse()) {
			p.sendMessage(MessageManager.getInstance().getArenaInUse(args[0]));
			return;
		}
		ArenaManager.getInstance().delArena(a);
		p.sendMessage(MessageManager.getInstance().getArenaRemoved(args[0]));
	}

	public String name() {
		return "remove";
	}

	public String info() {
		return "Removes a ShootingArcade arena";
	}

	public String[] aliases() {
		return new String[] { "r", "del" };
	}

	public String permission() {
		return "shootingarcade.remove";
	}

	public int argsReq() {
		return 1;
	}

	public String format() {
		return "/sa remove <ID>";
	}
}
