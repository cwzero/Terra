package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.curse.client.Client;
import com.liquidforte.terra.config.AppConfig;
import com.liquidforte.terra.database.LockDatabase;

public class TerraLockCache extends AbstractCache implements LockCache {
    private final AppConfig appConfig;
    private final Client client;
    private final ModCache modCache;
    private final FileCache fileCache;
    private final LockDatabase lockDatabase;

    @Inject
    public TerraLockCache(AppConfig appConfig, Client client, ModCache modCache, FileCache fileCache, LockDatabase lockDatabase) {
        this.appConfig = appConfig;
        this.client = client;
        this.modCache = modCache;
        this.fileCache = fileCache;
        this.lockDatabase = lockDatabase;
    }

    @Override
    public long getLock(String slug, String filter) {
        long addonId = modCache.getAddonId(slug);

        if (addonId <= 0) {
            throw new RuntimeException("Couldn't find addon id for slug: " + slug);
        } else {
            return getLock(addonId, filter);
        }
    }

    @Override
    public long getLock(long addonId, String filter) {
        long fileId = lockDatabase.getLock(addonId, filter);

        if (fileId <= 0) {
            var latestFile = client.getLatestFile(appConfig.getMinecraftVersion(), addonId, filter);

            if (latestFile.isPresent()) {
                fileId = latestFile.get().getId();

                if (addonId > 0 && fileId > 0) {
                    lockDatabase.lock(addonId, filter, fileId);
                }
            }
        }

        if (fileId <= 0) {
            throw new RuntimeException("Couldn't find file id or addon id (addonId:fileId) " + addonId + ":" + fileId);
        }

        return fileId;
    }
}
