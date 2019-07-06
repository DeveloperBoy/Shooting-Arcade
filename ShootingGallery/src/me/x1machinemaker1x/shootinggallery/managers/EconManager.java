package me.x1machinemaker1x.shootinggallery.managers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EconManager {
	private static EconManager instance = new EconManager();
	private Plugin pl;

	public static EconManager getInstance() {
		return instance;
	}

	private Economy econ = null;

	public void onEnable(Plugin pl) {
		this.pl = pl;
		if (!setupEconomy()) {
			pl.getLogger().severe(
					String.format("[%s] - Disabled due to no Vault dependency found!", pl.getDescription().getName()));
			pl.getServer().getPluginManager().disablePlugin(pl);
			return;
		}
	}

	private boolean setupEconomy() {
		if (this.pl.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = this.pl.getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		this.econ = ((Economy) rsp.getProvider());
		return this.econ != null;
	}

	public void addMoney(Player p, int amount) {
		EconomyResponse r = this.econ.depositPlayer(p, amount);
		if (r.transactionSuccess()) {
			p.sendMessage(MessageManager.getInstance().getAddMoney(this.econ.format(amount)));
		} else {
			p.sendMessage(MessageManager.getInstance().getMoneyError(r.errorMessage));
		}
	}
}
