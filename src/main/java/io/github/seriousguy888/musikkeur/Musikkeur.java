package io.github.seriousguy888.musikkeur;

import io.github.seriousguy888.musikkeur.guis.NoteBlockGui;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Musikkeur extends JavaPlugin {
  @Override
  public void onEnable() {
    registerListeners();
  }

  private void registerListeners() {
    PluginManager pluginManager = Bukkit.getPluginManager();

    pluginManager.registerEvents(new NoteBlockGui(), this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
