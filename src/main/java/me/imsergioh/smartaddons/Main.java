package me.imsergioh.smartaddons;

import me.imsergioh.smartaddons.command.LoadAddonCommand;
import me.imsergioh.smartaddons.command.ReloadAddonCommand;
import me.imsergioh.smartaddons.command.UnloadAddonCommand;
import me.imsergioh.smartaddons.plugin.PluginLoader;
import me.imsergioh.smartaddons.plugin.listener.PluginCommandsListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private File mainEventPluginsDir;
    private PluginLoader mainPluginLoader;

    @Override
    public void onEnable() {
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

    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("SmartAddons");
    }

    public File getMainEventPluginsDir() {
        return mainEventPluginsDir;
    }

    public PluginLoader getMainPluginLoader() {
        return mainPluginLoader;
    }
}
