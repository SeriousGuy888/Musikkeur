package io.github.seriousguy888.musikkeur;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DataManager {
  Musikkeur plugin;
  DataManager(Musikkeur plugin) {
    this.plugin = plugin;
  }

  /**
   * Load in the preferences and data of all currently online players from data.yml.
   */
  public void loadPlayerData() {
    Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
  }

  /**
   * Load the preferences and data of a player from data.yml.
   * @param player The player to load the data for.
   */
  public void loadPlayerData(Player player) {
    String uuid = player.getUniqueId().toString();
    boolean enabled = true;

    if(plugin.dataConfig.contains(uuid + ".enabled"))
      enabled = plugin.dataConfig.getBoolean(uuid + ".enabled");
    plugin.musikkeurEnabled.put(player, enabled);
  }


  /**
   * Save the preferences and data of all currently online players to data.yml.
   */
  public void savePlayerData() {
    Bukkit.getOnlinePlayers().forEach(this::savePlayerData);
  }

  /**
   * Save the preferences and data of a player and write it to data.yml.
   * @param player The player to save the data for.
   */
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
