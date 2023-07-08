package me.imsergioh.smartaddons;

import me.imsergioh.smartaddons.command.LoadAddonCommand;
import me.imsergioh.smartaddons.command.ReloadAddonCommand;
import me.imsergioh.smartaddons.command.UnloadAddonCommand;
import me.imsergioh.smartaddons.plugin.PluginLoader;
import me.imsergioh.smartaddons.plugin.listener.PluginCommandsListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SmartAddons extends JavaPlugin {

    private static SmartAddons plugin;
    private static File mainEventPluginsDir;
    private static PluginLoader mainPluginLoader;

    @Override
    public void onEnable() {
        plugin = this;
        getDataFolder().mkdirs();
        mainEventPluginsDir = new File(getDataFolder() + "/../../addon_plugins");
        mainEventPluginsDir.mkdirs();
        mainPluginLoader = new PluginLoader();
        mainPluginLoader.loadPlugins(mainEventPluginsDir.getAbsolutePath());

        Bukkit.getPluginManager().registerEvents(new PluginCommandsListeners(), this);

        getCommand("unloadAddon").setExecutor(new UnloadAddonCommand());
        getCommand("reloadAddon").setExecutor(new ReloadAddonCommand());
        getCommand("loadAddon").setExecutor(new LoadAddonCommand());
    }

    public static SmartAddons getPlugin() {
        return plugin;
    }

    public static File getMainEventPluginsDir() {
        return mainEventPluginsDir;
    }

    public static PluginLoader getMainPluginLoader() {
        return mainPluginLoader;
    }
}
