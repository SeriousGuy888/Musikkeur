package io.github.seriousguy888.musikkeur;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinLeaveListener implements Listener {
  Musikkeur plugin;

  JoinLeaveListener(Musikkeur plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    String uuid = player.getUniqueId().toString();

    // get the player's data saved in data.yml
    Boolean enabled = plugin.dataConfig.getBoolean(uuid + ".enabled");

    // save whether the player has musikkeur enabled in this hashmap.
    plugin.musikkeurEnabled.put(player, enabled);
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    String uuid = player.getUniqueId().toString();

    boolean enabled = plugin.musikkeurEnabled.get(player);
    if(true);
  }
}
