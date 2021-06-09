package com.liquidforte.terra.curse.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.ArchiveUtil;
import com.liquidforte.terra.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExportCommand extends AbstractCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ExportCommand.class);

    @Inject
    public ExportCommand(CommandContext context) {
        super(context);
        setDependencies("generateManifest");
    }

    @Override
    public String getDescription() {
        return "Generate curse compatible zip";
    }

    @Override
    protected void doRun() {
        Path cursePath = getAppPaths().getBuildPath().resolve("curse");
        Path overridesPath = cursePath.resolve("overrides");

        if (!Files.exists(overridesPath)) {
            try {
                Files.createDirectories(overridesPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path[] mcPaths = {
                getAppPaths().getMCDefaultConfigPath(),
                getAppPaths().getMCConfigPath(),
                getAppPaths().getMCModsPath(),
                getAppPaths().getMCResourcesPath(),
                getAppPaths().getMCSavesPath(),
                getAppPaths().getMCScriptsPath()
        };

        try {
            FileUtil.copyDir(mcPaths[0], overridesPath.resolve("defaultconfigs"));
            FileUtil.copyDir(mcPaths[1], overridesPath.resolve("config"));
            FileUtil.copyDir(mcPaths[3], overridesPath.resolve("resources"));
            FileUtil.copyDir(mcPaths[5], overridesPath.resolve("scripts"));

            String zipName = getAppConfig().getPackName() + "-" + getAppConfig().getPackVersion() + ".zip";
            Path zipPath = getAppPaths().getBuildPath().resolve(zipName);

            ArchiveUtil.zip(cursePath, zipPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
