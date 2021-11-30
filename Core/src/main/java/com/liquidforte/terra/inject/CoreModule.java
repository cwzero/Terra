package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.loader.GroupLoader;
import com.liquidforte.terra.api.options.AppPaths;
import com.liquidforte.terra.loader.GroupLoaderImpl;
import com.liquidforte.terra.options.AppPathsImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AppPaths.class).to(AppPathsImpl.class);
        bind(GroupLoader.class).to(GroupLoaderImpl.class);
        bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
    }
}
