package com.liquidforte.terra.api.storage;

import java.util.Map;

public interface LockStorage {
    long getLock(long addonId);

    void setLock(long addonId, long fileId);

    Map<Long, Long> getLocks();

    String getForgeLock(String minecraftVersion);

    void setForgeLock(String minecraftVersion, String forgeVersion);
}
