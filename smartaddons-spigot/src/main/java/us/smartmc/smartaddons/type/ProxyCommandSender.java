package us.smartmc.smartaddons.type;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import us.smartmc.smartaddons.util.ChatUtil;

import java.util.UUID;

public class ProxyCommandSender extends PluginPlayer<CommandSender> {

    public ProxyCommandSender(CommandSender player) {
        super(player);
    }

    @Override
    public boolean hasPermission(String s) {
        return player.hasPermission(s);
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(new ComponentBuilder(ChatUtil.color(message)).create());
    }

    @Override
    public UUID getUUID() {
        return null;
    }
}
