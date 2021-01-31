package com.liquidforte.terra.curse.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.api.curse.CurseClient;
import com.liquidforte.terra.api.service.FileService;
import com.liquidforte.terra.api.service.ModService;
import com.liquidforte.terra.curse.AddonSearchServiceImpl;
import com.liquidforte.terra.curse.CurseClientImpl;
import com.liquidforte.terra.curse.FileServiceImpl;
import com.liquidforte.terra.curse.ModServiceImpl;

public class CurseClientModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FeignModule());
        install(new JerseyModule());

        bind(CurseClient.class).to(CurseClientImpl.class);
        bind(AddonSearchService.class).to(AddonSearchServiceImpl.class);

        bind(FileService.class).to(FileServiceImpl.class);
        bind(ModService.class).to(ModServiceImpl.class);
    }
}
