package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContextFactory;
import com.liquidforte.terra.api.command.CommandFactory;
import com.liquidforte.terra.api.command.CommandParser;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.api.options.AppOptionsFactory;
import com.liquidforte.terra.command.CommandContextImpl;
import com.liquidforte.terra.command.CommandFactoryImpl;
import com.liquidforte.terra.command.CommandParserImpl;
import com.liquidforte.terra.command.ResolveCommand;
import com.liquidforte.terra.options.AppOptionsImpl;

public class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(AppOptions.class, AppOptionsImpl.class)
                .build(AppOptionsFactory.class));
        bind(CommandParser.class).to(CommandParserImpl.class);
        bind(CommandFactory.class).to(CommandFactoryImpl.class);

        Multibinder<Command> commandBinder = Multibinder.newSetBinder(binder(), Command.class);
        commandBinder.addBinding().to(ResolveCommand.class);
    }

    @Provides
    public CommandContextFactory getCommandContextFactory(AppOptionsFactory appOptionsFactory) {
        return args -> new CommandContextImpl(appOptionsFactory.create(args));
    }
}
