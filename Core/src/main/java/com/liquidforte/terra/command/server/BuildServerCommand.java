package com.liquidforte.terra.command.server;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.ArchiveUtil;
import com.liquidforte.terra.util.ForgeUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class BuildServerCommand extends AbstractCommand {
    @Inject
    public BuildServerCommand(CommandContext context) {
        super(context);
    }

    @Override
    public Set<String> getAlias() {
        return Set.of("build-server");
    }

    @Override
    protected void doRun() {
        setDependencies("installJava");

        String minecraftVersion = getAppConfig().getMinecraftVersion();
        String forgeVersion = getLockCache().getForgeLock();

        Path[] mcPaths = {
                getAppPaths().getMCDefaultConfigPath(),
                getAppPaths().getMCConfigPath(),
                getAppPaths().getMCModsPath(),
                getAppPaths().getMCResourcesPath(),
                getAppPaths().getMCSavesPath(),
                getAppPaths().getMCScriptsPath()
        };

        try {
            Path buildDir = getAppPaths().getServerBuildPath();
            Files.createDirectories(buildDir);

            ForgeUtil.installForge(buildDir, minecraftVersion, forgeVersion);

            Files.copy(mcPaths[0], buildDir.resolve("defaultconfigs"));
            Files.copy(mcPaths[1], buildDir.resolve("config"));
            Files.copy(mcPaths[2], buildDir.resolve("mods"));
            Files.copy(mcPaths[3], buildDir.resolve("resources"));
            Files.copy(mcPaths[5], buildDir.resolve("scripts"));

            String zipName = getAppConfig().getPackName() + "_server-" + getAppConfig().getPackVersion() + ".zip";
            Path zipPath = buildDir.getParent().resolve(zipName);

            ArchiveUtil.zip(buildDir, zipPath);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Builds a server zip";
    }
}
