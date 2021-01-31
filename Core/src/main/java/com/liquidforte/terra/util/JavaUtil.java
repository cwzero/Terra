package com.liquidforte.terra.util;

import java.io.IOException;
import java.nio.file.Path;

public class JavaUtil {
    private static final String JAVA_URL = "https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u282-b08/OpenJDK8U-jre_x64_windows_hotspot_8u282b08.zip";

    public static void downloadJava(Path destPath) {
        DownloadUtil.download(JAVA_URL, destPath.resolve("jre.zip"));
    }

    public static void extractJava(Path destPath) throws IOException {
        ArchiveUtil.unzip(destPath.resolve("jre.zip").toFile(), true);
    }

    public static void installJava(Path destPath) {
        downloadJava(destPath);
        try {
            extractJava(destPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
