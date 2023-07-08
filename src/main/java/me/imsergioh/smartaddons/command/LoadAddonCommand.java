package me.imsergioh.smartaddons.command;

import me.imsergioh.smartaddons.SmartAddons;
import me.imsergioh.smartaddons.util.FileUtil;
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

        File pluginFile = FileUtil.searchFileByName(SmartAddons.getMainEventPluginsDir(), args[0]);

        if (pluginFile == null) {
            sender.sendMessage("Not addon found with similar name as file");
            return true;
        }

        SmartAddons.getMainPluginLoader().loadPlugin(pluginFile, true);
        return true;
    }
}
