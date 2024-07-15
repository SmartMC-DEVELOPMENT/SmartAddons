package us.smartmc.smartaddons.manager;

import lombok.Getter;
import us.smartmc.smartaddons.SmartAddonsPlugin;
import us.smartmc.smartaddons.command.LoadAddonCommand;
import us.smartmc.smartaddons.command.ReloadAddonCommand;
import us.smartmc.smartaddons.command.UnloadAddonCommand;
import us.smartmc.smartaddons.type.IPluginPlayer;
import us.smartmc.smartaddons.type.PluginCommand;

import java.util.HashMap;

public class CustomCommandManager {

    @Getter
    private static SmartAddonsPlugin addonsPlugin;
    private final HashMap<String, PluginCommand> commands = new HashMap<>();

    public CustomCommandManager(SmartAddonsPlugin smartAddonsPlugin, String reloadName, String loadName, String unloadName) {
        addonsPlugin = smartAddonsPlugin;
        register(new ReloadAddonCommand(reloadName));
        register(new LoadAddonCommand(loadName));
        register(new UnloadAddonCommand(unloadName));
    }

    public void register(PluginCommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    public void unregister(String name) {
        commands.remove(name.toLowerCase());
    }

    public PluginCommand get(String name) {
        return commands.get(name.toLowerCase());
    }

    public boolean executeCommand(IPluginPlayer<?> player, String label) {
        label = label.replaceFirst("/", "");
        String name = label.split(" ")[0];
        if (!commands.containsKey(name.toLowerCase())) return false;
        String[] args = label.replaceFirst(name + " ", "").split(" ");
        commands.get(name.toLowerCase()).onCommand(player, args);
        return true;
    }

}
