package io.github.seriousguy888.musikkeur;

import io.github.seriousguy888.musikkeur.listeners.JoinLeaveListener;
import io.github.seriousguy888.musikkeur.listeners.NoteBlockGui;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Musikkeur extends JavaPlugin {
  public File dataFile;
  public FileConfiguration dataConfig;
  public DataManager dataManager;
  public HashMap<Player, Boolean> musikkeurEnabled;

  @Override
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void onEnable() {
    registerListeners();

    dataFile = new File(getDataFolder() + File.separator + "data.yml");
    if(!dataFile.exists()) {
      try {
        dataFile.getParentFile().mkdirs();
        dataFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    dataConfig = YamlConfiguration.loadConfiguration(dataFile);

    dataManager = new DataManager(this);
    musikkeurEnabled = new HashMap<>();

    dataManager.loadPlayerData(); // load data of all online players
  }

  private void registerListeners() {
    PluginManager pluginManager = Bukkit.getPluginManager();

    pluginManager.registerEvents(new NoteBlockGui(this), this);
    pluginManager.registerEvents(new JoinLeaveListener(this), this);

    this.getCommand("musikkeur").setExecutor(new MusikkeurCommand(this));
  }

  @Override
  public void onDisable() {
    dataManager.savePlayerData();
  }
}
