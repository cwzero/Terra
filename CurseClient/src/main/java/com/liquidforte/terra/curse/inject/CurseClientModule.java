package com.liquidforte.terra.curse.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.api.curse.CurseClient;
import com.liquidforte.terra.curse.AddonSearchServiceImpl;
import com.liquidforte.terra.curse.CurseClientImpl;

public class CurseClientModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FeignModule());
        install(new JerseyModule());

        bind(CurseClient.class).to(CurseClientImpl.class);
        bind(AddonSearchService.class).to(AddonSearchServiceImpl.class);
    }
}
