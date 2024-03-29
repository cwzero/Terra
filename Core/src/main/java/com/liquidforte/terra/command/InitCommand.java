package com.liquidforte.terra.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
            String[] ignore = {
                    "/src/minecraft/mods/",
                    "/src/minecraft/saves/",
                    "/server/",
                    "/instance"
            };

            Arrays.stream(ignore).forEach(out::println);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!appConfigDir.exists()) {
            appConfigDir.mkdirs();
        }

        if (appConfigFile.exists()) {
            System.err.println("Tried to run init in a directory that already has src/terra/app.json");
        }
        
        try {
            String packName = Paths.get("").toAbsolutePath().getFileName().toString();
            Map<String, Object> config = new HashMap<>();
            config.put("packName", packName);
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

        for (String dir : dirs) {
            mcPath.resolve(dir).toFile().mkdirs();
        }
    }

    @Override
    public String getDescription() {
        return "Generate a new project";
    }
}
