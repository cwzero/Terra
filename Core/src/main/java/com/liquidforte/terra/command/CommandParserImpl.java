package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContextFactory;
import com.liquidforte.terra.api.command.CommandFactory;
import com.liquidforte.terra.api.command.CommandParser;

public class CommandParserImpl implements CommandParser {
    private final CommandContextFactory commandContextFactory;
    private final CommandFactory commandFactory;

    @Inject
    public CommandParserImpl(CommandContextFactory commandContextFactory, CommandFactory commandFactory) {
        this.commandContextFactory = commandContextFactory;
        this.commandFactory = commandFactory;
    }

    @Override
    public Command parse(String... args) {
        return commandFactory.createCommand(commandContextFactory.create(args));
    }
}
