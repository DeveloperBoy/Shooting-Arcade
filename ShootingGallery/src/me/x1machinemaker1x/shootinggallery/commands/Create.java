package me.x1machinemaker1x.shootinggallery.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionOwner;

import me.x1machinemaker1x.shootinggallery.utils.ArenaManager;
import me.x1machinemaker1x.shootinggallery.utils.Cuboid;
import me.x1machinemaker1x.shootinggallery.utils.MessageManager;

public class Create extends SubCommand {
	private Method worldAdaptMethod = null;
	
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(args[0]) != null) {
			p.sendMessage(MessageManager.getInstance().getAlreadyCreated());
			return;
		}
		Class<?> worldEditAdapterClass = null;
		try {
			worldEditAdapterClass = Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			worldAdaptMethod = worldEditAdapterClass.getMethod("adapt", World.class);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
		Object worldEditPlayer = null;
		try {
			worldEditPlayer = worldAdaptMethod.invoke(null, p);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		LocalSession l = WorldEdit.getInstance().getSessionManager().get((SessionOwner) worldEditPlayer);
        Region s;
        try {
            s = l.getSelection(l.getSelectionWorld());
        } catch (IncompleteRegionException e) {
        	p.sendMessage(MessageManager.getInstance().getNoSelection());
			return;
        }
        if (s instanceof Polygonal2DRegion) {
			p.sendMessage(MessageManager.getInstance().getNotCuboid());
			return;
		}
        Cuboid cub = new Cuboid(new Location(p.getWorld(), s.getMinimumPoint().getX(), s.getMinimumPoint().getY(), s.getMinimumPoint().getZ()), new Location(p.getWorld(), s.getMaximumPoint().getX(), s.getMaximumPoint().getY(), s.getMaximumPoint().getZ()));
		ArenaManager.getInstance().addArena(args[0], cub, false);
		p.sendMessage(MessageManager.getInstance().getArenaCreated(args[0]));
	}

	public String name() {
		return "create";
	}

	public String info() {
		return "Creates an ShootingGallery arena";
	}

	public String[] aliases() {
		return new String[] { "add", "carena" };
	}

	public String permission() {
		return "shootinggallery.create";
	}

	public int argsReq() {
		return 1;
	}

	public String format() {
		return "/sg create <ID>";
	}
}
