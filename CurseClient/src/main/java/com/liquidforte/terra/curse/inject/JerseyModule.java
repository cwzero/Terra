package com.liquidforte.terra.curse.inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.RequestEntityProcessing;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.ext.ContextResolver;

public class JerseyModule extends AbstractModule {
    @Provides
    public ContextResolver<ObjectMapper> getJacksonResolver(ObjectMapper mapper) {
        return type -> mapper;
    }

    @Provides
    public ClientConfig getClientConfig(ContextResolver<ObjectMapper> jacksonResolver) {
        return new ClientConfig()
                .register(jacksonResolver)
                .property(ClientProperties.REQUEST_ENTITY_PROCESSING, RequestEntityProcessing.BUFFERED);
    }

    @Provides
    public ClientBuilder getClientBuilder(ClientConfig clientConfig) {
        return ClientBuilder.newBuilder().withConfig(clientConfig);
    }
}
