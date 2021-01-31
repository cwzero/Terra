package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadCommand extends LockCommand {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadCommand.class);

    @Inject
    public DownloadCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        LOG.info("Download Command running!");

        for (Group group : getGroupLoader().loadGroups()) {
            LOG.info("Found group: " + group.getId());
            for (ModSpec spec : group.getMods()) {
                String slug = spec.getSlug();
                LOG.info("Resolving: " + slug);
                long addonId = getModCache().getAddonId(slug);
                long fileId = getLockCache().getLock(addonId);

                getFileCache().getFileData(addonId, fileId);
            }
        }
    }
}
