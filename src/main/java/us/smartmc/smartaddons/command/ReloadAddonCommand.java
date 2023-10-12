package us.smartmc.smartaddons.command;

import us.smartmc.smartaddons.manager.CustomCommandManager;
import org.bukkit.ChatColor;
import us.smartmc.smartaddons.type.CommandPermissionNode;
import us.smartmc.smartaddons.type.IPluginPlayer;
import us.smartmc.smartaddons.type.PluginCommand;

public class ReloadAddonCommand extends PluginCommand {

    public ReloadAddonCommand(String name) {
        super(name, CommandPermissionNode.ADMINISTRATOR, "rladdon", "ra");
    }

    @Override
    public void onCommand(IPluginPlayer<?> sender, String[] args) {
        if (!sender.hasPermission("*")) return;
        if (args.length == 0) {
            sender.sendMessage("Write a plugin name to reload");
            return;
        }
        CustomCommandManager.getAddonsPlugin().getMainPluginLoader().reloadPlugin(args[0]);
        sender.sendMessage("&aReloaded addon");
    }
}
