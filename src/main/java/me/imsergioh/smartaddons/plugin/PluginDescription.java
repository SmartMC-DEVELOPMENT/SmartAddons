package me.imsergioh.smartaddons.plugin;

public class PluginDescription {

    private final String name;
    private final String mainClass;

    public PluginDescription(String name, String mainClass) {
        this.mainClass = mainClass;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMainClass() {
        return mainClass;
    }
}
