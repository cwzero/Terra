package com.liquidforte.terra.storage;

import com.google.inject.Inject;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.api.database.DatabaseFactory;
import com.liquidforte.terra.api.database.LockDao;
import com.liquidforte.terra.api.storage.LockStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LockStorageImpl implements LockStorage {
    private final Database lockDatabase;

    @Inject
    public LockStorageImpl(DatabaseFactory databaseFactory) {
        this.lockDatabase = databaseFactory.create(true, true, "lock");
        lockDatabase.getJdbi().useExtension(LockDao.class, dao -> dao.createTable());
    }

    @Override
    public long getLock(long addonId) {
        try {
            return lockDatabase.getJdbi().withExtension(LockDao.class, dao -> dao.getLock(addonId));
        } catch (Exception ex) {
        }
        return -1;
    }

    @Override
    public void setLock(long addonId, long fileId) {
        lockDatabase.getJdbi().useExtension(LockDao.class, dao -> dao.setLock(addonId, fileId));
    }

    @Override
    public Map<Long, Long> getLocks() {
        Map<Long, Long> result = new HashMap<>();

        lockDatabase.getJdbi().useExtension(LockDao.class, dao -> {
            try {
                List<Long> addons = dao.getAddons();
                for (long addonId : addons) {
                    try {
                        long lock = dao.getLock(addonId);
                        if (lock > 0) {
                            result.put(addonId, lock);
                        }
                    } catch (Exception ex) {
                    }
                }
            } catch (Exception ex) {

            }
        });

        return result;
    }
}
