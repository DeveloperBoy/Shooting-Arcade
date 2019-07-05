package me.x1machinemaker1x.shootinggallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import me.x1machinemaker1x.shootinggallery.utils.ConfigManager;
import me.x1machinemaker1x.shootinggallery.utils.MessageManager;
import me.x1machinemaker1x.shootinggallery.utils.PacketUtils;
import me.x1machinemaker1x.shootinggallery.utils.ScoreManager;
import me.x1machinemaker1x.shootinggallery.utils.XMaterial;

public class ArenaTask extends BukkitRunnable {
	private int counter;
	private Arena a;
	private HashMap<String, Integer> coords;
	private Random r;
	private List<SGBlock> blocks;

	public ArenaTask(int counter, Arena a) {
		blocks = new ArrayList<SGBlock>();
		r = new Random();
		coords = new HashMap<String, Integer>();
		this.counter = counter;
		this.a = a;
		CuboidSelection sel = a.getSelection();
		if (sel.getMinimumPoint().getBlockX() < sel.getMaximumPoint().getBlockX()) {
			coords.put("minX", sel.getMinimumPoint().getBlockX() + 1);
			coords.put("maxX", sel.getMaximumPoint().getBlockX() - 1);
		} else {
			coords.put("minX", sel.getMaximumPoint().getBlockX() + 1);
			coords.put("maxX", sel.getMinimumPoint().getBlockX() - 1);
		}
		if (sel.getMinimumPoint().getBlockY() < sel.getMaximumPoint().getBlockY()) {
			coords.put("minY", sel.getMinimumPoint().getBlockY() + 1);
			coords.put("maxY", sel.getMaximumPoint().getBlockY() - 1);
		} else {
			coords.put("minY", sel.getMaximumPoint().getBlockY() + 1);
			coords.put("maxY", sel.getMinimumPoint().getBlockY() - 1);
		}
		if (sel.getMinimumPoint().getBlockZ() < sel.getMaximumPoint().getBlockZ()) {
			coords.put("minZ", sel.getMinimumPoint().getBlockZ() + 1);
			coords.put("maxZ", sel.getMaximumPoint().getBlockZ() - 1);
		} else {
			coords.put("minZ", sel.getMaximumPoint().getBlockZ() + 1);
			coords.put("maxZ", sel.getMinimumPoint().getBlockZ() - 1);
		}
	}

	@SuppressWarnings("deprecation")
	public void run() {
		if (counter >= ConfigManager.getInstance().getConfig().getInt("RoundTimeInSeconds")) {
			int timeBeforeStart = counter - ConfigManager.getInstance().getConfig().getInt("RoundTimeInSeconds");
			if (timeBeforeStart > 0) {
				a.getPlayer().playSound(a.getPLoc(), Sound.ENTITY_HORSE_GALLOP, 3.0F, 0.5F);
			}
			if (timeBeforeStart == 10) {
				PacketUtils.sendTitle(a.getPlayer(), ChatColor.GOLD + "Shooting Gallery", 10, 80, 10);
				PacketUtils.sendSubTitle(a.getPlayer(), ChatColor.DARK_GRAY + "Round starts in 10 seconds!", 10,
						80, 10);
				a.getPlayer().sendMessage(MessageManager.getInstance().getGameMessages(0));
			} else if (timeBeforeStart == 9) {
				ItemStack bow = new ItemStack(Material.BOW);
				ItemMeta bowMeta = bow.getItemMeta();
				bowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
						ConfigManager.getInstance().getConfig().getString("BowName")));
				bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.YELLOW + "Use this bow to shoot the wool!");
				bowMeta.setLore(lore);
				bowMeta.setUnbreakable(true);
				bow.setItemMeta(bowMeta);
				a.getPlayer().getInventory().addItem(new org.bukkit.inventory.ItemStack[] { bow });
				org.bukkit.inventory.ItemStack arrow = new org.bukkit.inventory.ItemStack(Material.ARROW, 1);
				ItemMeta arrowMeta = arrow.getItemMeta();
				arrowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
						ConfigManager.getInstance().getConfig().getString("ArrowName")));
				arrow.setItemMeta(arrowMeta);
				a.getPlayer().getInventory().addItem(new org.bukkit.inventory.ItemStack[] { arrow });
			} else if (timeBeforeStart == 7) {
				a.getPlayer().sendMessage(MessageManager.getInstance().getGameMessages(1));
			} else if (timeBeforeStart == 5) {
				PacketUtils.sendTitle(a.getPlayer(), ChatColor.RED + "5", 2, 16, 2);
				PacketUtils.sendSubTitle(a.getPlayer(), ChatColor.DARK_GRAY + "Get ready..", 10, 1, 10);
			} else if (timeBeforeStart == 4) {
				PacketUtils.sendTitle(a.getPlayer(), ChatColor.RED + "4", 0, 10, 0);
				a.getPlayer().sendMessage(MessageManager.getInstance().getGameMessages(2));
			} else if (timeBeforeStart == 3) {
				PacketUtils.sendTitle(a.getPlayer(), ChatColor.RED + "3", 0, 10, 0);
			} else if (timeBeforeStart == 2) {
				PacketUtils.sendTitle(a.getPlayer(), ChatColor.RED + "2", 0, 10, 0);
			} else if (timeBeforeStart == 1) {
				PacketUtils.sendTitle(a.getPlayer(), ChatColor.RED + "1", 0, 10, 0);
			} else if (timeBeforeStart == 0) {
				PacketUtils.sendTitle(a.getPlayer(), ChatColor.GREEN + "Begin!", 0, 10, 0);
				a.getPlayer().playSound(a.getPLoc(), Sound.ENTITY_CREEPER_DEATH, 3.0F, 0.5F);
			}
			counter -= 1;
		} else if (counter > 0) {
			PacketUtils.cancelTitles(a.getPlayer());
			if (blocks.size() < ConfigManager.getInstance().getConfig().getInt("MaxBlocksInArena")) {
				int X = r.nextInt(coords.get("maxX") + 1 - coords.get("minX")) + coords.get("minX");
				int Y = r.nextInt(coords.get("maxY") + 1 - coords.get("minY")) + coords.get("minY");
				int Z = r.nextInt(coords.get("maxZ") + 1 - coords.get("minZ")) + coords.get("minZ");
				Block b = a.getSelection().getWorld().getBlockAt(X, Y, Z);
				while ((!b.getType().equals(Material.AIR)) || ((X == a.getSpawn().getBlockX()) && (Z == a.getSpawn().getBlockZ()))) {
					X = r.nextInt(coords.get("maxX") + 1 - coords.get("minX")) + coords.get("minX");
					Y = r.nextInt(coords.get("maxY") + 1 - coords.get("minY")) + coords.get("minY");
					Z = r.nextInt(coords.get("maxZ") + 1 - coords.get("minZ")) + coords.get("minZ");
					b = a.getSelection().getWorld().getBlockAt(X, Y, Z);
				}
				BlockState bs = b.getState();
				if (Math.random() < 0.2D) {
					b.setType(XMaterial.RED_WOOL.parseMaterial());
					b.getState().setRawData((byte) XMaterial.RED_WOOL.getData());
				} else {
					b.setType(XMaterial.GREEN_WOOL.parseMaterial());
					b.getState().setRawData((byte) XMaterial.GREEN_WOOL.getData());
				}
				bs.update(true);
			}
			a.getPlayer().setLevel(counter);
			PacketUtils.sendActionBar(a.getPlayer(),
					ChatColor.BOLD + ChatColor.GOLD.toString() + "❯❯" + ChatColor.RESET + ChatColor.DARK_BLUE
							+ " Score: " + a.getScore() + ChatColor.BOLD + ChatColor.GOLD.toString() + " ❮❮");
			counter --;
		} else {
			Player p = a.getPlayer();
			org.bukkit.inventory.ItemStack[] items = a.getPInvContents();
			Location loc = a.getPLoc();
			loc.setYaw(loc.getYaw() + 180.0F);
			int XPLevel = a.getXPLevel();
			float XPFloat = a.getXPFloat();
			if (ScoreManager.getInstance().addScore(p, a.getScore())) {
				p.sendMessage(MessageManager.getInstance().getNewHighscore(a.getScore()));
			} else {
				p.sendMessage(MessageManager.getInstance().getScoreMessage(a.getScore()));
			}
			a.stopArena();
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
		if (b.getType().equals(WoolType.GREEN)) {
			a.increaseScore(1);
		} else {
			a.increaseScore(5);
		}
		blocks.remove(b);
	}
}
