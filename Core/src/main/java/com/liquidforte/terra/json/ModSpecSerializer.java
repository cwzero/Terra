package com.liquidforte.terra.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.liquidforte.terra.model.ModSpecImpl;

import java.io.IOException;

public class ModSpecSerializer extends StdSerializer<ModSpecImpl> {
    private static final long serialVersionUID = 1L;

    public ModSpecSerializer() {
        this(null, false);
    }

    protected ModSpecSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    @Override
    public void serialize(ModSpecImpl value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getSlug());
    }
}
