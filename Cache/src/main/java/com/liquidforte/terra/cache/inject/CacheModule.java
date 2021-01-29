package com.liquidforte.terra.cache.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.cache.Cache;
import com.liquidforte.terra.cache.TerraCacheImpl;

public class CacheModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Cache.class).to(TerraCacheImpl.class);
    }
}
