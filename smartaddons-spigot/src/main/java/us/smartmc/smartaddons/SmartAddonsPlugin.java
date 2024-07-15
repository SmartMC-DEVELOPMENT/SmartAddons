package us.smartmc.smartaddons;

import us.smartmc.smartaddons.manager.CustomCommandManager;
import us.smartmc.smartaddons.plugin.AddonLoader;

import java.io.File;

public interface SmartAddonsPlugin {

    CustomCommandManager getCommandManager();

    AddonLoader getMainPluginLoader();
    File getMainEventPluginsDir();

}
