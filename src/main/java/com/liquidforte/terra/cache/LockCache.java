package com.liquidforte.terra.cache;

public interface LockCache extends Cache {
    long getLock(String slug, String filter);

    long getLock(long addonId, String filter);
}
