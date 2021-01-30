package com.liquidforte.terra.command;

import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;

import java.util.Arrays;

public class CompositeCommand extends AbstractCommand {
    private final Command[] commands;

    public CompositeCommand(CommandContext context, Command[] commands) {
        super(context);
        this.commands = Arrays.stream(commands).filter(it -> it != null).map(command -> command.getInstance(context)).toArray(Command[]::new);
    }

    @Override
    public void run() {
        for (Command command : commands) {
            command.run();
        }
    }

    @Override
    public Command getInstance(CommandContext context) {
        return new CompositeCommand(context, commands);
    }
}