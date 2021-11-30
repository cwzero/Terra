package com.liquidforte.terra.curse.inject;

import com.google.inject.Inject;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class FeignClient {
    private final Feign.Builder feignBuilder;
    private final feign.Client feignClient;
    private final Decoder decoder;
    private final Encoder encoder;

    public <T> T target(Class<T> targetClass, String url) {
        return feignBuilder.logger(new Slf4jLogger())
                .logLevel(Logger.Level.BASIC)
                .client(feignClient)
                .decoder(decoder)
                .encoder(encoder)
                .target(targetClass, url);
    }
}
