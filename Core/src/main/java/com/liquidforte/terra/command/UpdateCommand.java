package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateCommand extends LockCommand {
    private static final ExecutorService exec = Executors.newCachedThreadPool();
    @Inject
    public UpdateCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        getLockCache().updateForgeLock();

        for (Group group : getGroupLoader().loadGroups()) {
            for (ModSpec spec : group.getMods()) {
                exec.execute(() -> getLockCache().update(spec.getSlug()));
            }
        }

        exec.shutdown();
    }

    @Override
    public String getDescription() {
        return "Update versions for the enabled groups";
    }
}
