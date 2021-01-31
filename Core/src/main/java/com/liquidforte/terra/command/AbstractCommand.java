package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.loader.GroupLoader;
import com.liquidforte.terra.api.options.AppOptions;

public abstract class AbstractCommand implements Command, CommandContext {
    private final CommandContext context;

    protected AbstractCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public AppOptions getAppOptions() {
        return context.getAppOptions();
    }

    @Override
    public AppConfig getAppConfig() {
        return context.getAppConfig();
    }

    @Override
    public FileCache getFileCache() {
        return context.getFileCache();
    }

    @Override
    public GroupLoader getGroupLoader() {
        return context.getGroupLoader();
    }

    @Override
    public LockCache getLockCache() {
        return context.getLockCache();
    }

    @Override
    public ModCache getModCache() {
        return context.getModCache();
    }
}
