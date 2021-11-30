package com.liquidforte.terra.options;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.api.options.AppPaths;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AppPathsImpl implements AppPaths {
    private final Provider<AppConfig> appConfigProvider;
    private final AppOptions appOptions;

    private Path rootPath = Paths.get(".");
    private Path userDir = Paths.get(System.getProperty("user.home"));

    @Override
    public Path getMCDefaultConfigPath() {
        return getMCPath().resolve(appOptions.getDefaultConfigPath());
    }

    @Override
    public Path getBuildPath() {
        return rootPath.resolve(appOptions.getBuildPath());
    }

    @Override
    public Path getServerBuildPath() {
        return getBuildPath().resolve(appOptions.getServerPath());
    }

    @Override
    public Path getSrcPath() {
        return rootPath.resolve(appOptions.getSrcPath());
    }

    @Override
    public Path getMMCPath() {
        return getGlobalPath().resolve(appOptions.getMMCPath());
    }

    @Override
    public Path getMMCInstancePath() {
        return getMMCPath().resolve("instances").resolve(appConfigProvider.get().getPackName());
    }

    public Path getMMCMCPath() {
        return getMMCInstancePath().resolve(".minecraft");
    }

    @Override
    public Path getServerPath() {
        return rootPath.resolve(appOptions.getServerPath());
    }

    @Override
    public Path getMCPath() {
        return getSrcPath().resolve(appOptions.getMCPath());
    }

    @Override
    public Path getTerraPath() {
        return getSrcPath().resolve(appOptions.getTerraPath());
    }

    @Override
    public Path getGlobalPath() {
        return userDir.resolve(appOptions.getGlobalPath()
                .replace("~/", "")
                .replace("~\\", ""));
    }

    @Override
    public Path getLockPath() {
        return getTerraPath().resolve(appOptions.getLockPath());
    }

    @Override
    public Path getAppConfigPath() {
        return getTerraPath().resolve(appOptions.getAppConfigPath());
    }

    @Override
    public Path getGroupsPath() {
        return getTerraPath().resolve(appOptions.getGroupsPath());
    }

    @Override
    public Path getMCModsPath() {
        return getMCPath().resolve(appOptions.getModsPath());
    }

    @Override
    public Path getLocalCachePath() {
        return rootPath.resolve(appOptions.getLocalCachePath());
    }

    @Override
    public Path getCachePath() {
        if (appOptions.isLocal()) {
            return getLocalCachePath();
        } else {
            return userDir.resolve(appOptions.getCachePath().replace("~", "."));
        }
    }

    @Override
    public Path getMCConfigPath() {
        return getMCPath().resolve(appOptions.getConfigPath());
    }

    @Override
    public Path getMCResourcesPath() {
        return getMCPath().resolve(appOptions.getResourcesPath());
    }

    @Override
    public Path getMCSavesPath() {
        return getMCPath().resolve(appOptions.getSavesPath());
    }

    @Override
    public Path getMCScriptsPath() {
        return getMCPath().resolve(appOptions.getScriptsPath());
    }

    @Override
    public Path getMMCConfigPath() {
        return getMMCMCPath().resolve(appOptions.getConfigPath());
    }

    @Override
    public Path getMMCModsPath() {
        return getMMCMCPath().resolve(appOptions.getModsPath());
    }

    @Override
    public Path getMMCResourcesPath() {
        return getMMCMCPath().resolve(appOptions.getResourcesPath());
    }

    @Override
    public Path getMMCSavesPath() {
        return getMMCMCPath().resolve(appOptions.getSavesPath());
    }

    @Override
    public Path getMMCScriptsPath() {
        return getMMCMCPath().resolve(appOptions.getScriptsPath());
    }

    @Override
    public Path getMMCDefaultConfigPath() {
        return getMMCMCPath().resolve(appOptions.getDefaultConfigPath());
    }

    @Override
    public Path getServerConfigPath() {
        return getServerPath().resolve(appOptions.getConfigPath());
    }

    @Override
    public Path getServerModsPath() {
        return getServerPath().resolve(appOptions.getModsPath());
    }

    @Override
    public Path getServerResourcesPath() {
        return getServerPath().resolve(appOptions.getResourcesPath());
    }

    @Override
    public Path getServerSavesPath() {
        return getServerPath().resolve(appOptions.getSavesPath());
    }

    @Override
    public Path getServerScriptsPath() {
        return getServerPath().resolve(appOptions.getScriptsPath());
    }

    @Override
    public Path getServerDefaultConfigPath() {
        return getServerPath().resolve(appOptions.getDefaultConfigPath());
    }

    @Override
    public Path getJavaPath() {
        return getGlobalPath().resolve("java");
    }

    @Override
    public Path getJavaBinPath() {
        return getJavaPath().resolve("bin");
    }
}
