package com.liquidforte.terra.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LinkUtil {
    public static void link(Path source, Path dest) throws IOException {
        Path parent = dest.getParent();

        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        if (Files.exists(dest) && !Files.isSymbolicLink(dest)) {
            Files.delete(dest);
        }

        if (!Files.exists(dest) || !Files.isSymbolicLink(dest)) {
            Files.createSymbolicLink(dest.toAbsolutePath(), source.toAbsolutePath());
        }
    }
}
