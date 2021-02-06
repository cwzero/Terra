package com.liquidforte.terra.command.mmc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GenerateMMCFilesCommand extends AbstractCommand {
    private final ObjectMapper mapper;

    @Inject
    public GenerateMMCFilesCommand(CommandContext context, ObjectMapper mapper) {
        super(context);
        this.mapper = mapper;
        setDependencies("installJava");
    }

    @Override
    public Set<String> getAlias() {
        return Set.of("generate-mmc");
    }

    private void generateInstanceCfg() {
        Path instanceCfgPath = getAppPaths().getMMCInstancePath().resolve("instance.cfg");

        try {
            Files.write(instanceCfgPath, Arrays.asList(
                    "InstanceType=OneSix",
                    "name=" + getAppConfig().getPackName(),
                    "iconKey=default",
                    "MinMemAlloc=" + getAppConfig().getMinMemory(),
                    "MaxMemAlloc=" + getAppConfig().getMaxMemory(),
                    "OverrideMemory=true"
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generatePackMmc() {
        Path packmmcPath = getAppPaths().getMMCInstancePath().resolve("mmc-pack.json");

        try {
            PackMmc packMmc = new PackMmc();

            packMmc.getComponents().add(new PackMmcComponent(true, "net.minecraft", getAppConfig().getMinecraftVersion()));
            packMmc.setFormatVersion(1);
            String forgeVersion = getLockCache().getForgeLock();

            packMmc.getComponents().add(new PackMmcComponent(false, "net.minecraftforge", forgeVersion));

            mapper.writeValue(packmmcPath.toFile(), packMmc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateMultimcCfg() {
        Path multimcCfgPath = getAppPaths().getMMCPath().resolve("multimc.cfg");

        try {
            Files.write(multimcCfgPath, Arrays.asList(
                    "Analytics=true",
                    "AnalyticsSeen=2",
                    "AnalyticsClientId=" + UUID.randomUUID(),
                "JavaPath=" + getAppPaths().getJavaBinPath().resolve("javaw.exe").toAbsolutePath().toString().replace("\\", "/"),
                    "Language=en",
                    "LastHostname=" + InetAddress.getLocalHost().getHostName()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doRun() {

        generateMultimcCfg();
        generateInstanceCfg();
        generatePackMmc();

        /*
        try {
            Files.createFile(getAppPaths().getMMCPath().resolve("qt.conf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class PackMmc {
        private List<PackMmcComponent> components = new ArrayList<>();
        private int formatVersion = -1;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class PackMmcComponent {
        private boolean important = false;
        private String uid = "";
        private String version = "";
    }

    @Override
    public String getDescription() {
        return "Generate the files used to run mmc and its instance";
    }
}
