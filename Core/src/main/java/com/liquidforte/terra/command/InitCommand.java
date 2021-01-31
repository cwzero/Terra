package com.liquidforte.terra.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class InitCommand extends AbstractCommand {
    private final ObjectMapper mapper;

    @Inject
    public InitCommand(CommandContext context, ObjectMapper mapper) {
        super(context);
        this.mapper = mapper;
    }

    @Override
    public void doRun() {
        Path appConfigPath = getAppPaths().getAppConfigPath();
        Path groupsPath = getAppPaths().getGroupsPath();

        File appConfigFile = appConfigPath.toFile();
        File appConfigDir = appConfigFile.getParentFile();

        File gitIgnoreFile = new File(".gitignore");

        try {
            PrintStream out = new PrintStream(new FileOutputStream(gitIgnoreFile), true);
            out.println("/src/mods/");
            out.println("/src/saves/");
            out.println("/server/");
            out.println("/instance/");
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!appConfigDir.exists()) {
            appConfigDir.mkdirs();
        }

        if (!appConfigFile.exists()) {
            try {
                Map<String, Object> config = new HashMap<>();
                config.put("packName", "pack");
                config.put("packVersion", "0.0.1-SNAPSHOT");
                config.put("packAuthors", new String[0]);
                config.put("minecraftVersion", "1.12.2");
                config.put("alternateVersions", new String[]{
                        "1.12.1",
                        "1.12"
                });
                config.put("forgeVersion", "latest");
                config.put("minMemory", "6144");
                config.put("maxMemory", "8192");

                mapper.writeValue(appConfigFile, config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Error: app.json exists!");
        }

        File groupsDir = groupsPath.toFile();

        if (!groupsDir.exists()) {
            groupsDir.mkdirs();
        }

        Path mcPath = getAppPaths().getMCPath();

        String[] dirs = {
                "config",
                "mods",
                "resources",
                "saves",
                "scripts"
        };

        for (String dir: dirs) {
            mcPath.resolve(dir).toFile().mkdirs();
        }
    }
}
