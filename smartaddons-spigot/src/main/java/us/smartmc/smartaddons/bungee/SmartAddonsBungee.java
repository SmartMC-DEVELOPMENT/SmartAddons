package us.smartmc.smartaddons.bungee;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import us.smartmc.smartaddons.SmartAddonsPlugin;
import us.smartmc.smartaddons.manager.CustomCommandManager;
import us.smartmc.smartaddons.plugin.AddonLoader;

import java.io.File;

public class SmartAddonsBungee extends Plugin implements Listener, SmartAddonsPlugin {

    private static SmartAddonsBungee plugin;

    private static File mainEventPluginsDir;
    private static AddonLoader mainPluginLoader;

    private CustomCommandManager commandsManager;

    @Override
    public void onEnable() {
        plugin = this;
        mainPluginLoader = new AddonLoader();
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
    public AddonLoader getMainPluginLoader() {
        return mainPluginLoader;
    }

    @Override
    public File getMainEventPluginsDir() {
        return mainEventPluginsDir;
    }
}
