package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.loader.GroupLoader;
import com.liquidforte.terra.model.Group;
import com.liquidforte.terra.model.ModSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadCommand extends AbstractCommand {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadCommand.class);
    private final AppConfig appConfig;
    private final GroupLoader groupLoader;
    private final FileCache fileCache;
    private final LockCache lockCache;
    private final ModCache modCache;

    @Inject
    public DownloadCommand(CommandContext context, AppConfig appConfig, GroupLoader groupLoader, FileCache fileCache, LockCache lockCache, ModCache modCache) {
        super(context);
        this.appConfig = appConfig;
        this.groupLoader = groupLoader;
        this.lockCache = lockCache;
        this.fileCache = fileCache;
        this.modCache = modCache;
    }

    @Override
    public void run() {
        LOG.info("Download Command running!");

        for (Group group : groupLoader.loadGroups()) {
            LOG.info("Found group: " + group.getId());
            for (ModSpec spec : group.getMods()) {
                String slug = spec.getSlug();
                LOG.info("Resolving: " + slug);
                long addonId = modCache.getAddonId(slug);
                long fileId = lockCache.getLock(addonId);

                fileCache.getFileData(addonId, fileId);
            }
        }
    }
}
