package com.liquidforte.terra.storage;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.database.*;
import com.liquidforte.terra.api.model.File;
import com.liquidforte.terra.api.storage.FileStorage;
import com.liquidforte.terra.util.FingerprintUtil;

import java.util.ArrayList;
import java.util.List;

public class FileStorageImpl implements FileStorage {
    private final FileCache fileCache;
    private final Database fileDatabase;

    @Inject
    public FileStorageImpl(DatabaseFactory databaseFactory, FileCache fileCache) {
        this.fileCache = fileCache;
        fileDatabase = databaseFactory.create(false, false, "files");

        fileDatabase.getJdbi().useExtension(FileDao.class, dao -> dao.createTable());
        fileDatabase.getJdbi().useExtension(FileDataDao.class, dao -> dao.createTable());
        fileDatabase.getJdbi().useExtension(FileDependencyDao.class, dao -> dao.createTable());
    }

    @Override
    public byte[] getData(long addonId, long fileId) {
        try {
            return fileDatabase.getJdbi().withExtension(FileDataDao.class, dao -> dao.getData(fileId));
        } catch (IllegalStateException ignored) {

        }
        return new byte[0];
    }

    @Override
    public void setData(long addonId, long fileId, byte[] data) {
        if (data.length > 0 && FingerprintUtil.getByteArrayHash(data) == fileCache.getFile(addonId, fileId).getFingerprint()) {
            fileDatabase.getJdbi().useExtension(FileDataDao.class, dao -> dao.insert(fileId, data));
        }
    }

    @Override
    public File getFile(long addonId, long fileId) {
        try {
            return fileDatabase.getJdbi().withExtension(FileDao.class, dao -> dao.getFile(fileId));
        } catch (IllegalStateException ignored) {
        }
        return null;
    }

    @Override
    public void setFile(long addonId, File file) {
        fileDatabase.getJdbi().useExtension(FileDao.class, dao -> dao.insert(addonId, file));
    }

    @Override
    public long getAddonId(long fileId) {
        try {
            return fileDatabase.getJdbi().withExtension(FileDao.class, dao -> dao.getAddonId(fileId));
        } catch (IllegalStateException ex) {

        }
        return -1;
    }

    @Override
    public List<Long> getDependencies(long addonId, long fileId) {
        try {
            return fileDatabase.getJdbi().withExtension(FileDependencyDao.class, dao -> dao.getAddonId(addonId, fileId));
        } catch (IllegalStateException ex) {
        }
        return null;
    }

    @Override
    public void addDependency(long sourceAddonId, long sourceFileId, long addonId) {
        fileDatabase.getJdbi().useExtension(FileDependencyDao.class, dao -> dao.insert(sourceAddonId, sourceFileId, addonId));
    }
}
