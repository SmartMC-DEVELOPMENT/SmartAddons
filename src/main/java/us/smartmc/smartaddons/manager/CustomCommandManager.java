package us.smartmc.smartaddons.manager;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import us.smartmc.smartaddons.SmartAddonsPlugin;
import us.smartmc.smartaddons.bungee.SmartAddonsBungee;
import us.smartmc.smartaddons.command.LoadAddonCommand;
import us.smartmc.smartaddons.command.ReloadAddonCommand;
import us.smartmc.smartaddons.command.UnloadAddonCommand;
import us.smartmc.smartaddons.spigot.SmartAddonsSpigot;
import us.smartmc.smartaddons.test.SpigotRegistrationCommand;
import us.smartmc.smartaddons.type.*;
import us.smartmc.smartaddons.util.SpigotUtil;

import java.util.HashMap;

public class CustomCommandManager {

    private static SmartAddonsPlugin addonsPlugin;
    private final HashMap<String, PluginCommand> commands = new HashMap<>();

    public CustomCommandManager(SmartAddonsPlugin smartAddonsPlugin, String reloadName, String loadName, String unloadName) {
        addonsPlugin = smartAddonsPlugin;
        register(new ReloadAddonCommand(reloadName));
        register(new LoadAddonCommand(loadName));
        register(new UnloadAddonCommand(unloadName));
    }

    public static SmartAddonsPlugin getAddonsPlugin() {
        return addonsPlugin;
    }

    public void register(PluginCommand command) {
        commands.put(command.getName().toLowerCase(), command);

        if (addonsPlugin instanceof SmartAddonsBungee) {
            SmartAddonsBungee bungeePlugin = (SmartAddonsBungee) addonsPlugin;
            bungeePlugin.getProxy().getPluginManager().registerCommand(bungeePlugin,
                    new Command(command.getName()) {
                        @Override
                        public void execute(CommandSender sender, String[] args) {
                            command.onCommand(PluginPlayer.get(ProxyCommandSender.class, sender), args);
                        }
                    });
        }
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
