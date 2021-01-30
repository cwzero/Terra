package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.options.AppOptions;

public class CommandContextImpl implements CommandContext {
    private final AppOptions appOptions;

    @Inject
    public CommandContextImpl(AppOptions appOptions) {
        this.appOptions = appOptions;
    }

    @Override
    public AppOptions getOptions() {
        return appOptions;
    }
}
