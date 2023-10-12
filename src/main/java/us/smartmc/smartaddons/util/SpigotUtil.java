package us.smartmc.smartaddons.util;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import javax.activation.CommandMap;
import java.lang.reflect.Field;

public class SpigotUtil {

    public static SimpleCommandMap getCommandMap() {
        SimpleCommandMap commandMap = null;

        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (SimpleCommandMap) f.get(Bukkit.getPluginManager());
                return commandMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
