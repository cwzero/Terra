package com.liquidforte.terra.client.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.client.TerraClient;
import com.liquidforte.terra.client.TerraClientImpl;

public class TerraClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TerraClient.class).to(TerraClientImpl.class);
    }
}
