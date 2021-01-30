package com.liquidforte.terra.api.config;

import java.util.List;

public interface AppConfig {
    String getPackName();
    String getPackVersion();
    List<String> getPackAuthors();

    String getMinecraftVersion();
    String getForgeVersion();
}
