package io.github.seriousguy888.musikkeur;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DataManager {
  Musikkeur plugin;
  DataManager(Musikkeur plugin) {
    this.plugin = plugin;
  }

  public void loadPlayerData() {
    Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
  }
  public void loadPlayerData(Player player) {
    String uuid = player.getUniqueId().toString();

    boolean enabled;
    if(!plugin.dataConfig.contains(uuid + ".enabled")) {
      // set enabled to true if it is not present in the config file
      enabled = true;
    } else {
      // get the player's data saved in data.yml
      enabled = plugin.dataConfig.getBoolean(uuid + ".enabled");
    }

    // save whether the player has musikkeur enabled in this hashmap.
    plugin.musikkeurEnabled.put(player, enabled);
  }

  public void savePlayerData() {
    Bukkit.getOnlinePlayers().forEach(this::savePlayerData);
  }
  public void savePlayerData(Player player) {
    String uuid = player.getUniqueId().toString();

    Boolean enabled = plugin.musikkeurEnabled.get(player);
    plugin.dataConfig.set(uuid + ".enabled", enabled);
    try {
      plugin.dataConfig.save(plugin.dataFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
