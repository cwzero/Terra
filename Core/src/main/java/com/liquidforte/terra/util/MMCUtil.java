package com.liquidforte.terra.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MMCUtil {
    private static final String MMC_FILE = "mmc-stable-win32.zip";
    private static final String MMC_URL = "https://files.multimc.org/downloads/mmc-stable-win32.zip";
    private static final String MMC_EXE = "MultiMC.exe";

    public static void downloadMMC(Path destPath) {
        // TODO: OS Dependent
        DownloadUtil.download(MMC_URL, destPath.resolve(MMC_FILE));
    }

    public static void extractMMC(Path destPath) throws IOException {
        Path mmcPath = destPath.resolve(MMC_FILE);

        ArchiveUtil.unzip(mmcPath.toFile(), true);

        try {
            Files.delete(mmcPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void installMMC(Path destPath) throws IOException {
        Path mmcPath = destPath.resolve(MMC_FILE);

        if (!Files.exists(mmcPath.resolve(MMC_EXE))) {
            if (!Files.exists(mmcPath)) {
                downloadMMC(destPath);
            }

            extractMMC(destPath);
        }
    }
}
