package us.smartmc.smartaddons;

import us.smartmc.smartaddons.manager.CustomCommandManager;
import us.smartmc.smartaddons.plugin.PluginLoader;

import java.io.File;

public interface SmartAddonsPlugin {

    CustomCommandManager getCommandManager();

    PluginLoader getMainPluginLoader();
    File getMainEventPluginsDir();

}
