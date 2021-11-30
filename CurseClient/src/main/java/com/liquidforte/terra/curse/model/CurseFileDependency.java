package com.liquidforte.terra.curse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurseFileDependency {
    private long id;
    private long addonId;
    private long type;
    private long fileId;
}
