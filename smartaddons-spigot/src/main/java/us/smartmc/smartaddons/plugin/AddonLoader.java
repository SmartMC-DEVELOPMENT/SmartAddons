package us.smartmc.smartaddons.plugin;

import java.io.File;
import java.util.HashSet;

import static us.smartmc.smartaddons.plugin.AddonClassLoader.pluginsJars;

public class AddonLoader {

    public static void unloadAll() {
        for (String name : new HashSet<>(AddonClassLoader.plugins.keySet())) {
            unload(name);
        }
    }

    public static void loadPlugins(String pluginsDirectory) {
        File directory = new File(pluginsDirectory);
        File[] files = directory.listFiles();
        if (files == null) return;
        AddonClassLoader.loadModulesJars(files);
    }

    public static void unload(String name) {
        AddonPlugin plugin = AddonClassLoader.plugins.get(name);
        if (plugin == null) return;
        plugin.stop();
        CommandsRegistry.unregister(name);
        ListenersRegistry.unregister(plugin);
        AddonClassLoader.unregisterAddon(name);
    }

    public void reloadPlugin(String name) {
        AddonPlugin plugin = AddonClassLoader.plugins.get(name);
        if (plugin == null) {
            return;
        }
        File file = pluginsJars.get(name);
        unload(name);
        AddonClassLoader.loadPluginJar(file, true);
    }

    public AddonPlugin get(String name) {
        return AddonClassLoader.plugins.get(name);
    }

}
