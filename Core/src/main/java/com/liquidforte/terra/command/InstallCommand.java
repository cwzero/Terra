package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InstallCommand extends LockCommand {
    @Inject
    public InstallCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        ExecutorService exec = getExecutorService();

        for (Group group : getGroupLoader().loadGroups()) {
            for (ModSpec spec : group.getMods()) {
                exec.execute(() -> {
                    String slug = spec.getSlug();

                    long addonId = getModCache().getAddonId(slug);
                    long fileId = getLockCache().getLock(addonId);

                    getFileCache().installFile(addonId, fileId);
                });
            }
        }

        exec.shutdown();
    }

    @Override
    public String getDescription() {
        return "Installs mods into src/minecraft/mods";
    }
}
