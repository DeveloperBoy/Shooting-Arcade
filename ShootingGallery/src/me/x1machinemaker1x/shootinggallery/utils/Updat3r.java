package me.x1machinemaker1x.shootinggallery.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.x1machinemaker1x.shootinggallery.Main;

public class Updat3r {

	private boolean taskRunning = false;
	private Update update = null;

	private static Updat3r instance = null;
	public static String PROJECT_NAME = "ShootingGallery";
	public static String API_KEY = "sZEfHUzHvF36q915HsM66a7hIjdmJrGw";

	public static Updat3r getInstance() {
		if (instance == null) {
			instance = new Updat3r();
		}
		return instance;
	}

	public void startTask() {
		if (taskRunning) {
			return;
		}
		taskRunning = true;
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), new Runnable() {
			public void run() {
				update = getLatestUpdate();
			}
		}, 30 * 20l, 30 * 60 * 20l);
	}

	public Update getLatestUpdate() {
		try {
			URL url = new URL("https://updates.mrwouter.nl/api/v1/updates/?project=" + PROJECT_NAME + "&key=" + API_KEY
					+ "&show=latest");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent", PROJECT_NAME + " UpdateChecker");
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();

			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) conn.getContent()));
			JsonObject rootobj = root.getAsJsonObject();

			int errorcode = rootobj.get("status").getAsInt();
			if (errorcode != 200) {
				System.out.println(
						"[Updat3r] [" + PROJECT_NAME + "] An error has occured: " + rootobj.get("message").getAsString());
				return null;
			}
			JsonArray updates = rootobj.get("updates").getAsJsonArray();
			for (JsonElement update : updates) {
				JsonObject updateObj = update.getAsJsonObject();
				String version = updateObj.get("version").getAsString();
				String download = updateObj.get("download").getAsString();
				String releaseDate = updateObj.get("releaseDate").getAsString();
				boolean critical = updateObj.get("critical").getAsBoolean();
				return new Update(version, download, releaseDate, critical);
			}
			return null;
		} catch (Exception ex) {
			System.out.println("[Updat3r] [" + PROJECT_NAME
					+ "] An error has occured. Please report the stacktrace below to the developer of " + PROJECT_NAME);
			ex.printStackTrace();
			return null;
		}
	}

	public Update getLatestCached() {
		if (update == null) {
			update = getLatestUpdate();
		}
		return update;
	}

	public void downloadLatest(String fileURL) {
		try {
			URL url = new URL(fileURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestProperty("User-Agent", PROJECT_NAME + " UpdateChecker");
			int responseCode = httpConn.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = httpConn.getInputStream();
				String saveFilePath = new File(
						Main.getPlugin().getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();

				FileOutputStream outputStream = new FileOutputStream(saveFilePath);

				int bytesRead = -1;
				byte[] buffer = new byte[4096];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				outputStream.close();
				inputStream.close();
			} else {
				System.out.println("[Updat3r] [" + PROJECT_NAME
						+ "] An error has occured whilst downloading this resource. Please report the stacktrace below to the developer of "
						+ PROJECT_NAME + " (resp. code: " + responseCode + ")");
			}
			httpConn.disconnect();
		} catch (Exception ex) {
			System.out.println("[Updat3r] [" + PROJECT_NAME
					+ "] An error has occured whilst downloading this resource. Please report the stacktrace below to the developer of "
					+ PROJECT_NAME);
			ex.printStackTrace();
			return;
		}
	}

	public void sendUpdateMessage(Player p) {
		p.sendMessage("   �3-=-=-=[�bShootingGallery�3]=-=-=-   ");
		p.sendMessage("�3There is a update available for �bShootingGallery�3!");
		p.sendMessage("�3You're now using version �b" + Main.getPlugin().getDescription().getVersion() + "�3.");
		p.sendMessage("�3The newest version is �b" + update.getVersion());
		if (update.isCritical()) {
			p.sendMessage("�3�lPay attention. This update is marked as critical!");
		}
		p.sendMessage("�3To install this update, please typ �b/sg update");
		p.sendMessage("   �3-=-=-=[�bShootingGallery�3]=-=-=-   ");
	}

	public void sendUpdateMessageLater(Player p) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), new Runnable() {
			public void run() {
				if (p.isOp()) {
					if (update != null && update.isNewer()) {
						sendUpdateMessage(p);
					}
				}
			}
		}, 40l);
	}

	public class Update {

		private String version, downloadlink, releaseDate;
		private boolean critical;

		public Update(String version, String downloadlink, String releaseDate, boolean critical) {
			this.version = version;
			this.downloadlink = downloadlink;
			this.releaseDate = releaseDate;
			this.critical = critical;
		}

		public String getVersion() {
			return version;
		}

		public String getDownloadLink() {
			return downloadlink;
		}

		public String getReleaseDate() {
			return releaseDate;
		}

		public boolean isCritical() {
			return critical;
		}

		public boolean isNewer() {
			String[] ver = version.split("\\.");
			String[] sdbver = Main.getPlugin().getDescription().getVersion().split("\\.");

			if (ver.length == 1 || ver[0].equals("?")) {
				return false;
			} else {
				if (ver.length <= 1) {
					return false;
				} else {
					if (Integer.valueOf(ver[0]) > Integer.valueOf(sdbver[0])) {
						return true;
					}
					if (Integer.valueOf(ver[1]) > Integer.valueOf(sdbver[1])) {
						return true;
					}
					if (ver.length >= 3) {
						if (Integer.valueOf(ver[1]) >= Integer.valueOf(sdbver[1])) {
							if (sdbver.length <= 2) {
								return true;
							} else if (Integer.valueOf(ver[2]) > Integer.valueOf(sdbver[2])) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}
	}
}