package us.smartmc.smartaddons.test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import us.smartmc.smartaddons.type.PluginCommand;
import us.smartmc.smartaddons.type.PluginPlayer;
import us.smartmc.smartaddons.type.SpigotCommandSender;

public class SpigotRegistrationCommand extends Command {

    private final PluginCommand command;

    public SpigotRegistrationCommand(PluginCommand command) {
        super(command.getName());
        this.command = command;
        /*setAliases(command.getAliases());
        setPermissionMessage(command.getPermissionMessage());*/
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        args = commandLabel.replaceFirst(commandLabel.split(" ")[0] + " ", "").split(" ");
        command.onCommand(PluginPlayer.get(SpigotCommandSender.class, sender), args);
        return false;
    }
}
