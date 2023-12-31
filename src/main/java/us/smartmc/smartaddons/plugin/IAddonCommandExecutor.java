package us.smartmc.smartaddons.plugin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface IAddonCommandExecutor {

    void executeDefault(CommandSender sender, String label, String[] args);

    void executeAll(CommandSender sender, String[] args);

    void executeConsole(CommandSender sender, String[] args);

    void executePlayer(Player sender, String[] args);

    void executeAdminPlayer(Player sender, String[] args);
}
