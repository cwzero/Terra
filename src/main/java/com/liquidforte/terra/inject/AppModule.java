package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.options.AppOptionsImpl;

public class AppModule extends AbstractModule {
    private final AppOptions appOptions;

    public AppModule(String... args) {
        appOptions = new AppOptionsImpl(args);
    }

    @Override
    protected void configure() {
        bind(AppOptions.class).toInstance(appOptions);
        bind(AppConfig.class).toProvider(AppConfigProvider.class);
    }
}
