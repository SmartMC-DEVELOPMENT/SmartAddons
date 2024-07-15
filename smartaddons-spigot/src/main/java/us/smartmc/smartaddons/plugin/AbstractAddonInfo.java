package us.smartmc.smartaddons.plugin;

import lombok.Getter;

import java.io.File;
import java.util.List;

@Getter
public class AbstractAddonInfo {

    private final File file;
    private final String mainClass;

    private final List<String> dependencies;

    public AbstractAddonInfo(File file, String mainClass, List<String> dependencies) {
        this.file = file;
        this.mainClass = mainClass;
        this.dependencies = dependencies;
    }

}
