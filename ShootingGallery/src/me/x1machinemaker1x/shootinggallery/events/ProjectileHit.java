package me.x1machinemaker1x.shootinggallery.events;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.x1machinemaker1x.shootinggallery.Arena;
import me.x1machinemaker1x.shootinggallery.SGBlock;
import me.x1machinemaker1x.shootinggallery.utils.ArenaManager;
import me.x1machinemaker1x.shootinggallery.utils.XMaterial;

public class ProjectileHit implements Listener {
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (!(e.getEntity() instanceof Arrow)) {
			return;
		}
		Arrow arrow = (Arrow) e.getEntity();
		if (!(arrow.getShooter() instanceof Player)) {
			return;
		}
		Player p = (Player) arrow.getShooter();
		if (ArenaManager.getInstance().getArena(p) == null) {
			return;
		}
		Arena a = ArenaManager.getInstance().getArena(p);
		if (!e.getHitBlock().getType().equals(XMaterial.RED_WOOL.parseMaterial()) && !e.getHitBlock().getType().equals(XMaterial.GREEN_WOOL.parseMaterial())) {
			arrow.remove();
			return;
		}
		Location hitBlockLoc = e.getHitBlock().getLocation();
		if (a.getArenaTask().getSGBlock(hitBlockLoc) == null) {
			arrow.remove();
			return;
		}
		SGBlock b = a.getArenaTask().getSGBlock(hitBlockLoc);
		hitBlockLoc.getWorld().getBlockAt(b.getLocation()).setType(Material.AIR);
		hitBlockLoc.getWorld().playEffect(hitBlockLoc, Effect.SMOKE, 1);
		a.getArenaTask().removeSGBlock(b);
		arrow.remove();
	}
}
