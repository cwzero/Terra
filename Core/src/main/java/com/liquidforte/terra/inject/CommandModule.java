package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.command.CommandFactory;
import com.liquidforte.terra.api.command.CommandParser;
import com.liquidforte.terra.command.CommandContextImpl;
import com.liquidforte.terra.command.CommandFactoryImpl;
import com.liquidforte.terra.command.CommandParserImpl;
import com.liquidforte.terra.command.ResolveCommand;

public class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CommandContext.class).to(CommandContextImpl.class);
        bind(CommandParser.class).to(CommandParserImpl.class);
        bind(CommandFactory.class).to(CommandFactoryImpl.class);

        Multibinder<Command> commandBinder = Multibinder.newSetBinder(binder(), Command.class);
        commandBinder.addBinding().to(ResolveCommand.class);
    }
}
