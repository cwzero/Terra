package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.cache.FileCache;
import com.liquidforte.terra.cache.LockCache;
import com.liquidforte.terra.cache.ModCache;
import com.liquidforte.terra.config.AppOptions;
import com.liquidforte.terra.model.Group;
import com.liquidforte.terra.model.GroupLoader;
import com.liquidforte.terra.model.ModSpec;

import java.io.IOException;

public class InstallCommand extends AbstractCommand {
    private final GroupLoader groupLoader;
    private final AppOptions appOptions;
    private final FileCache fileCache;
    private final LockCache lockCache;
    private final ModCache modCache;

    @Inject
    public InstallCommand(GroupLoader groupLoader, AppOptions appOptions, FileCache fileCache, LockCache lockCache, ModCache modCache) {
        this.groupLoader = groupLoader;
        this.appOptions = appOptions;
        this.fileCache = fileCache;
        this.lockCache = lockCache;
        this.modCache = modCache;
    }

    @Override
    public Object call() throws Exception {
        appOptions.getModsDir().toFile().mkdirs();

        for (Group group : groupLoader.loadGroups()) {
            for (ModSpec mod : group.getMods()) {
                long addonId = modCache.getAddonId(mod.getSlug());
                long fileId = lockCache.getLock(mod.getSlug(), mod.getFilter());

                try {
                    fileCache.installFile(appOptions.getModsDir(), addonId, fileId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Installed.");

        return null;
    }
}
