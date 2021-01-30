package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;

public abstract class AbstractCommand implements Command {
    protected CommandContext context;

    protected AbstractCommand() {

    }

    protected AbstractCommand(CommandContext context) {
        this.context = context;
    }

    protected void setContext(CommandContext context) {
        this.context = context;
    }
}
