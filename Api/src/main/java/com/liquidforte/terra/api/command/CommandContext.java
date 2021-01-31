package com.liquidforte.terra.api.command;

import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.loader.GroupLoader;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.api.options.AppPaths;

public interface CommandContext {
    CommandParser getCommandParser();

    AppConfig getAppConfig();

    AppOptions getAppOptions();

    AppPaths getAppPaths();

    FileCache getFileCache();

    GroupLoader getGroupLoader();

    LockCache getLockCache();

    ModCache getModCache();
}
