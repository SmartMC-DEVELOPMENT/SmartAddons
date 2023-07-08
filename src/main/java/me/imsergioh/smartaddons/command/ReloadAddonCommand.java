package me.imsergioh.smartaddons.command;

import me.imsergioh.smartaddons.SmartAddons;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadAddonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("*")) return true;
        if (args.length == 0) {
            sender.sendMessage("Write a plugin name to reload");
            return true;
        }
        SmartAddons.getMainPluginLoader().reloadPlugin(args[0]);
        return true;
    }
}
