package us.smartmc.smartaddons.type;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.smartmc.smartaddons.util.ChatUtil;

import java.util.UUID;

public class SpigotCommandSender extends PluginPlayer<CommandSender> {
    public SpigotCommandSender(CommandSender player) {
        super(player);
    }

    @Override
    public boolean hasPermission(String s) {
        return player.hasPermission(s);
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(ChatUtil.color(message));
    }

    @Override
    public UUID getUUID() {
        if (player instanceof Player) {
            return ((Player) player).getUniqueId();
        }
        return null;
    }
}
