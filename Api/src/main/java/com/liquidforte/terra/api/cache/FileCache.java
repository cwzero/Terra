package com.liquidforte.terra.api.cache;

import com.liquidforte.terra.api.model.File;

import java.util.List;

public interface FileCache {
    File getFile(long fileId);

    byte[] getFileData(long fileId);

    List<Long> getModDependencies(long fileId);
}
