package com.liquidforte.terra.storage;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.api.database.FileDao;
import com.liquidforte.terra.api.database.FileDataDao;
import com.liquidforte.terra.api.database.FileDependencyDao;
import com.liquidforte.terra.api.model.File;
import com.liquidforte.terra.api.storage.FileStorage;
import org.jdbi.v3.core.result.ResultSetException;

import java.util.List;

public class FileStorageImpl implements FileStorage {
    private final FileCache fileCache;
    private final Database fileDatabase;

    @Inject
    public FileStorageImpl(@Named("files") Database fileDatabase, FileCache fileCache) {
        this.fileCache = fileCache;
        this.fileDatabase = fileDatabase;

        fileDatabase.getJdbi().useExtension(FileDao.class, dao -> dao.createTable());
        fileDatabase.getJdbi().useExtension(FileDataDao.class, dao -> dao.createTable());
        fileDatabase.getJdbi().useExtension(FileDependencyDao.class, dao -> dao.createTable());
    }

    @Override
    public byte[] getData(long addonId, long fileId) {
        try {
            byte[] result = fileDatabase.getJdbi().withExtension(FileDataDao.class, dao -> dao.getData(fileId));
            File file = fileCache.getFile(addonId, fileId);
            if (result.length == file.getFileLength()) {
                return result;
            } else {
                fileDatabase.getJdbi().useExtension(FileDataDao.class, dao -> dao.delete(fileId));
            }
        } catch (ResultSetException ex) {
            fileDatabase.getJdbi().useExtension(FileDataDao.class, dao -> dao.delete(fileId));
        } catch (IllegalStateException ex) {
            if (!ex.getMessage().contains("no results")) {
                fileDatabase.getJdbi().useExtension(FileDataDao.class, dao -> dao.delete(fileId));
            }
        }
        return new byte[0];
    }

    @Override
    public void setData(long addonId, long fileId, byte[] data) {
        File file = fileCache.getFile(addonId, fileId);
        if (data.length == file.getFileLength()) {
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
        if (file != null)
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
