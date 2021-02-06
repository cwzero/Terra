package com.liquidforte.terra.command.server;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.LinkUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class LinkServerCommand extends AbstractCommand {
    @Inject
    public LinkServerCommand(CommandContext context) {
        super(context);
    }

    @Override
    public Set<String> getAlias() {
        return Set.of("link-server");
    }

    @Override
    public void doRun() {
        Path[] mcPaths = {
                getAppPaths().getMCConfigPath(),
                getAppPaths().getMCModsPath(),
                getAppPaths().getMCResourcesPath(),
                getAppPaths().getMCSavesPath(),
                getAppPaths().getMCScriptsPath()
        };

        Path[] serverPaths = {
                getAppPaths().getServerConfigPath(),
                getAppPaths().getServerModsPath(),
                getAppPaths().getServerResourcesPath(),
                getAppPaths().getServerSavesPath(),
                getAppPaths().getServerScriptsPath()
        };

        for (int i = 0; i < mcPaths.length; i++) {
            Path mcPath = mcPaths[i];
            Path serverPath = serverPaths[i];

            File mcDir = mcPath.toFile();

            if (!mcDir.exists()) {
                mcDir.mkdirs();
            }

            try {
                LinkUtil.link(mcPath, serverPath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
