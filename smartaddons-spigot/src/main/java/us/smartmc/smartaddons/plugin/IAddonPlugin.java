package us.smartmc.smartaddons.plugin;

import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public interface IAddonPlugin {

    void start();

    void stop();

    void reload();

    void registerListeners(JavaPlugin plugin, Listener... listeners);

    void registerCommand(BukkitCommand... executors);
}
