package com.liquidforte.terra.command.impl;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.command.CommandFactory;
import com.liquidforte.terra.api.command.CommandParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class CommandParserImpl implements CommandParser {
    private final CommandContext commandContext;
    private final CommandFactory commandFactory;

    @Override
    public Command parse() {
        return commandFactory.createCommand(commandContext);
    }

    @Override
    public Command parse(String... commands) {
        return commandFactory.createCommand(commandContext, commands);
    }
}
