package com.liquidforte.terra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jdbi.v3.json.Json;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private String id = "";
    private boolean enabled = true;
    private List<ModSpec> mods = new ArrayList<>();
}
