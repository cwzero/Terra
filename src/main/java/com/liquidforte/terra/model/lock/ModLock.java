package com.liquidforte.terra.model.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModLock {
    private long addonId;
    private String slug;
}
