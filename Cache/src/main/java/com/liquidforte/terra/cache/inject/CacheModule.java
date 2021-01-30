package com.liquidforte.terra.cache.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.cache.FileCacheImpl;
import com.liquidforte.terra.cache.LockCacheImpl;
import com.liquidforte.terra.cache.ModCacheImpl;

public class CacheModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FileCache.class).to(FileCacheImpl.class);
        bind(LockCache.class).to(LockCacheImpl.class);
        bind(ModCache.class).to(ModCacheImpl.class);
    }
}
