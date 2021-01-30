package com.liquidforte.terra.inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.config.AppConfigImpl;
import com.liquidforte.terra.options.AppOptionsImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class AppModule extends AbstractModule {
    private final AppOptions appOptions;

    public AppModule(String... args) {
        appOptions = new AppOptionsImpl(args);
    }

    @Override
    protected void configure() {
        bind(AppOptions.class).toInstance(appOptions);
    }

    @Provides
    public AppConfig getAppConfig(ObjectMapper mapper, AppOptions appOptions) {
        Path appConfigPath = appOptions.getAppConfigPath();
        File appConfigFile = appConfigPath.toFile();
        File appConfigDir = appConfigFile.getParentFile();

        if (!appConfigDir.exists()) {
            appConfigDir.mkdirs();
        }

        if (!appConfigFile.exists()) {
            try {
                appConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        AppConfig result = new AppConfigImpl();

        try {
            mapper.readerForUpdating(result).readValue(appConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
