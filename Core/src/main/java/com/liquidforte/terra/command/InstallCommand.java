package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;
import com.liquidforte.terra.command.mmc.RunMMCCommand;
import com.liquidforte.terra.command.mmc.RunMMCInstanceCommand;
import com.liquidforte.terra.command.server.RunServerCommand;

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

    @Override
    public String getDescription() {
        return "Installs mods into src/minecraft/mods";
    }
}
