package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({ @Inject }))
public class LockCacheImpl implements LockCache {
    private final ModCache modCache;

    @Override
    public long getLock(String slug) {
        return getLock(modCache.getAddonId(slug));
    }

    @Override
    public long update(String slug) {
        return update(modCache.getAddonId(slug));
    }

    @Override
    public long getFile(String slug) {
        return getFile(modCache.getAddonId(slug));
    }

    @Override
    public long getLock(long addonId) {
        // TODO: Implement
        return 0;
    }

    @Override
    public long update(long addonId) {
        // TODO: Implement
        return 0;
    }

    @Override
    public long getFile(long addonId) {
        // TODO: Implement
        return 0;
    }

    @Override
    public void load() {
        // TODO: Implement
        // TODO: Call on start

    }

    @Override
    public void save() {
        // TODO: Implement
        // TODO: call on exit

    }

    @Override
    public void updateAll() {
        // TODO: Implement
        // TODO: Add Command

    }
}
