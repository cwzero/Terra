package com.liquidforte.terra.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ForgeUtil {
    private static final String FORGE_ROOT = "https://files.minecraftforge.net/maven";

    public static void downloadForge(Path destPath, String minecraftVersion, String forgeVersion) {
        String fullVersion = minecraftVersion + "-" + forgeVersion;
        String fileName = "forge-" + fullVersion + "-installer.jar";
        String forgeUrl = FORGE_ROOT + "/net/minecraftforge/forge/" + fullVersion + "/" + fileName;

        DownloadUtil.download(forgeUrl, destPath.resolve(fileName));
    }

    public static void installForge(Path destPath, String minecraftVersion, String forgeVersion) {
        String fullVersion = minecraftVersion + "-" + forgeVersion;
        String installerJarName = "forge-" + fullVersion + "-installer.jar";
        String forgeJarName = "forge-" + fullVersion + ".jar";

        Path installerJarPath = destPath.resolve(installerJarName);
        Path forgeJarPath = destPath.resolve(forgeJarName);
        if (!Files.exists(forgeJarPath)) {
            if (!Files.exists(installerJarPath)) {
                downloadForge(destPath, minecraftVersion, forgeVersion);
            }
            ExecUtil.execJar(installerJarPath, "--installServer");
        }

        try {
            Files.deleteIfExists(installerJarPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.list(destPath).filter(path -> path.getFileName().toString().contains("installer") && path.getFileName().toString().endsWith(".log")).forEach(p -> {
                try {
                    Files.deleteIfExists(p);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
