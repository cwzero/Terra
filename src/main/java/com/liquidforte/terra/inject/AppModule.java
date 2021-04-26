package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.liquidforte.terra.api.application.Application;
import com.liquidforte.terra.api.application.ApplicationContext;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.application.ApplicationContextImpl;
import com.liquidforte.terra.application.ApplicationImpl;
import com.liquidforte.terra.options.AppOptionsImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppModule extends AbstractModule {
    private final AppOptions appOptions;

    public AppModule(String... args) {
        this.appOptions = new AppOptionsImpl(args);
    }

    @Override
    protected void configure() {
        bind(ExecutorService.class).toProvider(Executors::newCachedThreadPool).in(Singleton.class);
        bind(AppOptions.class).toInstance(appOptions);

        bind(Application.class).to(ApplicationImpl.class);
        bind(ApplicationContext.class).to(ApplicationContextImpl.class);
    }
}
