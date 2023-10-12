package us.smartmc.smartaddons.type;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import us.smartmc.smartaddons.util.ChatUtil;

import java.util.UUID;

public class BungeeCordPlayer extends PluginPlayer<ProxiedPlayer> {

    public BungeeCordPlayer(ProxiedPlayer player) {
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
        return player.getUniqueId();
    }
}
