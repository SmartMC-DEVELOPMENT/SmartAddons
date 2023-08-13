package us.smartmc.smartaddons;

import us.smartmc.smartaddons.command.LoadAddonCommand;
import us.smartmc.smartaddons.command.ReloadAddonCommand;
import us.smartmc.smartaddons.command.UnloadAddonCommand;
import us.smartmc.smartaddons.plugin.PluginLoader;
import us.smartmc.smartaddons.plugin.listener.PluginCommandsListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SmartAddons extends JavaPlugin {

    private static SmartAddons plugin;

    private static File mainEventPluginsDir;
    private static PluginLoader mainPluginLoader;

    @Override
    public void onEnable() {
        plugin = this;
        mainPluginLoader = new PluginLoader();
        getDataFolder().mkdirs();
        mainEventPluginsDir = new File(getDataFolder() + "/../../addon_plugins");
        mainEventPluginsDir.mkdirs();
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
