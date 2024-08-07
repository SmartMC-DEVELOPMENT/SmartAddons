package us.smartmc.smartaddons.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandsRegistry {

    private static final HashMap<String, List<BukkitCommand>> registry = new HashMap<>();
    private static final HashMap<String, BukkitCommand> commands = new HashMap<>();

    public static void register(String plugin, BukkitCommand executor) {
        List<BukkitCommand> list = getCommands(plugin);
        list.add(executor);
        registry.put(plugin, list);
        commands.put(executor.getName(), executor);

        for (String alias : executor.getAliases()) {
            commands.put(alias, executor);
        }

        try {
            getCommandMap().register(executor.getName(), executor);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while trying to register command: " + executor.getName());
        }
    }

    public static CommandMap getCommandMap() throws NoSuchFieldException, IllegalAccessException {
        Class<?> craftServerClass = Bukkit.getServer().getClass();
        Field commandMapField = craftServerClass.getDeclaredField("commandMap");
        commandMapField.setAccessible(true);
        return (CommandMap) commandMapField.get(Bukkit.getServer());
    }

    public static void unregister(String plugin) {
        for (BukkitCommand command : getCommands(plugin)) {
            commands.remove(command.getName());
            for (String alias : command.getAliases()) {
                commands.remove(alias);
            }
        }
        registry.remove(plugin);
    }

    public static List<BukkitCommand> getCommands(String name) {
        return registry.getOrDefault(name, new ArrayList<>());
    }

    public static CommandExecutationState execute(CommandSender sender, String label) {
        String originalLabel = label;
        if (label.startsWith("/")) label = label.replaceFirst("/", "");
        String name = getName(label);
        if (!commands.containsKey(name)) return CommandExecutationState.NOT_FOUND;

        String[] args = getArgs(label);
        BukkitCommand commmand = commands.get(name);
        try {
            commmand.execute(sender, originalLabel, args);
            return CommandExecutationState.FOUND;
        } catch (Exception e) {
            e.printStackTrace();
            return CommandExecutationState.ERROR_OCCURRED;
        }
    }

    private static String getName(String label) {
        String name = label;
        if (label.contains(" ")) name = label.split(" ")[0];
        if (name.startsWith("/")) name = new StringBuilder(name).deleteCharAt(0).toString();
        return name;
    }

    private static String[] getArgs(String label) {
        if (label.contains(" ")) label = label.replaceFirst(label.split(" ")[0], "");
        return label.split(" ");
    }

}
