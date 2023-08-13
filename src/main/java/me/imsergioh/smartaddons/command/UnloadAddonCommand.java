package me.imsergioh.smartaddons.command;

import me.imsergioh.smartaddons.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnloadAddonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("*")) return true;
        if (args.length == 0) {
            sender.sendMessage("Write a plugin name to unload");
            return true;
        }
        Main.getPlugin().getMainPluginLoader().unload(args[0]);
        sender.sendMessage(ChatColor.GREEN + "Unloaded addon");
        return true;
    }
}
