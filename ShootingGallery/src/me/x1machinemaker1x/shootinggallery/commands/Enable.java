package me.x1machinemaker1x.shootinggallery.commands;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;

import org.bukkit.entity.Player;

public class Enable extends SubCommand {
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(args[0]) == null) {
			p.sendMessage(MessageManager.getInstance().getNotCreated(args[0]));
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		if (a.getSpawn() == null) {
			p.sendMessage(MessageManager.getInstance().getNoSpawn());
			return;
		}
		ArenaManager.getInstance().changeEnabled(a, true);
		p.sendMessage(MessageManager.getInstance().getArenaEnabled(args[0]));
	}

	public String name() {
		return "enable";
	}

	public String info() {
		return "Enables an arena";
	}

	public String[] aliases() {
		return new String[] { "e", "en" };
	}

	public String permission() {
		return "shootinggallery.enable";
	}

	public int argsReq() {
		return 1;
	}

	public String format() {
		return "/sg enable <ID>";
	}
}
