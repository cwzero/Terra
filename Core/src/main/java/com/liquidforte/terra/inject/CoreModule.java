package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.loader.GroupLoader;
import com.liquidforte.terra.loader.GroupLoaderImpl;

public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GroupLoader.class).to(GroupLoaderImpl.class);
    }
}
