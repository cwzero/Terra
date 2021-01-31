package com.liquidforte.terra.curse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurseSortableGameVersion {
    private String gameVersionPadded;
    private String gameVersion;
    private Date gameVersionReleaseDate;
    private String gameVersionName;
}
