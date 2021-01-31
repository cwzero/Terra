package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.command.CommandFactory;
import com.liquidforte.terra.api.command.CommandParser;
import com.liquidforte.terra.command.*;
import com.liquidforte.terra.command.impl.CommandContextImpl;
import com.liquidforte.terra.command.impl.CommandFactoryImpl;
import com.liquidforte.terra.command.impl.CommandParserImpl;
import com.liquidforte.terra.command.mmc.*;
import com.liquidforte.terra.command.server.*;

public class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CommandContext.class).to(CommandContextImpl.class);
        bind(CommandParser.class).to(CommandParserImpl.class);
        bind(CommandFactory.class).to(CommandFactoryImpl.class);

        Multibinder<Command> commandBinder = Multibinder.newSetBinder(binder(), Command.class);
        commandBinder.addBinding().to(CommandsCommand.class);
        commandBinder.addBinding().to(DownloadCommand.class);
        commandBinder.addBinding().to(GenerateMMCFilesCommand.class);
        commandBinder.addBinding().to(GenerateServerFilesCommand.class);
        commandBinder.addBinding().to(RunMMCCommand.class);
        commandBinder.addBinding().to(RunMMCInstanceCommand.class);
        commandBinder.addBinding().to(RunServerCommand.class);
        commandBinder.addBinding().to(InitCommand.class);
        commandBinder.addBinding().to(InstallCommand.class);
        commandBinder.addBinding().to(InstallForgeCommand.class);
        commandBinder.addBinding().to(InstallJavaCommand.class);
        commandBinder.addBinding().to(InstallMMCCommand.class);
        commandBinder.addBinding().to(LinkCommand.class);
        commandBinder.addBinding().to(LinkMMCCommand.class);
        commandBinder.addBinding().to(LinkServerCommand.class);
        commandBinder.addBinding().to(ResolveCommand.class);
        commandBinder.addBinding().to(UpdateCommand.class);
    }
}
