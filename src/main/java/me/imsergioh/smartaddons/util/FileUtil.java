package me.imsergioh.smartaddons.util;

import java.io.File;

public class FileUtil {

    public static File searchFileByName(File dir, String substring) {
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) return null;
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.contains(substring))
                    return file;
            }
        }
        return null;
    }

}
