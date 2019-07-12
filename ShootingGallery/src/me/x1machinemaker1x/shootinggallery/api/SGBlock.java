package me.x1machinemaker1x.shootinggallery.api;

import org.bukkit.Location;

import me.x1machinemaker1x.shootinggallery.utils.XMaterial;

public class SGBlock {
	private XMaterial mat;
	private Location loc;

	public SGBlock(XMaterial mat, Location loc) {
		this.mat = mat;
		this.loc = loc;
	}

	public XMaterial getXMaterial() {
		return this.mat;
	}

	public Location getLocation() {
		return this.loc;
	}
}
