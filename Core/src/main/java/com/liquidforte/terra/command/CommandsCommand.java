package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;

import java.util.Set;

public class CommandsCommand extends AbstractCommand {
    private final Set<Command> commands;

    @Inject
    public CommandsCommand(CommandContext context, Set<Command> commands) {
        super(context);
        this.commands = commands;
    }

    @Override
    public void doRun() {
        commands.stream().map(it -> it.getCommand()).forEach(System.out::println);
    }
}
