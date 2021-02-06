package com.liquidforte.terra.api.options;

import java.nio.file.Path;

public interface AppPaths {
    Path getMMCPath();

    Path getMMCInstancePath();

    Path getBuildPath();

    Path getServerBuildPath();

    Path getSrcPath();

    Path getMCPath();

    Path getTerraPath();

    Path getGlobalPath();

    Path getLockPath();

    Path getAppConfigPath();

    Path getGroupsPath();

    Path getLocalCachePath();

    Path getServerPath();

    Path getCachePath();

    Path getMCConfigPath();

    Path getMCModsPath();

    Path getMCResourcesPath();

    Path getMCSavesPath();

    Path getMCScriptsPath();

    Path getMMCConfigPath();

    Path getMMCModsPath();

    Path getMMCResourcesPath();

    Path getMMCSavesPath();

    Path getMMCScriptsPath();

    Path getServerConfigPath();

    Path getServerModsPath();

    Path getServerResourcesPath();

    Path getServerSavesPath();

    Path getServerScriptsPath();

    Path getJavaPath();

    Path getJavaBinPath();
}
