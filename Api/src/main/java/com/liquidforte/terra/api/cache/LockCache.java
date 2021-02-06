package com.liquidforte.terra.api.cache;

public interface LockCache {
    String getForgeLock();

    String updateForgeLock();

    void setForgeLock(String forgeVersion);

    long getLock(String slug);

    long getLock(long addonId);

    long update(String slug);

    long update(long addonId);

    void load();

    void save();
}
