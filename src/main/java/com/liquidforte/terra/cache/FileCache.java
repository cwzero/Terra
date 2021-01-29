package com.liquidforte.terra.cache;

import com.liquidforte.terra.model.lock.FileDependency;
import com.liquidforte.terra.model.lock.FileLock;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileCache extends Cache {
    FileLock getFile(long addonId, long fileId);

    void downloadFileToCache(long addonId, long fileId) throws IOException;

    boolean hasFile(long fileId);

    void installFile(Path destDir, long addonId, long fileId) throws IOException;

    List<FileDependency> getDependencies(long addonId, long fileId) throws IOException;
}
