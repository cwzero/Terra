package com.liquidforte.terra.api.options;

public interface AppOptions {
    String[] getCommand();

    boolean isLocal();

    String getServerPath();

    String getGlobalPath();

    String getMMCPath();

    String getSrcPath();

    String getBuildPath();

    String getMCPath();

    String getTerraPath();

    String getLockPath();

    String getAppConfigPath();

    String getGroupsPath();

    String getModsPath();

    String getConfigPath();

    String getResourcesPath();

    String getSavesPath();

    String getScriptsPath();

    String getLocalCachePath();

    String getCachePath();
}
