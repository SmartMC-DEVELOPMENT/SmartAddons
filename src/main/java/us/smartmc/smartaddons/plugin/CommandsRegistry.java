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

        registry.put(plugin, list);

        List<String> aliases = new ArrayList<>(executor.getAliases());
        aliases.replaceAll(String::toLowerCase);
        executor.setAliases(aliases);

        list.add(executor);
        commands.put(executor.getName(), executor);
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
        label = label.replaceFirst(name, "");
        label = label.replaceFirst(name + " ", "");

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
        return label.split(" ")[0];
    }

    private static String[] getArgs(String label) {
        String[] args = new String[]{};
        if (label.contains(" ")) args = label.replaceFirst(getName(label) + " ", "").split(" ");
        return args;
    }

}
