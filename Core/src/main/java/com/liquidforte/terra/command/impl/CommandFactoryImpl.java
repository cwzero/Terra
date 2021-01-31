package com.liquidforte.terra.command.impl;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.command.CommandFactory;
import com.liquidforte.terra.command.CompositeCommand;

import java.util.Arrays;
import java.util.Set;

public class CommandFactoryImpl implements CommandFactory {
    private final Set<Command> commands;

    @Inject
    public CommandFactoryImpl(Set<Command> commands) {
        this.commands = commands;
    }

    @Override
    public Command createCommand(CommandContext context) {
        return createCommand(context, context.getAppOptions().getCommand());
    }

    @Override
    public Command createCommand(CommandContext context, String[] command) {
        if (command.length <= 0) {
            return null;
        }

        if (command.length > 1) {
            Command[] commands = new Command[command.length];

            for (int i = 0; i < command.length; i++) {
                commands[i] = createCommand(context, command[i]);
            }

            return new CompositeCommand(context, commands);
        }

        return createCommand(context, command[0]);
    }

    @Override
    public Command createCommand(CommandContext context, String command) {
        for (Command co : commands) {
            if (co.getCommand().contentEquals(command) || Arrays.stream(co.getAlias()).anyMatch(c -> c.contentEquals(command))) {
                return co;
            }
        }

        return null;
    }
}
