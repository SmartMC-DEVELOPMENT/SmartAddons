package us.smartmc.smartaddons.bungee;

import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import us.smartmc.smartaddons.SmartAddonsPlugin;
import us.smartmc.smartaddons.manager.CustomCommandManager;
import us.smartmc.smartaddons.plugin.PluginLoader;
import us.smartmc.smartaddons.type.BungeeCordPlayer;
import us.smartmc.smartaddons.type.PluginPlayer;
import us.smartmc.smartaddons.type.ProxyCommandSender;

import java.io.File;

public class SmartAddonsBungee extends Plugin implements Listener, SmartAddonsPlugin {

    @Getter
    private static SmartAddonsBungee plugin;

    private static File mainEventPluginsDir;
    private static PluginLoader mainPluginLoader;

    private CustomCommandManager commandsManager;

    @Override
    public void onEnable() {
        plugin = this;
        mainPluginLoader = new PluginLoader();
        commandsManager = new CustomCommandManager(this, "bReloadAddon", "bLoadAddon", "bUnloadAddon");
        getDataFolder().mkdirs();
        mainEventPluginsDir = new File(getDataFolder() + "/../../addon_plugins");
        mainEventPluginsDir.mkdirs();
        mainPluginLoader.loadPlugins(mainEventPluginsDir.getAbsolutePath());

        getProxy().getPluginManager().registerListener(this, this);
    }

    @Override
    public CustomCommandManager getCommandManager() {
        return commandsManager;
    }

    @Override
    public PluginLoader getMainPluginLoader() {
        return mainPluginLoader;
    }

    @Override
    public File getMainEventPluginsDir() {
        return mainEventPluginsDir;
    }
}
