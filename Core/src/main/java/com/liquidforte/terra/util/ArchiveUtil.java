package com.liquidforte.terra.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ArchiveUtil {
    public static void unzip(File zip, boolean strip) throws IOException {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip.getAbsoluteFile()));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            File newFile = newFile(zip.getParentFile().getAbsoluteFile(), zipEntry, strip);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                while ((len = zis.read(byteBuffer.array())) > 0) {
                    fos.write(byteBuffer.array(), 0, len);
                    byteBuffer.clear();
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();

        zip.deleteOnExit();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry, boolean strip) throws IOException {
        File destFile;
        String name = zipEntry.getName();
        if (strip) {
            destFile = new File(destinationDir, name.substring(name.indexOf("/")));
        } else {
            destFile = new File(destinationDir, zipEntry.getName());
        }

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
