package com.liquidforte.terra.command.server;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.LockCommand;
import com.liquidforte.terra.util.ForgeUtil;

import java.nio.file.Path;
import java.util.Set;

public class InstallForgeCommand extends LockCommand {
    @Inject
    public InstallForgeCommand(CommandContext context) {
        super(context);
        setDependencies("installJava");
    }

    @Override
    public Set<String> getAlias() {
        return Set.of("install-forge");
    }

    @Override
    protected void doRun() {
        String minecraftVersion = getAppConfig().getMinecraftVersion();
        String forgeVersion = getLockCache().getForgeLock();

        Path serverPath = getAppPaths().getServerPath();

        ForgeUtil.installForge(serverPath, minecraftVersion, forgeVersion);
    }

    @Override
    public String getDescription() {
        return "Install Minecraft Forge for the Local Server";
    }
}
