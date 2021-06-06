package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InstallCommand extends LockCommand {
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    @Inject
    public InstallCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
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
        try {
            while (!exec.awaitTermination(5, TimeUnit.MINUTES)) {

            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Installs mods into src/minecraft/mods";
    }
}
