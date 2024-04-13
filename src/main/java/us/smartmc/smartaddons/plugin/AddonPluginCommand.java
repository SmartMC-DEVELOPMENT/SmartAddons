package us.smartmc.smartaddons.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public abstract class AddonPluginCommand extends BukkitCommand implements CommandExecutor, IAddonCommandExecutor {

    private final String name;
    private final boolean forAdmins;
    private final String permission;

    public AddonPluginCommand(String name, boolean forAdmins, String permissions) {
        super(name);
        this.name = name;
        this.forAdmins = forAdmins;
        this.permission = permissions;
    }

    @Override
    public void executeDefault(CommandSender sender, String label, String[] args) {
        executeAll(sender, args);
        if (sender instanceof Player) {
            executePlayer(((Player) sender).getPlayer(), args);
        } else {
            executeConsole(sender, args);
            return;
        }

        if (forAdmins) {
            Player player = (Player) sender;
            if (!player.hasPermission("*")) return;
            executeAdminPlayer(player, args);
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        executeDefault(sender, label, args);
        return false;
    }

    public AddonPluginCommand(String name, boolean forAdmins) {
        super(name);
        this.name = name;
        this.forAdmins = forAdmins;
        this.permission = "*";
    }

    public AddonPluginCommand(String name) {
        super(name);
        this.name = name;
        this.forAdmins = false;
        this.permission = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        executeDefault(sender, label, args);
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
