package gg.incash.bukkit;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BukkitTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equalsIgnoreCase("incash") && sender instanceof Player && sender.hasPermission("op")) {
            List<String> commandList = new ArrayList<>();
            commandList.add("reload");
            return commandList;
        }
        return null;
    }

}
