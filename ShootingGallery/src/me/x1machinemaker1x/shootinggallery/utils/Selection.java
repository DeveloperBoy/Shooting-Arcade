package me.x1machinemaker1x.shootinggallery.utils;

import java.util.UUID;

import org.bukkit.Location;

public class Selection {
	
	private UUID playeruuid;
	private Location locone;
	private Location loctwo;
	
	public UUID getPlayeruuid() {
		return playeruuid;
	}
	
	public void setPlayeruuid(UUID playeruuid) {
		this.playeruuid = playeruuid;
	}

	public Location getLocone() {
		return locone;
	}

	public void setLocone(Location locone) {
		this.locone = locone;
	}

	public Location getLoctwo() {
		return loctwo;
	}

	public void setLoctwo(Location loctwo) {
		this.loctwo = loctwo;
	}

}
