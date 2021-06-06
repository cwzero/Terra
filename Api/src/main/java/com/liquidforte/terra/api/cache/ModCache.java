package com.liquidforte.terra.api.cache;

public interface ModCache {
    long getAddonId(String slug);

    void getAddons(String minecraftVersion, int count);

    void load();

    void save();
}
