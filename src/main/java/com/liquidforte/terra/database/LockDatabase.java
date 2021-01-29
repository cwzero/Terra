package com.liquidforte.terra.database;

import com.google.inject.Inject;
import com.liquidforte.terra.config.LockConfig;

public class LockDatabase extends H2Database {
    @Inject
    public LockDatabase(LockConfig config) {
        super(config);
    }

    public void init() {
        getJdbi().useExtension(LockDao.class, dao -> dao.createTable());
    }

    public long getLock(long addonId, String filter) {
        try {
            return getJdbi().withExtension(LockDao.class, dao -> dao.getLock(addonId, filter));
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

    public void lock(long addonId, String filter, long fileId) {
        getJdbi().useExtension(LockDao.class, dao -> dao.lock(addonId, filter, fileId));
    }
}
