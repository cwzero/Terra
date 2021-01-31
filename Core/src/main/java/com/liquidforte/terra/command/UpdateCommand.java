package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;

public class UpdateCommand extends LockCommand {
    @Inject
    public UpdateCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        getLockCache().updateForgeLock();

        for (Group group : getGroupLoader().loadGroups()) {
            for (ModSpec spec : group.getMods()) {
                getLockCache().update(spec.getSlug());
            }
        }
    }
}
