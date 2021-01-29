package com.liquidforte.terra.curse.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.curse.CurseClient;
import com.liquidforte.terra.curse.CurseClientImpl;

public class CurseClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CurseClient.class).to(CurseClientImpl.class);
    }
}
