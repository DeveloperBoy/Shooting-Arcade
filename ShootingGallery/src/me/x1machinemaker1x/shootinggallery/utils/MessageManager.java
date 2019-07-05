package me.x1machinemaker1x.shootinggallery.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class MessageManager {
	private static MessageManager instance = new MessageManager();
	private String notPlayer;
	private String noPermission;
	private String noTeleport;
	private String notInArena;
	private String cannotLeave;
	private String useFormat;
	private String notCommand;
	private String errorMessage;
	private String alreadyCreated;
	private String notCreated;
	private String noSelection;
	private String notCuboid;
	private String noSpawn;
	private String notInSelection;
	private String arenaInUse;
	private String arenaNotInUse;
	private String notEnabled;
	private String notConfig;
	private String configReloaded;
	private String arenaCreated;
	private String arenaEnabled;
	private String spawnSet;
	private String arenaRemoved;
	private String forceStop;
	private String signCreated;
	private String joinArena;
	private String leaveArena;
	private String newHighscore;
	private String scoreMessage;
	private String addMoney;
	private String moneyError;
	private List<String> gameMessages;
	private String highScores;
	private String selectedpointone;
	private String selectedpointtwo;

	public static MessageManager getInstance() {
		return instance;
	}

	public void onEnable() {
		ConfigurationSection conf = ConfigManager.getInstance().getMessages().getConfigurationSection("Messages");
		if (conf == null) {
			conf = ConfigManager.getInstance().getMessages().createSection("Messages");
		}
		if (!conf.isSet("NotPlayer")) {
			conf.set("NotPlayer", "&cYou must be a player to use this command!");
		}
		if (!conf.isSet("NoPermission")) {
			conf.set("NoPermission", "&cYou do not have permission to use that command!");
		}
		if (!conf.isSet("NoTeleport")) {
			conf.set("NoTeleport", "&cYou are not allowed to teleport out of an arena!");
		}
		if (!conf.isSet("NotInArena")) {
			conf.set("NotInArena", "&cYou are not in an arena!");
		}
		if (!conf.isSet("CannotLeave")) {
			conf.set("CannotLeave", "&cYou are not allowed to leave arena %id%!");
		}
		if (!conf.isSet("UseFormat")) {
			conf.set("UseFormat", "&cUse format: %format%");
		}
		if (!conf.isSet("NotCommand")) {
			conf.set("NotCommand", "&c%command% is not a valid command!");
		}
		if (!conf.isSet("ErrorMessage")) {
			conf.set("ErrorMessage", "&cAn error has occurred: %cause%");
		}
		if (!conf.isSet("AlreadyCreated")) {
			conf.set("AlreadyCreated", "&cThere is already a ShootingGallery arena with that ID!");
		}
		if (!conf.isSet("NotCreated")) {
			conf.set("NotCreated", "&cThere is no arena with id %id%!");
		}
		if (!conf.isSet("noSelection")) {
			conf.set("NoSelection", "&cYou must have a selected area!");
		}
		if (!conf.isSet("SelectedPointOne")) {
			conf.set("SelectedPointOne", "&aSelected point one!");
		}
		if (!conf.isSet("SelectedPointTwo")) {
			conf.set("SelectedPointTwo", "&aSelected point two!");
		}
		if (!conf.isSet("NoSpawn")) {
			conf.set("NoSpawn", "&cNo spawn point has been set for that arena!");
		}
		if (!conf.isSet("NotInSelection")) {
			conf.set("NotInSelection", "&cThis spawn point is not in the arena selection!");
		}
		if (!conf.isSet("ArenaInUse")) {
			conf.set("ArenaInUse", "&cArena %id% is in use!");
		}
		if (!conf.isSet("ArenaNotInUse")) {
			conf.set("ArenaNotInUse", "&cArena %id% is not in use!");
		}
		if (!conf.isSet("NotEnabled")) {
			conf.set("NotEnabled", "&cArena %id% has not been enabled!");
		}
		if (!conf.isSet("NotConfig")) {
			conf.set("NotConfig", "&c%file% is not a valid configuration file!");
		}
		if (!conf.isSet("ConfigReloaded")) {
			conf.set("ConfigReloaded", "&a%file% has been reloaded!");
		}
		if (!conf.isSet("ArenaCreated")) {
			conf.set("ArenaCreated", "&aA ShootingGallery arena with an ID of %id% has been created!");
		}
		if (!conf.isSet("ArenaEnabled")) {
			conf.set("ArenaEnabled", "&aArena %id% has been enabled!");
		}
		if (!conf.isSet("SpawnSet")) {
			conf.set("SpawnSet", "&aThe spawn point for arena %id% is set!");
		}
		if (!conf.isSet("ArenaRemoved")) {
			conf.set("ArenaRemoved", "&aArena %id% has been removed!");
		}
		if (!conf.isSet("ForceStop")) {
			conf.set("ForceStop", "&aArena %id% has been forced to stop!");
		}
		if (!conf.isSet("SignCreated")) {
			conf.set("SignCreated", "&aSign for arena %id% successfully created!");
		}
		if (!conf.isSet("JoinArena")) {
			conf.set("JoinArena", "&aYou have joined arena %id%!");
		}
		if (!conf.isSet("LeaveArena")) {
			conf.set("LeaveArena", "&aYou have left arena %id%! Your score will not be counted.");
		}
		if (!conf.isSet("NewHighscore")) {
			conf.set("NewHighscore", "&aYou have just set a new high score of %score%!");
		}
		if (!conf.isSet("ScoreMessage")) {
			conf.set("ScoreMessage", "&aYou got a score of %score%. Nice job!");
		}
		if (!conf.isSet("AddMoney")) {
			conf.set("AddMoney", "&eYou were given %amount%!");
		}
		if (!conf.isSet("MoneyError")) {
			conf.set("MoneyError", "&cAn error occurred: %cause%");
		}
		if (!conf.isSet("HighScores")) {
			conf.set("HighScores", "%player%: %score%");
		}
		if (!conf.isSet("GameMessages")) {
			List<String> gms = new ArrayList<String>();
			gms.add("&aYou have joined a Shooting Gallery!");
			gms.add("&aUse the bow and arrow to shoot at the wool blocks!");
			gms.add("&2Green &awool is worth 1 point and &4Red &ais worth 5!");
			conf.set("GameMessages", gms);
		}
		ConfigManager.getInstance().saveMessages();
	}

	public void reloadMessages() {
		ConfigManager.getInstance().reloadMessages();
		ConfigurationSection conf = ConfigManager.getInstance().getMessages().getConfigurationSection("Messages");
		this.notPlayer = conf.getString("NotPlayer");
		this.noPermission = conf.getString("NoPermission");
		this.noTeleport = conf.getString("NoTeleport");
		this.notInArena = conf.getString("NotInArena");
		this.cannotLeave = conf.getString("CannotLeave");
		this.useFormat = conf.getString("UseFormat");
		this.notCommand = conf.getString("NotCommand");
		this.errorMessage = conf.getString("ErrorMessage");
		this.alreadyCreated = conf.getString("AlreadyCreated");
		this.notCreated = conf.getString("NotCreated");
		this.noSelection = conf.getString("NoSelection");
		this.selectedpointone = conf.getString("SelectedPointOne");
		this.selectedpointtwo = conf.getString("SelectedPointTwo");
		this.noSpawn = conf.getString("NoSpawn");
		this.notInSelection = conf.getString("NotInSelection");
		this.arenaInUse = conf.getString("ArenaInUse");
		this.arenaNotInUse = conf.getString("ArenaNotInUse");
		this.notEnabled = conf.getString("NotEnabled");
		this.notConfig = conf.getString("NotConfig");
		this.configReloaded = conf.getString("ConfigReloaded");
		this.arenaCreated = conf.getString("ArenaCreated");
		this.arenaEnabled = conf.getString("ArenaEnabled");
		this.spawnSet = conf.getString("SpawnSet");
		this.arenaRemoved = conf.getString("ArenaRemoved");
		this.forceStop = conf.getString("ForceStop");
		this.signCreated = conf.getString("SignCreated");
		this.joinArena = conf.getString("JoinArena");
		this.leaveArena = conf.getString("LeaveArena");
		this.newHighscore = conf.getString("NewHighscore");
		this.scoreMessage = conf.getString("ScoreMessage");
		this.addMoney = conf.getString("AddMoney");
		this.moneyError = conf.getString("MoneyError");
		this.highScores = conf.getString("HighScores");
		this.gameMessages = conf.getStringList("GameMessages");
	}

	public String getNotPlayer() {
		return setupMessage(this.notPlayer);
	}

	public String getNoPermission() {
		return setupMessage(this.noPermission);
	}

	public String getNoTeleport() {
		return setupMessage(this.noTeleport);
	}

	public String getNotInArena() {
		return setupMessage(this.notInArena);
	}

	public String getCannotLeave(String id) {
		return setupMessage(this.cannotLeave.replace("%id%", id));
	}

	public String getUseFormat(String format) {
		return setupMessage(this.useFormat.replace("%format%", format));
	}

	public String getNotCommand(String cmdLabel) {
		return setupMessage(this.notCommand.replace("%command%", cmdLabel));
	}

	public String getErrorMessage(String cause) {
		return setupMessage(this.errorMessage.replace("%cause%", cause));
	}

	public String getAlreadyCreated() {
		return setupMessage(this.alreadyCreated);
	}

	public String getNotCreated(String notID) {
		return setupMessage(this.notCreated.replace("%id%", notID));
	}

	public String getNoSelection() {
		return setupMessage(this.noSelection);
	}

	public String getNotCuboid() {
		return setupMessage(this.notCuboid);
	}

	public String getNoSpawn() {
		return setupMessage(this.noSpawn);
	}

	public String getNotInSelection() {
		return setupMessage(this.notInSelection);
	}

	public String getArenaInUse(String id) {
		return setupMessage(this.arenaInUse.replace("%id%", id));
	}

	public String getArenaNotInUse(String id) {
		return setupMessage(this.arenaNotInUse.replace("%id%", id));
	}

	public String getNotEnabled(String id) {
		return setupMessage(this.notEnabled.replace("%id%", id));
	}

	public String getNotConfig(String file) {
		return setupMessage(this.notConfig.replace("%file%", file));
	}

	public String getConfigReloaded(String file) {
		return setupMessage(this.configReloaded.replace("%file%", file));
	}

	public String getArenaCreated(String id) {
		return setupMessage(this.arenaCreated.replace("%id%", id));
	}

	public String getArenaEnabled(String id) {
		return setupMessage(this.arenaEnabled.replace("%id%", id));
	}

	public String getSpawnSet(String id) {
		return setupMessage(this.spawnSet.replace("%id%", id));
	}

	public String getArenaRemoved(String id) {
		return setupMessage(this.arenaRemoved.replace("%id%", id));
	}

	public String getForceStop(String id) {
		return setupMessage(this.forceStop.replace("%id%", id));
	}

	public String getSignCreated(String id) {
		return setupMessage(this.signCreated.replace("%id%", id));
	}

	public String getJoinArena(String id) {
		return setupMessage(this.joinArena.replace("%id%", id));
	}

	public String getLeaveArena(String id) {
		return setupMessage(this.leaveArena.replace("%id%", id));
	}

	public String getNewHighscore(int score) {
		return setupMessage(this.newHighscore.replace("%score%", score + ""));
	}

	public String getScoreMessage(int score) {
		return setupMessage(this.scoreMessage.replace("%score%", score + ""));
	}

	public String getAddMoney(String amount) {
		return setupMessage(this.addMoney.replace("%amount%", amount));
	}

	public String getMoneyError(String cause) {
		return setupMessage(this.moneyError.replace("%cause%", cause));
	}
	
	public String getHighScores(String player, String score) {
		return setupMessage(this.highScores.replace("%player%", player).replace("%score%", score));
	}

	public String getGameMessages(int n) {
		return setupMessage((String) this.gameMessages.get(n));
	}

	public void info(CommandSender cs, String msg) {
		msg(cs, ChatColor.BLUE, msg);
	}

	public void msg(CommandSender cs, ChatColor color, String msg) {
		cs.sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "SG" + ChatColor.GRAY + "]" + color + msg);
	}

	public String setupMessage(String message) {
		return ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "SG" + ChatColor.GRAY + "]"
				+ ChatColor.translateAlternateColorCodes('&', message);
	}

	public String getSelectedpointone() {
		return setupMessage(this.selectedpointone);
	}

	public String getSelectedpointtwo() {
		return setupMessage(this.selectedpointtwo);
	}
}
