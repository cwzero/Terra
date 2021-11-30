package com.liquidforte.terra.curse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModLoader {
    private String name = "";
    private String gameVersion = "";
    private boolean latest = false;
    private boolean recommended = false;
    private Date dateModified;
}
