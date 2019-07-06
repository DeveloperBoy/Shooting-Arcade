package me.x1machinemaker1x.shootinggallery.commands;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;

import org.bukkit.entity.Player;

public class Forcestop extends SubCommand {
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(args[0]) == null) {
			p.sendMessage(MessageManager.getInstance().getNotCreated(args[0]));
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		if (!a.inUse()) {
			p.sendMessage(MessageManager.getInstance().getArenaNotInUse(a.getID()));
			return;
		}
		ArenaManager.getInstance().forceLeaveArena(a);
		p.sendMessage(MessageManager.getInstance().getForceStop(a.getID()));
	}

	public String name() {
		return "forcestop";
	}

	public String info() {
		return "Forcestops an arena";
	}

	public String[] aliases() {
		return new String[] { "fs", "stop" };
	}

	public String permission() {
		return "shootingarcade.forcestop";
	}

	public int argsReq() {
		return 1;
	}

	public String format() {
		return "/sa forcestop <ID>";
	}
}
