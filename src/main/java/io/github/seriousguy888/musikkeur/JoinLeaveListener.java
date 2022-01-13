package io.github.seriousguy888.musikkeur;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class JoinLeaveListener implements Listener {
  Musikkeur plugin;

  JoinLeaveListener(Musikkeur plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    plugin.dataManager.loadPlayerData(player);
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    plugin.dataManager.savePlayerData(player);
  }
}
