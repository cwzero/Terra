package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.model.File;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class FileCacheImpl implements FileCache {
    private final LockCache lockCache;

    @Override
    public File getFile(long fileId) {
        // TODO: Implement
        return null;
    }

    @Override
    public byte[] getFileData(long fileId) {
        // TODO: Implement
        return new byte[0];
    }

    @Override
    public List<Long> getModDependencies(long fileId) {
        // TODO: Implement
        return null;
    }
}
