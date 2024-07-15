package us.smartmc.smartaddons.type;

public interface IPluginCommand {

    void onCommand(IPluginPlayer<?> player, String[] args);

    String getPermissionMessage();
    CommandPermissionNode getPermissionNode();

}
