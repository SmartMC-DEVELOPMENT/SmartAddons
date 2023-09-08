package us.smartmc.smartaddons.plugin;

import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;

public interface IAddonPlugin {

    void start();

    void stop();

    void reload();

    void registerListeners(Listener... listeners);

    void registerCommand(BukkitCommand... executors);
}
