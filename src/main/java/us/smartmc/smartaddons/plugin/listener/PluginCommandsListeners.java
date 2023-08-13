package us.smartmc.smartaddons.plugin.listener;

import us.smartmc.smartaddons.plugin.CommandExecutationState;
import us.smartmc.smartaddons.plugin.CommandsRegistry;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class PluginCommandsListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreCommandPlayer(PlayerCommandPreprocessEvent event) {
        CommandExecutationState state = CommandsRegistry.execute(event.getPlayer(), event.getMessage());
        if (state.equals(CommandExecutationState.NOT_FOUND)) return;
        event.setCancelled(true);

        if (state.equals(CommandExecutationState.ERROR_OCCURRED)) {
            event.getPlayer().sendMessage(ChatColor.RED + "Error trying to execute this command!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConsoleCommand(ServerCommandEvent event) {
        CommandExecutationState state = CommandsRegistry.execute(event.getSender(), event.getCommand());
        if (state.equals(CommandExecutationState.NOT_FOUND)) return;
        event.setCancelled(true);

        if (state.equals(CommandExecutationState.ERROR_OCCURRED)) {
            event.getSender().sendMessage(ChatColor.RED + "Error trying to execute this command!");
        }
    }

}
