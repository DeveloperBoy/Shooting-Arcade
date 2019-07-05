package me.x1machinemaker1x.shootinggallery;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.x1machinemaker1x.shootinggallery.utils.Cuboid;

public class Arena {
	private String id;
	private Cuboid selection = null;
	private Location spawn = null;
	private boolean enabled;
	private boolean isInUse = false;
	private Player p = null;
	private Location pLoc = null;
	private ItemStack[] pInvContents = null;
	private int pXPLevel = 0;
	private float pXPFloat = 0.0F;
	private ArenaTask task;
	private int score = 0;

	public Arena(String id, Cuboid selection, boolean enabled) {
		this.id = id;
		this.selection = selection;
		this.enabled = enabled;
	}

	public Arena(String id, Cuboid selection, Location spawn, boolean enabled) {
		this.id = id;
		this.selection = selection;
		this.spawn = spawn;
		this.enabled = enabled;
	}

	public String getID() {
		return this.id;
	}

	public Cuboid getSelection() {
		return this.selection;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getSpawn() {
		return this.spawn;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean inUse() {
		return this.isInUse;
	}

	public Player getPlayer() {
		if (this.p != null) {
			return this.p;
		}
		return null;
	}

	public ArenaTask getArenaTask() {
		return this.task;
	}

	public void increaseScore(int amount) {
		this.score += amount;
	}

	public int getScore() {
		return this.score;
	}

	public void startArena(Player p, ItemStack[] pInvContents, Location pLoc, int pXPLevel, float pXPFloat,
			ArenaTask task) {
		this.score = 0;
		this.p = p;
		this.pLoc = pLoc;
		this.pInvContents = pInvContents;
		this.pXPLevel = pXPLevel;
		this.pXPFloat = pXPFloat;
		this.isInUse = true;
		this.task = task;
	}

	public void stopArena() {
		this.score = 0;
		this.p = null;
		this.pLoc = null;
		this.pInvContents = null;
		this.pXPLevel = 0;
		this.pXPFloat = 0.0F;
		this.isInUse = false;
		this.task = null;
	}

	public void forceStop() {
		this.score = 0;
		this.p = null;
		this.pLoc = null;
		this.pInvContents = null;
		this.pXPLevel = 0;
		this.pXPFloat = 0.0F;
		this.isInUse = false;
		try {
			Bukkit.getScheduler().cancelTask(this.task.getTaskId());
		} catch (NullPointerException e) {
		}
		this.task = null;
	}

	public ItemStack[] getPInvContents() {
		return this.pInvContents;
	}

	public Location getPLoc() {
		return this.pLoc;
	}

	public int getXPLevel() {
		return this.pXPLevel;
	}

	public float getXPFloat() {
		return this.pXPFloat;
	}
}
