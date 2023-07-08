package me.imsergioh.smartaddons.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AddonPluginCommand implements CommandExecutor, IAddonCommandExecutor {

    private final String name;
    private final boolean forAdmins;
    private final String permission;

    public AddonPluginCommand(String name, boolean forAdmins, String permissions) {
        this.name = name;
        this.forAdmins = forAdmins;
        this.permission = permissions;
    }

    public AddonPluginCommand(String name, boolean forAdmins) {
        this.name = name;
        this.forAdmins = forAdmins;
        this.permission = "*";
    }

    public AddonPluginCommand(String name) {
        this.name = name;
        this.forAdmins = false;
        this.permission = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        executeAll(sender, args);

        if (sender instanceof Player) {
            executePlayer(((Player) sender).getPlayer(), args);
        } else {
            executeConsole(sender, args);
            return true;
        }

        if (forAdmins) {
            Player player = (Player) sender;
            if (!player.hasPermission("*")) return true;
            executeAdminPlayer(player, args);
        }
        return true;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isForAdmins() {
        return forAdmins;
    }

    public String getName() {
        return name;
    }
}
