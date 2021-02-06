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
    public Set<String> getAlias() {
        return Set.of("help");
    }

    @Override
    public String getDescription() {
        return "List available commands";
    }

    @Override
    public void doRun() {
        commands.stream().forEach(command -> {
            System.out.println("-" + command.getCommand() + ": ");
            System.out.println("\t" + command.getDescription());
        });
    }
}
