package us.smartmc.smartaddons.velocity;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import us.smartmc.smartaddons.SmartAddonsPlugin;
import us.smartmc.smartaddons.manager.CustomCommandManager;
import us.smartmc.smartaddons.plugin.AddonLoader;
import us.smartmc.smartaddons.type.PluginPlayer;
import us.smartmc.smartaddons.type.SpigotCommandSender;

import java.io.File;
import java.nio.file.Path;

@Plugin(id = "smartaddons-velocity",
        version = "1.20", description = "New")
public class SmartAddonsVelocity implements SmartAddonsPlugin {

    private static SmartAddonsVelocity plugin;

    private static File mainEventPluginsDir;
    private AddonLoader mainPluginLoader;

    private CustomCommandManager commandsManager;

    private final Path directory;
    private final ProxyServer proxyServer;

    public SmartAddonsVelocity(@DataDirectory Path directory, ProxyServer proxyServer) {
        this.directory = directory;
        this.proxyServer = proxyServer;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        plugin = this;
        mainPluginLoader = new AddonLoader();
        commandsManager = new CustomCommandManager(this, "reloadAddon", "loadAddon", "unloadAddon");
        getDataFolder().mkdirs();
        mainEventPluginsDir = new File(getDataFolder() + "/../../addon_plugins");
        mainEventPluginsDir.mkdirs();
        mainPluginLoader.loadPlugins(mainEventPluginsDir.getAbsolutePath());

        proxyServer.getEventManager().register(this, this);
    }

    @Subscribe(order = PostOrder.EARLY)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        boolean executed =
                commandsManager.executeCommand(PluginPlayer.get(SpigotCommandSender.class, event.getPlayer()), event.getMessage());
        if (executed) {
            event.setCancelled(true);
        }
    }

    @Subscribe(order = PostOrder.EARLY)
    public void onCommand(CommandExecuteEvent event) {
        boolean executed =
                commandsManager.executeCommand(PluginPlayer.get(SpigotCommandSender.class, event.getCommandSource()), event.getCommand());
        if (executed) {
            event.setResult(CommandExecuteEvent.CommandResult.denied());
        }
    }

    public File getDataFolder() {
        return directory.toFile();
    }

    public AddonLoader getMainPluginLoader() {
        return mainPluginLoader;
    }

    public CustomCommandManager getCommandManager() {
        return commandsManager;
    }

    public static SmartAddonsVelocity getPlugin() {
        return plugin;
    }

    public File getMainEventPluginsDir() {
        return mainEventPluginsDir;
    }
}
