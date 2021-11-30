package com.liquidforte.terra.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.liquidforte.terra.api.model.ModSpec;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class ModSpecImpl implements ModSpec {
    private final String slug;
}
