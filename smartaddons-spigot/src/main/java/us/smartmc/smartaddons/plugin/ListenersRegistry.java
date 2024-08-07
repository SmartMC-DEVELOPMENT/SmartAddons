package us.smartmc.smartaddons.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class ListenersRegistry {

    private static final HashMap<AddonPlugin, ListenersHandler> listeners = new HashMap<>();

    public static void register(JavaPlugin javaPlugin, AddonPlugin plugin, Listener listener) {
        if (!listeners.containsKey(plugin)) {
            listeners.put(plugin, new ListenersHandler(plugin));
        }
        getListenersHandler(plugin).register(listener);
        Bukkit.getPluginManager().registerEvents(listener, javaPlugin);
    }

    public static void unregister(AddonPlugin plugin) {
        unregisterListeners(plugin);
    }

    private static void unregisterListeners(AddonPlugin plugin) {
        ListenersHandler handler = getListenersHandler(plugin);
        if (handler == null) return;
        for (int i = 0; i < handler.getListeners().size(); i++) {
            Listener listener = handler.getListeners().get(i);
            handler.unregister(listener);
        }
        listeners.remove(plugin);
    }

    public static ListenersHandler getListenersHandler(AddonPlugin plugin) {
        return listeners.get(plugin);
    }
}
