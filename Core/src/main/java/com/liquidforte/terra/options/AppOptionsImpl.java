package com.liquidforte.terra.options;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.liquidforte.terra.api.options.AppOptions;

public class AppOptionsImpl implements AppOptions {
    private final String[] command;

    @Inject
    public AppOptionsImpl(@Assisted String... args) {
        command = new String[]{"resolve"};
    }

    public String[] getCommand() {
        return command;
    }
}
