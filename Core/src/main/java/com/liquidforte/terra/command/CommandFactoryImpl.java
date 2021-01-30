package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.command.CommandFactory;

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
        return createCommand(context, context.getOptions().getCommand());
    }

    @Override
    public Command createCommand(CommandContext context, String[] command) {
        Command[] commands = new Command[command.length];

        for (int i = 0; i < command.length; i++) {
            commands[i] = createCommand(context, command[i]);
        }

        return new CompositeCommand(context, commands);
    }

    @Override
    public Command createCommand(CommandContext context, String command) {
        return commands.stream().filter(it ->
                it.getCommand().contentEquals(command) ||
                        Arrays.stream(it.getAlias()).anyMatch(c -> c.contentEquals(command))).findFirst().orElse(null);
    }
}
