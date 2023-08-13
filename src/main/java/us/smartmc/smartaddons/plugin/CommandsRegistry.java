package us.smartmc.smartaddons.plugin;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandsRegistry {

    private static final HashMap<String, List<AddonPluginCommand>> registry = new HashMap<>();
    private static final HashMap<String, AddonPluginCommand> commands = new HashMap<>();

    public static void register(String plugin, AddonPluginCommand executor) {
        List<AddonPluginCommand> list = getCommands(plugin);
        list.add(executor);
        registry.put(plugin, list);
        commands.put(executor.getName().toLowerCase(), executor);
    }

    public static void unregister(String plugin) {
        for (AddonPluginCommand command : getCommands(plugin)) {
            commands.remove(command.getName().toLowerCase());
        }
        registry.remove(plugin);
    }

    public static List<AddonPluginCommand> getCommands(String name) {
        return registry.getOrDefault(name, new ArrayList<>());
    }

    public static CommandExecutationState execute(CommandSender sender, String label) {
        String originalLabel = label;
        if (label.startsWith("/")) label = label.replaceFirst("/", "");
        String name = getName(label).toLowerCase();
        if (!commands.containsKey(name)) return CommandExecutationState.NOT_FOUND;
        label = label.replaceFirst(name, "");
        label = label.replaceFirst(name + " ", "");

        String[] args = getArgs(label);
        AddonPluginCommand commmand = commands.get(name);
        try {
            commmand.onCommand(sender, null, originalLabel, args);
            return CommandExecutationState.FOUND;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandExecutationState.ERROR_OCCURRED;
        }
    }

    private static String getName(String label) {
        return label.split(" ")[0];
    }

    private static String[] getArgs(String label) {
        return label.split(" ");
    }

}
