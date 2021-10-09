package io.github.seriousguy888.musikkeur;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

public final class Musikkeur extends JavaPlugin {
  @Override
  public void onEnable() {
    registerListeners();
  }

  private void registerListeners() {
    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new MenuFunctionListener(), this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
