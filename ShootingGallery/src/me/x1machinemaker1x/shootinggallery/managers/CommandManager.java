package me.x1machinemaker1x.shootinggallery.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.shootinggallery.commands.Create;
import me.x1machinemaker1x.shootinggallery.commands.Enable;
import me.x1machinemaker1x.shootinggallery.commands.Forcestop;
import me.x1machinemaker1x.shootinggallery.commands.Highscore;
import me.x1machinemaker1x.shootinggallery.commands.Leave;
import me.x1machinemaker1x.shootinggallery.commands.Reload;
import me.x1machinemaker1x.shootinggallery.commands.Remove;
import me.x1machinemaker1x.shootinggallery.commands.Setspawn;
import me.x1machinemaker1x.shootinggallery.commands.SubCommand;

public class CommandManager implements CommandExecutor {
	private List<SubCommand> commands = new ArrayList<SubCommand>();

	public void setup() {
		this.commands.add(new Create());
		this.commands.add(new Enable());
		this.commands.add(new Remove());
		this.commands.add(new Setspawn());
		this.commands.add(new Forcestop());
		this.commands.add(new Reload());
		this.commands.add(new Leave());
		this.commands.add(new Highscore());
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(MessageManager.getInstance().getNotPlayer());
			return true;
		}
		Player p = (Player) cs;
		if (args.length == 0) {
			for (SubCommand c : this.commands) {
				MessageManager.getInstance().info(p, c.format() + " (" + aliases(c) + ") - " + c.info());
			}
			return true;
		}
		SubCommand command = get(args[0]);
		if (command == null) {
			p.sendMessage(MessageManager.getInstance().getNotCommand(args[0]));
			return true;
		}
		if (!p.hasPermission(command.permission())) {
			cs.sendMessage(MessageManager.getInstance().getNoPermission());
			return true;
		}
		List<String> a = new ArrayList<String>();
		a.addAll(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		if (args.length != command.argsReq()) {
			p.sendMessage(MessageManager.getInstance().getUseFormat(command.format()));
			return true;
		}
		try {
			command.onCommand(p, args);
		} catch (Exception e) {
			p.sendMessage(MessageManager.getInstance().getErrorMessage("error"));
			e.printStackTrace();
		}
		return true;
	}

	private String aliases(SubCommand cmd) {
		String fin = "";
		for (String a : cmd.aliases()) {
			fin += a + " | ";
		}
		return fin.substring(0, fin.lastIndexOf(" | "));
	}

	private SubCommand get(String name) {
		for (SubCommand c : commands) {
			if (c.name().equalsIgnoreCase(name))
				return c;
			for (String alias : c.aliases())
				if (name.equalsIgnoreCase(alias))
					return c;
		}
		return null;
	}
}
