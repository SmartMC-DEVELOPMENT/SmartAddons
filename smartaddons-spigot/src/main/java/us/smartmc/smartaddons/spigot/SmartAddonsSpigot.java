package us.smartmc.smartaddons.spigot;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import us.smartmc.smartaddons.SmartAddonsPlugin;
import us.smartmc.smartaddons.manager.CustomCommandManager;
import us.smartmc.smartaddons.plugin.AddonClassLoader;
import us.smartmc.smartaddons.plugin.AddonLoader;
import us.smartmc.smartaddons.type.PluginPlayer;
import us.smartmc.smartaddons.type.SpigotCommandSender;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class SmartAddonsSpigot extends JavaPlugin implements SmartAddonsPlugin, Listener {

    private static SmartAddonsSpigot plugin;

    private static File mainEventPluginsDir;

    private CustomCommandManager commandsManager;

    @Override
    public void onEnable() {
        plugin = this;
        commandsManager = new CustomCommandManager(this, "reloadAddon", "loadAddon", "unloadAddon");
        getDataFolder().mkdirs();
        mainEventPluginsDir = new File(getDataFolder() + "/../../addon_plugins");
        mainEventPluginsDir.mkdirs();

        AddonLoader.loadPlugins(mainEventPluginsDir.getAbsolutePath());

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        boolean executed =
                commandsManager.executeCommand(PluginPlayer.get(SpigotCommandSender.class, event.getPlayer()), event.getMessage());
        if (executed) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(ServerCommandEvent event) {
        boolean executed =
                commandsManager.executeCommand(PluginPlayer.get(SpigotCommandSender.class, event.getSender()), event.getCommand());
        if (executed) {
            event.setCancelled(true);
        }
    }

    @Override
    public AddonLoader getMainPluginLoader() {
        return new AddonLoader();
    }

    @Override
    public CustomCommandManager getCommandManager() {
        return commandsManager;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public File getMainEventPluginsDir() {
        return mainEventPluginsDir;
    }
}
