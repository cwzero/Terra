package com.liquidforte.terra.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.codec.digest.MurmurHash2;
import org.apache.commons.io.FileUtils;

public class FingerprintUtil {
    public static long getByteArrayHash(byte[] bytes) {
        byte[] normalizedArray = computeNormalizedArray(bytes);
        int hash = MurmurHash2.hash32(normalizedArray, normalizedArray.length, 1);
        return Integer.toUnsignedLong(hash);
    }

    public static long getFileHash(Path path) throws IOException {
        return getFileHash(path.toFile());
    }

    public static long getFileHash(File file) throws IOException {
        return getByteArrayHash(FileUtils.readFileToByteArray(file));
    }

    public static byte[] computeNormalizedArray(byte[] bytes) {
        byte[] result = new byte[bytes.length];

        int length = 0;
        for (int a = 0; a < bytes.length; a++) {
            if (!isWhitespaceCharacter(bytes[a])) {
                result[length] = bytes[a];
                length++;
            }
        }

        byte[] res = new byte[length];
        System.arraycopy(result, 0, res, 0, length);
        return res;
    }

    public static boolean isWhitespaceCharacter(byte b) {
        return b == 9 || b == 10 || b == 13 || b == 32;
    }
}
