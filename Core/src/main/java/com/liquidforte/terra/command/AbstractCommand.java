package com.liquidforte.terra.command;

import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.command.CommandParser;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.loader.GroupLoader;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.api.options.AppPaths;

public abstract class AbstractCommand implements Command, CommandContext {
    private final CommandContext context;

    protected AbstractCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public CommandParser getCommandParser() {
        return context.getCommandParser();
    }

    @Override
    public AppConfig getAppConfig() {
        return context.getAppConfig();
    }

    @Override
    public AppOptions getAppOptions() {
        return context.getAppOptions();
    }

    @Override
    public AppPaths getAppPaths() {
        return context.getAppPaths();
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

    protected void before() {
        System.out.println("Running command: " + getCommand());
    }

    protected abstract void doRun();

    protected void after() {

    }

    @Override
    public void run() {
        before();

        doRun();

        after();
    }
}
