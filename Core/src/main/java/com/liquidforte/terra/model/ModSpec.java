package com.liquidforte.terra.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class ModSpec {
    private final String slug;
}
