package com.liquidforte.terra.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.api.service.FileService;
import com.liquidforte.terra.api.storage.LockStorage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class LockCacheImpl implements LockCache {
    private static final Logger LOG = LoggerFactory.getLogger(LockCacheImpl.class);

    private final AppConfig appConfig;
    private final AppOptions appOptions;
    private final ObjectMapper mapper;
    private final ModCache modCache;
    private final LockStorage lockStorage;
    private final FileService fileService;

    @Override
    public long getLock(String slug) {
        return getLock(modCache.getAddonId(slug));
    }

    @Override
    public long update(String slug) {
        return update(modCache.getAddonId(slug));
    }

    @Override
    public long getLock(long addonId) {
        long result = lockStorage.getLock(addonId);

        if (result <= 0) {
            result = update(addonId);
        }

        return result;
    }

    @Override
    public long update(long addonId) {
        String minecraftVersion = appConfig.getMinecraftVersion();
        String[] alternateVersions = appConfig.getAlternateVersions().toArray(new String[0]);
        long result = fileService.getLatestFile(minecraftVersion, alternateVersions, addonId);

        if (result > 0) {
            lockStorage.setLock(addonId, result);
        }

        fileService.getDependencies(addonId, result).forEach(dep -> {
            update(dep);
        });

        return result;
    }

    @Override
    public void load() {
        LOG.info("Loading lock cache");

        Path lockPath = appOptions.getLockPath();
        File lockFile = lockPath.toFile();
        File lockDir = lockFile.getParentFile();

        if (!lockDir.exists()) {
            lockDir.mkdirs();
        }

        if (lockFile.exists()) {
            try {
                PackLock packLock = mapper.readValue(lockFile, PackLock.class);

                if (packLock.getMinecraftVersion().contentEquals(appConfig.getMinecraftVersion())) {
                    for (long addonId : packLock.getLock().keySet()) {
                        long fileId = packLock.getLock().get(addonId);

                        lockStorage.setLock(addonId, fileId);
                    }
                } else {
                    lockFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save() {
        LOG.info("Saving lock cache");

        Path lockPath = appOptions.getLockPath();
        File lockFile = lockPath.toFile();
        File lockDir = lockFile.getParentFile();

        if (!lockDir.exists()) {
            lockDir.mkdirs();
        }

        if (!lockFile.exists()) {
            try {
                lockFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PackLock packLock = new PackLock();
        packLock.setMinecraftVersion(appConfig.getMinecraftVersion());
        Map<Long, Long> lock = lockStorage.getLocks();
        packLock.setLock(lock);

        try {
            mapper.writeValue(lockFile, packLock);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAll() {
        // TODO: Implement
        // TODO: Add Command

    }
}
