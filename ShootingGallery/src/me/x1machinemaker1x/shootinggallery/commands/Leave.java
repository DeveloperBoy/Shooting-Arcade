package me.x1machinemaker1x.shootinggallery.commands;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;

import org.bukkit.entity.Player;

public class Leave extends SubCommand {
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(p) == null) {
			p.sendMessage(MessageManager.getInstance().getNotInArena());
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(p);
		ArenaManager.getInstance().forceLeaveArena(a);
		p.sendMessage(MessageManager.getInstance().getLeaveArena(a.getID()));
	}

	public String name() {
		return "leave";
	}

	public String info() {
		return "Leave an arena";
	}

	public String[] aliases() {
		return new String[] { "l", "le" };
	}

	public String permission() {
		return "shootinggallery.leave";
	}

	public int argsReq() {
		return 0;
	}

	public String format() {
		return "/sg leave";
	}
}
