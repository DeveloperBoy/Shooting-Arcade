package me.x1machinemaker1x.shootinggallery.commands;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.shootinggallery.managers.ConfigManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;

public class Reload extends SubCommand {
	public void onCommand(Player p, String[] args) {
		switch (args[0]) {
		case "arenas":
			ConfigManager.getInstance().reloadArenas();
			break;
		case "config":
			ConfigManager.getInstance().reloadConfig();
			break;
		case "scores":
			ConfigManager.getInstance().reloadScores();
			break;
		case "messages":
			MessageManager.getInstance().reloadMessages();
			break;
		case "signs":
			ConfigManager.getInstance().reloadSigns();
			break;
		case "all":
			ConfigManager.getInstance().reloadConfig();
			ConfigManager.getInstance().reloadArenas();
			ConfigManager.getInstance().reloadSigns();
			MessageManager.getInstance().reloadMessages();
			ConfigManager.getInstance().reloadScores();
			p.sendMessage(MessageManager.getInstance().getConfigReloaded("All files"));
			return;
		default:
			p.sendMessage(MessageManager.getInstance().getNotConfig(args[0] + ".yml"));
			return;
		}
		p.sendMessage(MessageManager.getInstance().getConfigReloaded(args[0] + ".yml"));
	}

	public String name() {
		return "reload";
	}

	public String info() {
		return "Reload configuration files";
	}

	public String[] aliases() {
		return new String[] { "r", "rel" };
	}

	public String permission() {
		return "shootingarcade.reload";
	}

	public int argsReq() {
		return 1;
	}

	public String format() {
		return "/sa reload <config|arenas|signs|messages|scores|all>";
	}
}
