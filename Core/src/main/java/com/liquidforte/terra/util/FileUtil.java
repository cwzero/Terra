package com.liquidforte.terra.util;

import java.io.File;

public class FileUtil {
    public static String getRelativePath(File dir, File file) {
        String dirPath = dir.getPath();
        String filePath = file.getPath();

        if (filePath.contains(".")) {
            filePath = filePath.substring(0, filePath.indexOf("."));
        }

        if (filePath.contains(dirPath)) {
            filePath = filePath.substring(filePath.indexOf(dirPath) + dirPath.length());
        }

        filePath = filePath.replace("\\", "/").replace("/", ".");
        if (filePath.startsWith(".")) {
            filePath = filePath.substring(1);
        }

        return filePath;
    }
}
