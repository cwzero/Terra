package com.liquidforte.terra.command;

import com.google.common.base.Strings;
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

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public abstract class AbstractCommand implements Command, CommandContext {
    private final CommandContext context;
    private String[] deps = new String[0];
    private Command dependencies = null;

    protected AbstractCommand(CommandContext context) {
        this.context = context;
    }

    protected void setDependencies(String... args) {
        deps = args;
    }

    protected String[] getDeps() {
        return Arrays.stream(deps).filter(it -> !Strings.isNullOrEmpty(it)).collect(Collectors.toList()).toArray(new String[0]);
    }

    protected Command getDependencies() {
        if (dependencies == null || dependencies.isEmpty()) {
            if (getDeps().length > 0) {
                dependencies = getCommandParser().parse(getDeps());
            }
        }
        return dependencies;
    }

    @Override
    public boolean needsDatabase() {
        if (getDependencies() != null) {
            return dependencies.needsDatabase();
        }
        return false;
    }

    @Override
    public boolean needsLockCache() {
        if (getDependencies() != null) {
            return dependencies.needsLockCache();
        }
        return false;
    }

    @Override
    public ExecutorService getExecutorService() { return context.getExecutorService(); }

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
        if (getDependencies() != null) {
            dependencies.run();
        }
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
