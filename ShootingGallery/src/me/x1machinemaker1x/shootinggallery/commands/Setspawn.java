package me.x1machinemaker1x.shootinggallery.commands;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.ConfigManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;

public class Setspawn extends SubCommand {
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(args[0]) == null) {
			p.sendMessage(MessageManager.getInstance().getNotCreated(args[0]));
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		if ((!a.getSelection().containsLocation(p.getLocation()))
				&& (ConfigManager.getInstance().getConfig().getBoolean("SpawnPointMustBeInArena"))) {
			p.sendMessage(MessageManager.getInstance().getNotInSelection());
			return;
		}
		ArenaManager.getInstance().addSpawn(a, p.getLocation());
		p.sendMessage(MessageManager.getInstance().getSpawnSet(args[0]));
	}

	public String name() {
		return "setspawn";
	}

	public String info() {
		return "Sets the spawn point for an arena";
	}

	public String[] aliases() {
		return new String[] { "ss", "spawn" };
	}

	public String permission() {
		return "shootingarcade.setspawn";
	}

	public int argsReq() {
		return 1;
	}

	public String format() {
		return "/sa setspawn <ID>";
	}
}
