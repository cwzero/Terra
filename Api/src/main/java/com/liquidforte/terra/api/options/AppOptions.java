package com.liquidforte.terra.api.options;

import java.nio.file.Path;

public interface AppOptions {
    String[] getCommand();

    boolean isLocal();

    Path getAppConfigPath();

    Path getGroupsPath();
    Path getModsPath();

    Path getCachePath();

    Path getLocalCachePath();
}
