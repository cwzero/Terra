package com.liquidforte.terra.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.StringJoiner;

public class ExecUtil {
    public static String join(String... args) {
        StringJoiner joiner = new StringJoiner(" ");

        Arrays.stream(args).forEach(joiner::add);

        return joiner.toString();
    }

    public static void execJar(Path jar, String... args) {
        String command = "java -jar " + jar.toAbsolutePath().toString();
        exec(jar.getParent().toFile(), command, args);
    }

    public static void exec(File workingDir, String command, String... args) {
        exec(workingDir, command, join(args));
    }

    public static void exec(File workingDir, String command, String args) {
        exec(workingDir, command + " " + args);
    }

    public static void exec(File workingDir, String command) {
        try {
            Process process = Runtime.getRuntime().exec(command, new String[0], workingDir);
            java.io.InputStream is = process.getInputStream();
            byte b[] = new byte[is.available()];
            is.read(b, 0, b.length);
            System.out.println(new String(b));
            is.close();

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
