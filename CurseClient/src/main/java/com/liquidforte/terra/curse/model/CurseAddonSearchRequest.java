package com.liquidforte.terra.curse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurseAddonSearchRequest {
    private long categoryId = 0;
    private long gameId = 432;
    private String gameVersion = "1.16.5";
    private long index = 0;
    private long pageSize = 50;
    private String searchFilter = "";
    private long sectionId = 6;
    private long sort = 5;
}
