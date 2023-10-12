package us.smartmc.smartaddons.command;

import us.smartmc.smartaddons.manager.CustomCommandManager;
import org.bukkit.ChatColor;
import us.smartmc.smartaddons.type.CommandPermissionNode;
import us.smartmc.smartaddons.type.IPluginPlayer;
import us.smartmc.smartaddons.type.PluginCommand;

public class UnloadAddonCommand extends PluginCommand {

    public UnloadAddonCommand(String name) {
        super(name, CommandPermissionNode.ADMINISTRATOR, "ua");
    }

    @Override
    public void onCommand(IPluginPlayer<?> sender, String[] args) {
        if (!sender.hasPermission("*")) return;
        if (args.length == 0) {
            sender.sendMessage("Write a plugin name to unload");
            return;
        }
        CustomCommandManager.getAddonsPlugin().getMainPluginLoader().unload(args[0]);
        sender.sendMessage("&aUnloaded addon");
    }
}
