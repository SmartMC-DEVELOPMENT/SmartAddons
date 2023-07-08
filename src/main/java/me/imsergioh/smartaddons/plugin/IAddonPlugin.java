package me.imsergioh.smartaddons.plugin;

import org.bukkit.event.Listener;

public interface IAddonPlugin {

    void start();

    void stop();

    void reload();

    void registerListeners(Listener... listeners);

    void registerCommand(AddonPluginCommand executor);
}
