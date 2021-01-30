package com.liquidforte.terra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private String id = "";
    private boolean enabled = true;
    private List<ModSpec> mods = new ArrayList<>();
}
