package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.cache.FileCache;
import com.liquidforte.terra.cache.LockCache;
import com.liquidforte.terra.cache.ModCache;
import com.liquidforte.terra.model.Group;
import com.liquidforte.terra.model.GroupLoader;
import com.liquidforte.terra.model.ModSpec;

import java.io.IOException;

public class DownloadCommand extends AbstractCommand {
    private final GroupLoader groupLoader;
    private final LockCache lockCache;
    private final ModCache modCache;
    private final FileCache fileCache;

    @Inject
    public DownloadCommand(GroupLoader groupLoader, LockCache lockCache, ModCache modCache, FileCache fileCache) {
        this.groupLoader = groupLoader;
        this.lockCache = lockCache;
        this.modCache = modCache;
        this.fileCache = fileCache;
    }

    @Override
    public Object call() throws Exception {
        for (Group group : groupLoader.loadGroups()) {
            for (ModSpec spec : group.getMods()) {
                long addonId = modCache.getAddonId(spec.getSlug());
                long fileId = lockCache.getLock(spec.getSlug(), spec.getFilter());
                try {
                    fileCache.downloadFileToCache(addonId, fileId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
