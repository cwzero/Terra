package com.liquidforte.terra.command.mmc;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.LinkUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class LinkMMCCommand extends AbstractCommand {
    @Inject
    public LinkMMCCommand(CommandContext context) {
        super(context);
    }

    @Override
    public Set<String> getAlias() {
        return Set.of("link-mmc");
    }

    @Override
    public void doRun() {
        Path[] mcPaths = {
                getAppPaths().getMCDefaultConfigPath(),
                getAppPaths().getMCConfigPath(),
                getAppPaths().getMCModsPath(),
                getAppPaths().getMCResourcesPath(),
                getAppPaths().getMCSavesPath(),
                getAppPaths().getMCScriptsPath()
        };

        Path[] mmcPaths = {
                getAppPaths().getMMCDefaultConfigPath(),
                getAppPaths().getMMCConfigPath(),
                getAppPaths().getMMCModsPath(),
                getAppPaths().getMMCResourcesPath(),
                getAppPaths().getMMCSavesPath(),
                getAppPaths().getMMCScriptsPath()
        };

        for (int i = 0; i < mcPaths.length; i++) {
            Path mcPath = mcPaths[i];
            Path mmcPath = mmcPaths[i];

            File mcDir = mcPath.toFile();

            if (!mcDir.exists()) {
                mcDir.mkdirs();
            }

            try {
                LinkUtil.link(mcPath, mmcPath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try {
            LinkUtil.link(getAppPaths().getMMCInstancePath(), Paths.get("instance"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Link the directories for the MMC instance";
    }
}
