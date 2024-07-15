package us.smartmc.smartaddons.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class PluginCommand implements IPluginCommand {

    private final String name;
    private final CommandPermissionNode permissionNode;
    private final List<String> aliases = new ArrayList<>();

    public PluginCommand(String name, CommandPermissionNode permissionNode, String... aliases) {
        this.name = name;
        this.permissionNode = permissionNode;
        this.aliases.addAll(Arrays.asList(aliases));
    }

    @Override
    public CommandPermissionNode getPermissionNode() {
        return permissionNode;
    }

    @Override
    public String getPermissionMessage() {
        return "&cYou don't have permission to execute this command.";
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
