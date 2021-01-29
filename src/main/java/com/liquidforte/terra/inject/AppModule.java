package com.liquidforte.terra.inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.liquidforte.terra.app.App;
import com.liquidforte.terra.app.TerraApp;
import com.liquidforte.terra.config.AppConfig;
import com.liquidforte.terra.config.AppOptions;
import com.liquidforte.terra.config.AppOptionsFactory;
import com.liquidforte.terra.config.CacheConfig;

import java.io.File;
import java.io.IOException;

public class AppModule extends AbstractModule {
    private String[] args;

    public AppModule(String[] args) {
        this.args = args;
    }

    @Override
    protected void configure() {
        bind(App.class).to(TerraApp.class).in(Singleton.class);

        install(new FactoryModuleBuilder().implement(AppOptions.class, AppOptions.class).build(AppOptionsFactory.class));
    }

    @Provides
    @Singleton
    public AppOptions getAppOptions(AppOptionsFactory factory) {
        return factory.create(args);
    }

    @Provides
    @Singleton
    public AppConfig getAppConfig(ObjectMapper mapper, AppOptions options) {
        AppConfig appConfig = new AppConfig(options);
        File appConfigFile = new File(options.getAppConfigPath());

        if (appConfigFile.exists()) {
            try {
                mapper.readerForUpdating(appConfig).readValue(appConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return appConfig;
    }

    @Provides
    @Singleton
    public CacheConfig getCacheConfig(ObjectMapper mapper, AppOptions options) {
        CacheConfig cacheConfig = new CacheConfig();
        File cacheConfigFile = new File(options.getCacheConfigPath());

        if (cacheConfigFile.exists()) {
            try {
                mapper.readerForUpdating(cacheConfig).readValue(cacheConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cacheConfig;
    }
}
