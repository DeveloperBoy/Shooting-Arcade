package me.x1machinemaker1x.shootinggallery.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
	private static ConfigManager instance = new ConfigManager();
	private Plugin plugin;
	private File cfile;
	private File afile;
	private File sfile;
	private File mfile;
	private File scfile;
	private FileConfiguration config;
	private FileConfiguration arenas;
	private FileConfiguration signs;
	private FileConfiguration messages;
	private FileConfiguration scores;
	private List<String> configFiles;

	public static ConfigManager getInstance() {
		return instance;
	}

	public void onEnable(Plugin pl) {
		this.configFiles = new ArrayList<String>();
		this.configFiles.add("config");
		this.configFiles.add("arenas");
		this.configFiles.add("signs");
		this.configFiles.add("messages");
		this.configFiles.add("scores");
		this.plugin = pl;
		this.cfile = new File(this.plugin.getDataFolder(), "config.yml");
		this.config = this.plugin.getConfig();
		this.config.options().copyDefaults(true);
		saveConfig();
		if (!this.plugin.getDataFolder().exists()) {
			try {
				this.plugin.getDataFolder().createNewFile();
			} catch (Exception e) {
				Bukkit.getServer().getLogger().severe("Could not create the data folder!");
			}
		}
		this.afile = new File(this.plugin.getDataFolder(), "arenas.yml");
		this.sfile = new File(this.plugin.getDataFolder(), "signs.yml");
		this.mfile = new File(this.plugin.getDataFolder(), "messages.yml");
		this.scfile = new File(this.plugin.getDataFolder(), "scores.yml");

		this.arenas = setupConfig(this.afile, "arenas.yml", false);
		this.signs = setupConfig(this.sfile, "signs.yml", false);
		this.messages = setupConfig(this.mfile, "messages.yml", false);
		this.scores = setupConfig(this.scfile, "scores.yml", false);

		this.arenas.set("do-not-delete-this", Boolean.valueOf(true));
		saveArenas();
		this.signs.set("do-not-delete-this", Boolean.valueOf(true));
		saveSigns();
		this.scores.set("do-not-delete-this", Boolean.valueOf(true));
		saveScores();
	}

	public void onDisable() {
		saveConfig();
		saveArenas();
		saveSigns();
	}

	private FileConfiguration setupConfig(File f, String fileName, boolean copyFromJar) {
		if (!f.exists()) {
			if (copyFromJar == false) {
				try {
					f.createNewFile();
				} catch (Exception e) {
					Bukkit.getServer().getLogger().severe("Could not create " + fileName + "!");
				}
			} else {
				f.getParentFile().mkdirs();
				copy(plugin.getResource(fileName), f);
			}

		}
		return YamlConfiguration.loadConfiguration(f);
	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration getConfig() {
		return this.config;
	}

	public void saveConfig() {
		try {
			this.config.save(this.cfile);
		} catch (Exception e) {
			Bukkit.getServer().getLogger().severe("Could not save config.yml");
		}
	}

	public void reloadConfig() {
		this.config = YamlConfiguration.loadConfiguration(this.cfile);
	}

	public FileConfiguration getArenas() {
		return this.arenas;
	}

	public void saveArenas() {
		try {
			this.arenas.save(this.afile);
		} catch (Exception e) {
			Bukkit.getServer().getLogger().severe("Could not save arenas.yml");
		}
	}

	public void reloadArenas() {
		this.arenas = YamlConfiguration.loadConfiguration(this.afile);
		ArenaManager.getInstance().onEnable(this.plugin);
	}

	public FileConfiguration getSigns() {
		return this.signs;
	}

	public void saveSigns() {
		try {
			this.signs.save(this.sfile);
		} catch (Exception e) {
			Bukkit.getServer().getLogger().severe("Could not save signs.yml");
		}
	}

	public void reloadSigns() {
		this.signs = YamlConfiguration.loadConfiguration(this.sfile);
		SignManager.getInstance().onEnable();
	}

	public FileConfiguration getMessages() {
		return this.messages;
	}

	public void saveMessages() {
		try {
			this.messages.save(this.mfile);
		} catch (Exception e) {
			Bukkit.getServer().getLogger().severe("Could not save messages.yml");
		}
	}

	public void reloadMessages() {
		this.messages = YamlConfiguration.loadConfiguration(this.mfile);
	}

	public FileConfiguration getScores() {
		return this.scores;
	}

	public void saveScores() {
		try {
			this.scores.save(this.scfile);
		} catch (Exception e) {
			Bukkit.getServer().getLogger().severe("Could not save scores.yml");
		}
	}

	public void reloadScores() {
		this.scores = YamlConfiguration.loadConfiguration(this.scfile);
	}

	public boolean isConfig(String file) {
		return this.configFiles.contains(file);
	}
}
