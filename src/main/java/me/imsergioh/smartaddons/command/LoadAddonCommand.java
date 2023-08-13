package me.imsergioh.smartaddons.command;

import me.imsergioh.smartaddons.Main;
import me.imsergioh.smartaddons.util.FileUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;

public class LoadAddonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("*")) return true;
        if (args.length == 0) {
            sender.sendMessage("Write a addon name to try load it");
            return true;
        }

        File pluginFile = FileUtil.searchFileByName(Main.getPlugin().getMainEventPluginsDir(), args[0]);

        if (pluginFile == null) {
            sender.sendMessage("Not addon found with similar name as file");
            return true;
        }

        Main.getPlugin().getMainPluginLoader().loadPlugin(pluginFile, true);
        sender.sendMessage(ChatColor.GREEN + "Loaded addon");
        return true;
    }
}
