package com.liquidforte.terra.curse.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.api.curse.CurseClient;
import com.liquidforte.terra.api.service.FileService;
import com.liquidforte.terra.api.service.ForgeService;
import com.liquidforte.terra.api.service.ModService;
import com.liquidforte.terra.api.service.SearchService;
import com.liquidforte.terra.curse.*;
import com.liquidforte.terra.curse.command.ExportCommand;
import com.liquidforte.terra.curse.command.GenerateManifestCommand;

public class CurseClientModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FeignModule());
        install(new JerseyModule());

        bind(CurseClient.class).to(CurseClientImpl.class);
        bind(SearchService.class).to(AddonSearchService.class);
        bind(AddonSearchService.class).to(AddonSearchServiceImpl.class);

        bind(FileService.class).to(FileServiceImpl.class).in(Singleton.class);
        bind(ForgeService.class).to(ForgeServiceImpl.class);
        bind(ModService.class).to(ModServiceImpl.class);

        Multibinder<Command> commandBinder = Multibinder.newSetBinder(binder(), Command.class);
        commandBinder.addBinding().to(ExportCommand.class);
        commandBinder.addBinding().to(GenerateManifestCommand.class);
    }
}
