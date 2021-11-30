package com.liquidforte.terra.curse.inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.liquidforte.terra.api.curse.AddonSearchAPI;
import com.liquidforte.terra.api.curse.CurseFileAPI;
import com.liquidforte.terra.api.curse.ForgeSearchAPI;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs2.JAXRSClient;

import javax.ws.rs.client.ClientBuilder;

public class FeignModule extends AbstractModule {
    private static final String BASE_URL = "https://addons-ecs.forgesvc.net/api/v2";

    @Provides
    public Feign.Builder getBuilder() {
        return Feign.builder();
    }

    @Provides
    public feign.Client getClient(ClientBuilder clientBuilder) {
        return new JAXRSClient(clientBuilder);
    }

    @Provides
    public Decoder getDecoder(ObjectMapper mapper) {
        return new JacksonDecoder(mapper);
    }

    @Provides
    public Encoder getEncoder(ObjectMapper mapper) {
        return new JacksonEncoder(mapper);
    }

    @Provides
    public AddonSearchAPI getAddonSearchAPI(FeignClient feignClient) {
        return feignClient.target(AddonSearchAPI.class, BASE_URL);
    }

    @Provides
    public CurseFileAPI getCurseFileAPI(FeignClient feignClient) {
        return feignClient.target(CurseFileAPI.class, BASE_URL);
    }

    @Provides
    public ForgeSearchAPI getForgeSearchAPI(FeignClient feignClient) {
        return feignClient.target(ForgeSearchAPI.class, BASE_URL);
    }
}
