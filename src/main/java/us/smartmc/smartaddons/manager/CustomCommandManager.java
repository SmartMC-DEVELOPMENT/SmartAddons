package us.smartmc.smartaddons.manager;

import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import us.smartmc.smartaddons.SmartAddonsPlugin;
import us.smartmc.smartaddons.bungee.SmartAddonsBungee;
import us.smartmc.smartaddons.command.LoadAddonCommand;
import us.smartmc.smartaddons.command.ReloadAddonCommand;
import us.smartmc.smartaddons.command.UnloadAddonCommand;
import us.smartmc.smartaddons.type.IPluginPlayer;
import us.smartmc.smartaddons.type.PluginCommand;
import us.smartmc.smartaddons.type.PluginPlayer;
import us.smartmc.smartaddons.type.ProxyCommandSender;

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

        if (addonsPlugin instanceof SmartAddonsBungee bungeePlugin) {
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
        if (label.startsWith("/"))
            label = label.replaceFirst("/", "");
        String name = label.contains(" ") ? label.split(" ")[0] : label;

        PluginCommand targetCommand = null;

        for (PluginCommand pluginCommand : commands.values()) {
            if (pluginCommand.getName().equalsIgnoreCase(name)) {
                targetCommand = pluginCommand;
                break;
            }
            for (String alias : pluginCommand.getAliases()) {
                if (alias.equalsIgnoreCase(name)) {
                    targetCommand = pluginCommand;
                    break;
                }
            }
        }
        if (targetCommand == null) return false;

        String[] args = label.contains(" ") ? label.replaceFirst(name + " ", "").split(" ") : new String[]{};

        targetCommand.onCommand(player, args);
        return true;
    }

}
