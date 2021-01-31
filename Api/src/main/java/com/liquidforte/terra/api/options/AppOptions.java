package com.liquidforte.terra.api.options;

import java.nio.file.Path;

public interface AppOptions {
    String[] getCommand();

    boolean isLocal();

    String getLockPathString();

    Path getLockPath();

    Path getAppConfigPath();

    Path getGroupsPath();
    Path getModsPath();

    String getLocalCachePathString();

    String getCachePathString();

    Path getCachePath();

    Path getLocalCachePath();
}
