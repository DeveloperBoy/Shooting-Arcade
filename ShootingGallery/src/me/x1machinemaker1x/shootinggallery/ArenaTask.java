package me.x1machinemaker1x.shootinggallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.scheduler.BukkitRunnable;

import me.x1machinemaker1x.shootinggallery.api.SGBlock;
import me.x1machinemaker1x.shootinggallery.managers.ConfigManager;
import me.x1machinemaker1x.shootinggallery.managers.MessageManager;
import me.x1machinemaker1x.shootinggallery.managers.ScoreManager;
import me.x1machinemaker1x.shootinggallery.utils.Cuboid;
import me.x1machinemaker1x.shootinggallery.utils.TitleActionBarUtil;
import me.x1machinemaker1x.shootinggallery.utils.Util;
import me.x1machinemaker1x.shootinggallery.utils.XMaterial;
import me.x1machinemaker1x.shootinggallery.utils.XSound;

@SuppressWarnings("deprecation")
public class ArenaTask extends BukkitRunnable {
	private int counter;
	private Arena arena;
	private HashMap<String, Integer> coords;
	private Random rand;
	private List<SGBlock> blocks;

	public ArenaTask(int counter, Arena arena) {
		blocks = new ArrayList<SGBlock>();
		rand = new Random();
		coords = new HashMap<String, Integer>();
		this.counter = counter;
		this.arena = arena;
		Cuboid sel = arena.getSelection();
		if (sel.getLowerLocation().getBlockX() < sel.getUpperLocation().getBlockX()) {
			coords.put("minX", sel.getLowerLocation().getBlockX() + 1);
			coords.put("maxX", sel.getUpperLocation().getBlockX() - 1);
		} else {
			coords.put("minX", sel.getUpperLocation().getBlockX() + 1);
			coords.put("maxX", sel.getLowerLocation().getBlockX() - 1);
		}
		if (sel.getLowerLocation().getBlockY() < sel.getUpperLocation().getBlockY()) {
			coords.put("minY", sel.getLowerLocation().getBlockY() + 1);
			coords.put("maxY", sel.getUpperLocation().getBlockY() - 1);
		} else {
			coords.put("minY", sel.getUpperLocation().getBlockY() + 1);
			coords.put("maxY", sel.getLowerLocation().getBlockY() - 1);
		}
		if (sel.getLowerLocation().getBlockZ() < sel.getUpperLocation().getBlockZ()) {
			coords.put("minZ", sel.getLowerLocation().getBlockZ() + 1);
			coords.put("maxZ", sel.getUpperLocation().getBlockZ() - 1);
		} else {
			coords.put("minZ", sel.getUpperLocation().getBlockZ() + 1);
			coords.put("maxZ", sel.getLowerLocation().getBlockZ() - 1);
		}
	}

	public void run() {
		if (counter >= ConfigManager.getInstance().getConfig().getInt("RoundTimeInSeconds")) {
			int timeBeforeStart = counter - ConfigManager.getInstance().getConfig().getInt("RoundTimeInSeconds");
			if (timeBeforeStart > 1) {
				XSound.ENTITY_PLAYER_LEVELUP.playSound(arena.getPlayer(), 3.0F, 2.5F);
			}
			if (timeBeforeStart == 10) {
				TitleActionBarUtil.sendTitle(arena.getPlayer(), "§6Shooting Gallery", 10, 80, 10);
				TitleActionBarUtil.sendSubTitle(arena.getPlayer(), "§8Round starts in 10 seconds!", 10, 80, 10);
				arena.getPlayer().sendMessage(MessageManager.getInstance().getGameMessages(0));
			} else if (timeBeforeStart == 9) {
				ItemStack bow = new ItemStack(Material.BOW);
				ItemMeta bowMeta = bow.getItemMeta();
				bowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
						ConfigManager.getInstance().getConfig().getString("BowName")));
				bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
				List<String> lore = new ArrayList<String>();
				/**
				 * @todo Get lore from config
				 */
				lore.add(ChatColor.YELLOW + "Use this bow to shoot the wool!");
				bowMeta.setLore(lore);
				bowMeta.setUnbreakable(true);
				bow.setItemMeta(bowMeta);
				arena.getPlayer().getInventory().addItem(new ItemStack[] { bow });
				org.bukkit.inventory.ItemStack arrow = new ItemStack(Material.ARROW, 1);
				ItemMeta arrowMeta = arrow.getItemMeta();
				arrowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
						ConfigManager.getInstance().getConfig().getString("ArrowName")));
				arrow.setItemMeta(arrowMeta);
				arena.getPlayer().getInventory().addItem(new org.bukkit.inventory.ItemStack[] { arrow });
			} else if (timeBeforeStart == 7) {
				arena.getPlayer().sendMessage(MessageManager.getInstance().getGameMessages(1));
			} else if (timeBeforeStart == 5) {
				TitleActionBarUtil.sendTitle(arena.getPlayer(), "§c5", 2, 16, 2);
				TitleActionBarUtil.sendSubTitle(arena.getPlayer(), "§8Get ready...", 10, 1, 10);
			} else if (timeBeforeStart == 4) {
				TitleActionBarUtil.sendTitle(arena.getPlayer(), "§c4", 0, 10, 0);
				arena.getPlayer().sendMessage(MessageManager.getInstance().getGameMessages(2));
			} else if (timeBeforeStart == 3) {
				TitleActionBarUtil.sendTitle(arena.getPlayer(), "§c3", 0, 10, 0);
			} else if (timeBeforeStart == 2) {
				TitleActionBarUtil.sendTitle(arena.getPlayer(), "§c2", 0, 10, 0);
			} else if (timeBeforeStart == 1) {
				TitleActionBarUtil.sendTitle(arena.getPlayer(), "§c1", 0, 10, 0);
			} else if (timeBeforeStart == 0) {
				TitleActionBarUtil.sendTitle(arena.getPlayer(), "§aBEGIN!", 0, 10, 0);
				XSound.ITEM_TOTEM_USE.playSound(arena.getPlayer(), 3.0F, 2.0F);
			}
			counter -= 1;
		} else if (counter > 0) {
			if (blocks.size() < ConfigManager.getInstance().getConfig().getInt("MaxBlocksInArena")) {
				int X = rand.nextInt(coords.get("maxX") + 1 - coords.get("minX")) + coords.get("minX");
				int Y = rand.nextInt(coords.get("maxY") + 1 - coords.get("minY")) + coords.get("minY");
				int Z = rand.nextInt(coords.get("maxZ") + 1 - coords.get("minZ")) + coords.get("minZ");
				Block b = arena.getSelection().getWorld().getBlockAt(X, Y, Z);
				while ((!b.getType().equals(Material.AIR)) || ((X == arena.getSpawn().getBlockX()) && (Z == arena.getSpawn().getBlockZ()))) {
					X = rand.nextInt(coords.get("maxX") + 1 - coords.get("minX")) + coords.get("minX");
					Y = rand.nextInt(coords.get("maxY") + 1 - coords.get("minY")) + coords.get("minY");
					Z = rand.nextInt(coords.get("maxZ") + 1 - coords.get("minZ")) + coords.get("minZ");
					b = arena.getSelection().getWorld().getBlockAt(X, Y, Z);
				}
				if (Math.random() < 0.2D) {
					if (Util.is113orUp()) {
						BlockState state = b.getState();
						state.setType(XMaterial.RED_WOOL.parseMaterial());
						state.update(true, true);
					} else {
						b.setType(XMaterial.RED_WOOL.parseMaterial());
						
						BlockState state = b.getState();
						MaterialData data = state.getData();
						if (data instanceof Wool) {
						    Wool wool = (Wool) data;
						    wool.setColor(DyeColor.RED);
						    state.update(true, true);
						}
					}
					blocks.add(new SGBlock(XMaterial.RED_WOOL, b.getLocation()));
				} else {
					if (Util.is113orUp()) {
						BlockState state = b.getState();
						state.setType(XMaterial.GREEN_WOOL.parseMaterial());
						state.update(true, true);
					} else {
						b.setType(XMaterial.GREEN_WOOL.parseMaterial());
						
						BlockState state = b.getState();
						MaterialData data = state.getData();
						if (data instanceof Wool) {
						    Wool wool = (Wool) data;
						    wool.setColor(DyeColor.GREEN);
						    state.update(true, true);
						}
					}
					blocks.add(new SGBlock(XMaterial.GREEN_WOOL, b.getLocation()));
				}
			}
			arena.getPlayer().setLevel(counter);
			TitleActionBarUtil.sendActionBarMessage(arena.getPlayer(),
					ChatColor.BOLD + ChatColor.GOLD.toString() + "❯❯" + ChatColor.RESET + ChatColor.DARK_BLUE
							+ " Score: " + arena.getScore() + ChatColor.BOLD + ChatColor.GOLD.toString() + " ❮❮");
			counter --;
		} else {
			Player p = arena.getPlayer();
			org.bukkit.inventory.ItemStack[] items = arena.getPInvContents();
			Location loc = arena.getPLoc();
			loc.setYaw(loc.getYaw() + 180.0F);
			int XPLevel = arena.getXPLevel();
			float XPFloat = arena.getXPFloat();
			if (ScoreManager.getInstance().addScore(p, arena.getScore())) {
				p.sendMessage(MessageManager.getInstance().getNewHighscore(arena.getScore()));
			} else {
				p.sendMessage(MessageManager.getInstance().getScoreMessage(arena.getScore()));
			}
			arena.stopArena();
			p.teleport(loc);
			p.setExp(0.0F);
			p.setLevel(0);
			p.setLevel(XPLevel);
			p.setExp(XPFloat);
			p.getInventory().clear();
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					p.getInventory().setItem(i, items[i]);
				}
			}
			for (int x = coords.get("minX"); x <= coords.get("maxX"); x++) {
				for (int y = coords.get("minY"); y <= coords.get("maxY"); y++) {
					for (int z = coords.get("minZ"); z <= coords.get("maxZ"); z++) {
						p.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
					}
				}
			}
			coords.clear();
			blocks.clear();
			cancel();
		}
	}

	public HashMap<String, Integer> getCoords() {
		return coords;
	}

	public SGBlock getSGBlock(Location loc) {
		for (SGBlock b : blocks) {
			if (b.getLocation().equals(loc)) {
				return b;
			}
		}
		return null;
	}

	public void removeSGBlock(SGBlock b) {
		if (b.getXMaterial().equals(XMaterial.GREEN_WOOL)) {
			arena.increaseScore(1);
		} else {
			arena.increaseScore(5);
		}
		blocks.remove(b);
	}
}
