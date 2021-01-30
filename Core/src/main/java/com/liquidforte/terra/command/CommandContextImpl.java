package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.options.AppOptions;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class CommandContextImpl implements CommandContext {
    private final AppOptions appOptions;

    @Override
    public AppOptions getOptions() {
        return appOptions;
    }
}
