package com.liquidforte.terra.model.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDependency {
    private long sourceAddonId = -1;
    private long sourceFileId = -1;
    private long fileId = -1;
    private long addonId = -1;
}
