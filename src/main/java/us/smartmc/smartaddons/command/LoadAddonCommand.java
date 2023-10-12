package us.smartmc.smartaddons.command;

import us.smartmc.smartaddons.manager.CustomCommandManager;
import us.smartmc.smartaddons.type.CommandPermissionNode;
import us.smartmc.smartaddons.type.IPluginPlayer;
import us.smartmc.smartaddons.type.PluginCommand;
import us.smartmc.smartaddons.util.FileUtil;
import org.bukkit.ChatColor;

import java.io.File;

public class LoadAddonCommand extends PluginCommand {

    public LoadAddonCommand(String name) {
        super(name, CommandPermissionNode.ADMINISTRATOR, "la");
    }

    @Override
    public void onCommand(IPluginPlayer<?> sender, String[] args) {
        if (!sender.hasPermission("*")) return;
        if (args.length == 0) {
            sender.sendMessage("Write a addon name to try load it");
        }

        File pluginFile = FileUtil.searchFileByName(CustomCommandManager.getAddonsPlugin().getMainEventPluginsDir(), args[0]);

        if (pluginFile == null) {
            sender.sendMessage("Not addon found with similar name as file");
            return;
        }

        CustomCommandManager.getAddonsPlugin().getMainPluginLoader().loadPlugin(pluginFile, true);
        sender.sendMessage("&aLoaded addon");
    }
}
