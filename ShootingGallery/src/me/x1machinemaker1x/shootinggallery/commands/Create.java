package me.x1machinemaker1x.shootinggallery.commands;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;
import me.x1machinemaker1x.shootinggallery.managers.SelectionManager;
import me.x1machinemaker1x.shootinggallery.utils.Cuboid;

public class Create extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(args[0]) != null) {
			p.sendMessage(MessageManager.getInstance().getAlreadyCreated());
			return;
		}
		
		Cuboid cub = SelectionManager.getCuboidFromSelection(p);
		if (cub == null) {
			p.sendMessage(MessageManager.getInstance().getNoSelection());
			return;
		}
		
		ArenaManager.getInstance().addArena(args[0], cub, false);
		p.sendMessage(MessageManager.getInstance().getArenaCreated(args[0]));
	}

	public String name() {
		return "create";
	}

	public String info() {
		return "Creates an ShootingArcade arena";
	}

	public String[] aliases() {
		return new String[] { "add", "carena" };
	}

	public String permission() {
		return "shootingarcade.create";
	}

	public int argsReq() {
		return 1;
	}

	public String format() {
		return "/sa create <ID>";
	}
}
