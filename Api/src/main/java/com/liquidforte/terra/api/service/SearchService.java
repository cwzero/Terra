package com.liquidforte.terra.api.service;

import java.util.Map;

public interface SearchService {
    Map<String, Long> search(String minecraftVersion, int count);
}
