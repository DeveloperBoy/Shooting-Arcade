package me.x1machinemaker1x.shootinggallery.commands;

 import java.util.UUID;

 import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

 import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.managers.ArenaManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;
import me.x1machinemaker1x.shootinggallery.managers.ScoreManager;

 public class Highscore extends SubCommand {

 	public void onCommand(Player p, String[] args) {
		Arena a = ArenaManager.getInstance().getArena(args[0]);
		if (a == null) {
			p.sendMessage(MessageManager.getInstance().getNotCreated(args[0]));
			return;
		}
		int limit;
		if (ScoreManager.getInstance().topScores().size() >= 10) {
			limit = 10;
		}
		else {
			limit = ScoreManager.getInstance().topScores().size();
		}
		for (int i = 0; i < limit; i ++) {
			String[] score = ScoreManager.getInstance().topScores().get(i).split(":");
			p.sendMessage(MessageManager.getInstance().getHighScores(Bukkit.getPlayer(UUID.fromString(score[0])).getName(), score[1]));
		}
	}

 	public String name() {
		return "highscore";
	}

 	public String info() {
		return "Displays the top 10 scores for an arena";
	}

 	public String[] aliases() {
		return new String[] { "h", "hs" };
	}

 	public String permission() {
		return "shootinggallery.highscore";
	}

 	public int argsReq() {
		return 1;
	}

 	public String format() {
		return "/sg highscore <ID>";
	}

 }