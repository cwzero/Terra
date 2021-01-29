package com.liquidforte.terra.inject;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.liquidforte.curse.jackson.MultiDateDeserializer;

import java.util.Date;

public class JacksonModule extends AbstractModule {
    @Provides
    protected ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

        mapper.setDefaultPrettyPrinter(prettyPrinter);

        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateSerializer());
        module.addDeserializer(Date.class, new MultiDateDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

    @Provides
    protected ObjectWriter getObjectWriter(ObjectMapper mapper) {
        return mapper.writerWithDefaultPrettyPrinter();
    }
}
