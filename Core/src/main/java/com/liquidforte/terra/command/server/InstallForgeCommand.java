package com.liquidforte.terra.command.server;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.LockCommand;
import com.liquidforte.terra.util.ForgeUtil;

import java.nio.file.Path;

public class InstallForgeCommand extends LockCommand {
    @Inject
    public InstallForgeCommand(CommandContext context) {
        super(context);
        setDependencies("installJava");
    }

    @Override
    protected void doRun() {
        setDependencies("installJava");

        String minecraftVersion = getAppConfig().getMinecraftVersion();
        String forgeVersion = getLockCache().getForgeLock();

        Path serverPath = getAppPaths().getServerPath();

        ForgeUtil.installForge(serverPath, minecraftVersion, forgeVersion);
    }
}
