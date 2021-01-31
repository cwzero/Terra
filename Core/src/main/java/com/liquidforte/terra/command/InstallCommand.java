package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;

public class InstallCommand extends LockCommand {
    @Inject
    public InstallCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        for (Group group : getGroupLoader().loadGroups()) {
            for (ModSpec spec : group.getMods()) {
                String slug = spec.getSlug();

                long addonId = getModCache().getAddonId(slug);
                long fileId = getLockCache().getLock(addonId);

                getFileCache().installFile(addonId, fileId);
            }
        }
    }
}
