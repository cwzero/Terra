package com.liquidforte.terra.command.impl;

import com.google.common.base.Strings;
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
        String[] cos = context.getAppOptions().getCommand();

        if (cos.length <= 0) {
            return createCommand(context, "commands");
        } else {
            return createCommand(context, cos);
        }
    }

    @Override
    public Command createCommand(CommandContext context, String[] command) {
        if (command.length <= 0 || Arrays.stream(command).allMatch(Strings::isNullOrEmpty)) {
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
        if (Strings.isNullOrEmpty(command)) {
            return null;
        }

        for (Command co : commands) {
            if (co.getCommand().contentEquals(command) || co.getAlias().stream().anyMatch(c -> c.contentEquals(command))) {
                return co;
            }
        }

        return null;
    }
}
