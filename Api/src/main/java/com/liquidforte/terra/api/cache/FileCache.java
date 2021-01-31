package com.liquidforte.terra.api.cache;

import com.liquidforte.terra.api.model.File;

import java.util.List;

public interface FileCache {
    File getFile(long addonId, long fileId);

    byte[] getFileData(long addonId, long fileId);

    void installFile(long addonId, long fileId);

    List<Long> getModDependencies(long addonId, long fileId);
}
