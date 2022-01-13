package io.github.seriousguy888.musikkeur;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MusikkeurCommand implements CommandExecutor {
    Musikkeur plugin;

    MusikkeurCommand(Musikkeur plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String commandLabel,
                             String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("The console cannot use this command!");
            return true;
        }




        return false;
    }
}