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

public class InstallCommand extends AbstractCommand {
    private final AppConfig appConfig;
    private final GroupLoader groupLoader;
    private final FileCache fileCache;
    private final LockCache lockCache;
    private final ModCache modCache;

    @Inject
    public InstallCommand(CommandContext context, AppConfig appConfig, GroupLoader groupLoader, FileCache fileCache, LockCache lockCache, ModCache modCache) {
        super(context);
        this.appConfig = appConfig;
        this.groupLoader = groupLoader;
        this.fileCache = fileCache;
        this.lockCache = lockCache;
        this.modCache = modCache;
    }

    @Override
    public void run() {
        for (Group group : groupLoader.loadGroups()) {
            for (ModSpec spec : group.getMods()) {
                String slug = spec.getSlug();

                long addonId = modCache.getAddonId(slug);
                long fileId = lockCache.getLock(addonId);

                fileCache.installFile(addonId, fileId);
            }
        }
    }
}
