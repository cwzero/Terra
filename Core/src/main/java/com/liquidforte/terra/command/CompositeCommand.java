package com.liquidforte.terra.command;

import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;

import java.util.Arrays;

public class CompositeCommand extends AbstractCommand {
    private final Command[] commands;

    public CompositeCommand(CommandContext context, Command[] commands) {
        super(context);
        this.commands = Arrays.stream(commands).filter(it -> it != null).toArray(Command[]::new);
    }

    @Override
    protected void before() {

    }

    @Override
    public void doRun() {
        for (Command command : commands) {
            command.run();
        }
    }
}
