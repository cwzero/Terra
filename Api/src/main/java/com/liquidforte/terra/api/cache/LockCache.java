package com.liquidforte.terra.api.cache;

public interface LockCache {
    long getLock(String slug);
    long getLock(long addonId);
    long update(String slug);
    long update(long addonId);

    void load();
    void save();
    void updateAll();
}
