package me.imsergioh.smartaddons.plugin;

import org.bukkit.event.*;
import java.util.ArrayList;

public class ListenersHandler {

    private final AddonPlugin plugin;

    private final ArrayList<Listener> listeners;

    ListenersHandler(AddonPlugin plugin) {
        this.plugin = plugin;
        this.listeners = new ArrayList<>();
    }

    public void register(Listener listener) {
        listeners.add(listener);
    }

    public void unregister(Listener listener) {
        listeners.remove(listener);
        if (listener.getClass().getSuperclass().equals(AddonListener.class)) {
            AddonListener eventListener = (AddonListener) listener;
            eventListener.setEnabled(false);
        }
    }

    public ArrayList<Listener> getListeners() {
        return listeners;
    }

    public AddonPlugin getPlugin() {
        return plugin;
    }
}
