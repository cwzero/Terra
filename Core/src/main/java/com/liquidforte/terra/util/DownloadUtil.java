package com.liquidforte.terra.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadUtil {
    public static void download(String url, Path destPath) {
        try {
            Files.createDirectories(destPath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        download(url, destPath.toFile());
    }

    public static void download(String url, File destFile) {
        if (!destFile.exists()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try {
                URLConnection conn = new URL(url).openConnection();
                InputStream input = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(destFile);

                ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                int len;
                while ((len = input.read(byteBuffer.array())) > 0) {
                    fos.write(byteBuffer.array(), 0, len);
                    byteBuffer.clear();
                }

                fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
