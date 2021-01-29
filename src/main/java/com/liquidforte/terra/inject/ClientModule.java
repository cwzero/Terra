package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.curse.client.Client;
import com.liquidforte.curse.client.CurseClient;

public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Client.class).to(CurseClient.class);
    }
}
