package com.liquidforte.terra.command.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.command.CommandParser;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.loader.GroupLoader;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.api.options.AppPaths;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class CommandContextImpl implements CommandContext {
    private final Provider<CommandParser> commandParserProvider;
    private final Provider<AppConfig> appConfigProvider;
    private final AppOptions appOptions;
    private final AppPaths appPaths;
    private final Provider<GroupLoader> groupLoaderProvider;
    private final Provider<FileCache> fileCacheProvider;
    private final Provider<LockCache> lockCacheProvider;
    private final Provider<ModCache> modCacheProvider;

    @Override
    public CommandParser getCommandParser() {
        return commandParserProvider.get();
    }

    @Override
    public AppConfig getAppConfig() {
        return appConfigProvider.get();
    }

    @Override
    public FileCache getFileCache() {
        return fileCacheProvider.get();
    }

    @Override
    public GroupLoader getGroupLoader() {
        return groupLoaderProvider.get();
    }

    @Override
    public LockCache getLockCache() {
        return lockCacheProvider.get();
    }

    @Override
    public ModCache getModCache() {
        return modCacheProvider.get();
    }
}
