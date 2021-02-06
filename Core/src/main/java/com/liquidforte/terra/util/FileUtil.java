package com.liquidforte.terra.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static void rmDir(Path dir) {
        rmFiles(dir);

        try {
            Files.deleteIfExists(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rmFiles(Path dir) {
        try (Stream<Path> walk = Files.walk(dir)) {
            final List<Path> paths = walk
                    .sorted(Comparator.reverseOrder())
                    .filter(it -> !it.equals(dir))
                    .collect(Collectors.toList());
            for (Path path : paths) {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
