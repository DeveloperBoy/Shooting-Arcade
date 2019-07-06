package me.x1machinemaker1x.shootinggallery.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ScoreManager {
	private static ScoreManager instance = new ScoreManager();
	HashMap<UUID, Integer> scores;

	public static ScoreManager getInstance() {
		return instance;
	}

	public void onEnable() {
		scores = new HashMap<UUID, Integer>();
		ConfigurationSection conf = ConfigManager.getInstance().getScores().getConfigurationSection("Scores");
		if (conf != null) {
			for (String uuid : conf.getKeys(false)) {
				scores.put(UUID.fromString(uuid), Integer.valueOf(conf.getInt(uuid)));
			}
		}
	}

	public void updateScoresInConfig() {
		ConfigManager.getInstance().getScores().set("Arenas", null);
		ConfigManager.getInstance().saveScores();
		if (!scores.isEmpty()) {
			ConfigurationSection conf = ConfigManager.getInstance().getScores().createSection("Scores");
			for (UUID uuid : scores.keySet()) {
				conf.set(uuid.toString(), scores.get(uuid));
			}
			ConfigManager.getInstance().saveScores();
		}
	}

	public boolean addScore(Player p, int score) {
		boolean highScore;
		if (scores.containsKey(p.getUniqueId())) {
			if (scores.get(p.getUniqueId()) < score) {
				scores.replace(p.getUniqueId(), score);
				highScore = true;
			} else {
				highScore = false;
			}
		} else {
			scores.put(p.getUniqueId(), Integer.valueOf(score));
			highScore = true;
		}
		updateScoresInConfig();
		return highScore;
	}

	public void resetScores() {
		scores.clear();
		ConfigManager.getInstance().getScores().set("Scores", null);
		ConfigManager.getInstance().saveScores();
	}
	
	public List<String> topScores() {
		List<String> topScores = new ArrayList<String>();
		LinkedHashMap<UUID, Integer> sortedScores = sortHashMapByValues(scores);
		for (UUID uuid : sortedScores.keySet()) {
			topScores.add(uuid.toString() + ":" + sortedScores.get(uuid));
		}
		return topScores;
	}
	
	public LinkedHashMap<UUID, Integer> sortHashMapByValues(
	        HashMap<UUID, Integer> passedMap) {
	    List<UUID> mapKeys = new ArrayList<>(passedMap.keySet());
	    List<Integer> mapValues = new ArrayList<>(passedMap.values());
	    Collections.sort(mapValues, Collections.reverseOrder());
	    Collections.sort(mapKeys);

	    LinkedHashMap<UUID, Integer> sortedMap = new LinkedHashMap<>();

	    Iterator<Integer> valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        Integer val = valueIt.next();
	        Iterator<UUID> keyIt = mapKeys.iterator();

	        while (keyIt.hasNext()) {
	            UUID key = keyIt.next();
	            Integer comp1 = passedMap.get(key);
	            Integer comp2 = val;

	            if (comp1.equals(comp2)) {
	                keyIt.remove();
	                sortedMap.put(key, val);
	                break;
	            }
	        }
	    }
	    return sortedMap;
	}
}