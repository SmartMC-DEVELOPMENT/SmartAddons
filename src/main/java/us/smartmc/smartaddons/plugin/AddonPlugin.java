package us.smartmc.smartaddons.plugin;

import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;

import java.io.File;

public abstract class AddonPlugin implements IAddonPlugin {

    private AddonInfo info = null;
    private File dataFolder = null;

    public AddonPlugin() {
        info = getClass().getDeclaredAnnotation(AddonInfo.class);
    }

    @Override
    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            if (listener == null) continue;
            ListenersRegistry.register(this, listener);
        }
    }

    @Override
    public void registerCommand(BukkitCommand... executors) {
        for (BukkitCommand executor : executors) {
            CommandsRegistry.register(getInfo().name(), executor);
        }
    }

    @Override
    public void reload() {
        stop();
        start();
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public AddonInfo getInfo() {
        return info;
    }

}
