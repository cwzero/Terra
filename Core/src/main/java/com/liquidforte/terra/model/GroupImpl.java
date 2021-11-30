package com.liquidforte.terra.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupImpl implements Group {
    private String id = "";
    private boolean enabled = true;
    @JsonDeserialize(contentAs = ModSpecImpl.class)
    private List<ModSpec> mods = new ArrayList<>();
}
