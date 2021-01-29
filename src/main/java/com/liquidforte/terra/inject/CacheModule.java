package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.liquidforte.terra.cache.*;

public class CacheModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FileCache.class).to(TerraFileCache.class).in(Singleton.class);
        bind(LockCache.class).to(TerraLockCache.class).asEagerSingleton();
        bind(ModCache.class).to(TerraModCache.class).in(Singleton.class);
    }
}
