package me.x1machinemaker1x.shootinggallery.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.shootinggallery.utils.Updat3r;

public class Update extends SubCommand {
	public void onCommand(Player p, String[] args) {
		if (!Updat3r.getInstance().getLatestCached().isNewer()) {
			//TODO Return message that there is no update!
			return;
		}
		//TODO Return message that we gonna install a update.
		Updat3r.getInstance().downloadLatest(Updat3r.getInstance().getLatestCached().getDownloadLink());
		Bukkit.reload();
	}

	public String name() {
		return "update";
	}

	public String info() {
		return "Update the plugin";
	}

	public String[] aliases() {
		return new String[] { "u"};
	}

	public String permission() {
		return "shootinggallery.update";
	}

	public int argsReq() {
		return 0;
	}

	public String format() {
		return "/sg update";
	}
}
