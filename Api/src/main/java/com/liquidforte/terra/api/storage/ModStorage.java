package com.liquidforte.terra.api.storage;

public interface ModStorage {
    long getAddonId(String slug);

    void setAddonId(long addonId, String slug);

    long getModCount();
}
