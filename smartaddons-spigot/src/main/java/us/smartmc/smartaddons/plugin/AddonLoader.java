package us.smartmc.smartaddons.plugin;

import java.io.File;
import java.util.HashMap;

import static us.smartmc.smartaddons.plugin.AddonClassLoader.pluginDirs;

public class AddonLoader {

    private final HashMap<String, AddonPlugin> plugins;

    public AddonLoader() {
        plugins = new HashMap<>();
    }

    public static void loadPlugins(String pluginsDirectory) {
        File directory = new File(pluginsDirectory);
        File[] files = directory.listFiles();
        if (files == null) return;
        AddonClassLoader.loadModulesJars(files);
    }

    public void unload(String name) {
        AddonPlugin plugin = plugins.get(name);
        if (plugin == null) return;
        plugin.stop();
        CommandsRegistry.unregister(name);
        ListenersRegistry.unregister(plugin);
        plugins.remove(name);
    }

    public void reloadPlugin(String name) {
        AddonPlugin plugin = plugins.get(name);
        if (plugin == null) {
            return;
        }
        File file = new File(pluginDirs.get(plugin));
        unload(name);
        AddonClassLoader.loadPluginJar(file, true);
    }

    public AddonPlugin get(String name) {
        return plugins.get(name);
    }

}
