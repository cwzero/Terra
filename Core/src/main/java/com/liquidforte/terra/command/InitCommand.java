package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;

public class InitCommand extends AbstractCommand {
    @Inject
    public InitCommand() {

    }

    public InitCommand(CommandContext context) {
        super(context);
    }

    @Override
    public Command getInstance(CommandContext context) {
        return new InitCommand(context);
    }

    @Override
    public void run() {
        // TODO: Implement
        // Should create a new project with the defaults/ values specified in options
    }
}
