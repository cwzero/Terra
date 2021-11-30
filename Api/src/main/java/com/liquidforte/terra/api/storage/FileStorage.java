package com.liquidforte.terra.api.storage;

import com.liquidforte.terra.api.model.File;

import java.util.List;

public interface FileStorage {
    byte[] getData(long addonId, long fileId);
    void setData(long addonId, long fileId, byte[] data);

    File getFile(long addonId, long fileId);
    void setFile(long addonId, File file);

    long getAddonId(long fileId);

    List<Long> getDependencies(long addonId, long fileId);
    void addDependency(long sourceAddonId, long sourceFileId, long addonId);
}
