package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;

public abstract class AbstractCommand implements Command {
    private final CommandContext context;

    protected AbstractCommand(CommandContext context) {
        this.context = context;
    }

    protected CommandContext getContext() {
        return context;
    }
}
