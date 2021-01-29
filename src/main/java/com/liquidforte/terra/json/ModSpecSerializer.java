package com.liquidforte.terra.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.liquidforte.terra.model.ModSpec;
import com.liquidforte.terra.util.FilterUtil;

public class ModSpecSerializer extends StdSerializer<ModSpec> {
    private static final long serialVersionUID = 1L;

    public ModSpecSerializer() {
        this(null, false);
    }

    protected ModSpecSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    @Override
    public void serialize(ModSpec value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String filter = value.getFilter();

        if (FilterUtil.checkFilter(filter)) {
            gen.writeString(value.getSlug());
        } else {
            gen.writeStartObject();

            gen.writeStringField("slug", value.getSlug());
            gen.writeStringField("fileNameFilter", value.getFilter());

            gen.writeEndObject();
        }
    }
}