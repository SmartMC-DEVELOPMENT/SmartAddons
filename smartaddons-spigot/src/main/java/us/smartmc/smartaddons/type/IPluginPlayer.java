package us.smartmc.smartaddons.type;

import java.util.UUID;

public interface IPluginPlayer<P> {

    void sendMessage(String message);

    UUID getUUID();
    P getPlayer();

    boolean hasPermission(String s);
}
