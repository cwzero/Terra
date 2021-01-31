package com.liquidforte.terra.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackLock {
    private String minecraftVersion = "";
    private Map<Long, Long> lock = new HashMap<>();
}
