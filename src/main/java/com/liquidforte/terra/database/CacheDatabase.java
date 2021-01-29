package com.liquidforte.terra.database;

import com.google.inject.Inject;
import com.liquidforte.terra.config.CacheConfig;
import com.liquidforte.terra.model.lock.FileDependency;
import com.liquidforte.terra.model.lock.FileLock;
import com.liquidforte.terra.model.lock.ModLock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CacheDatabase extends H2Database {
    @Inject
    public CacheDatabase(CacheConfig config) {
        super(config);
    }

    public void init() {
        getJdbi().useExtension(ModDao.class, ModDao::createTable);
        getJdbi().useExtension(FileDao.class, FileDao::createTable);
        getJdbi().useExtension(FileDataDao.class, FileDataDao::createTable);
        getJdbi().useExtension(DependencyDao.class, DependencyDao::createTable);
    }

    public long getAddonId(String slug) {
        try {
            return (long) getJdbi().withExtension(ModDao.class, dao -> dao.getAddonIdBySlug(slug));
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("no results")) {
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ModLock insertMod(ModLock mod) {
        getJdbi().useExtension(ModDao.class, dao -> dao.insertMod(mod));
        return mod;
    }

    public void insertDependency(FileDependency dep) {
        getJdbi().useExtension(DependencyDao.class, dao -> dao.insertDependency(dep));
    }

    public FileLock getFile(long addonId, long fileId) {
        try {
            return getJdbi().withExtension(FileDao.class, dao -> dao.getFile(addonId, fileId));
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("no results")) {
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveFile(FileLock file) {
        try {
            getJdbi().useExtension(FileDao.class, dao -> {
                dao.insertFile(file);
            });

            for (FileDependency dep : file.getDependencies()) {
                dep.setSourceFileId(file.getFileId());
                getJdbi().useExtension(DependencyDao.class, dao -> {
                    dao.insertDependency(dep);
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Optional<byte[]> getFileData(long fileId) {
        try {
            return Optional.of(getJdbi().withExtension(FileDataDao.class, dao -> dao.getData(fileId)));
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("no results")) {
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void saveFileData(long fileId, byte[] data) {
        getJdbi().useExtension(FileDataDao.class, dao -> dao.insertData(fileId, data));
    }

    public boolean hasFile(long fileId) {
        return getFileData(fileId).isPresent();
    }

    public List<FileDependency> getDependencies(long addonId, long fileId) {
        try {
            return getJdbi().withExtension(DependencyDao.class, dao -> dao.getDependencies(fileId));
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("no results")) {
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
