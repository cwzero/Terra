package com.liquidforte.terra.inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.options.AppPaths;
import com.liquidforte.terra.config.AppConfigImpl;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AppConfigProvider implements Provider<AppConfig> {
    private final ObjectMapper mapper;
    private final AppPaths appPaths;

    @Override
    public AppConfig get() {
        Path appConfigPath = appPaths.getAppConfigPath();
        File appConfigFile = appConfigPath.toFile();
        File appConfigDir = appConfigFile.getParentFile();

        if (!appConfigDir.exists()) {
            appConfigDir.mkdirs();
        }

        if (!appConfigFile.exists()) {
            return new AppConfigImpl();
        } else {
            AppConfig result = new AppConfigImpl();

            try {
                mapper.readerForUpdating(result).readValue(appConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}
