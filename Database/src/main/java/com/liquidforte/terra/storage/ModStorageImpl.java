package com.liquidforte.terra.storage;

import com.google.inject.Inject;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.api.database.DatabaseFactory;
import com.liquidforte.terra.api.database.ModDao;
import com.liquidforte.terra.api.storage.ModStorage;

public class ModStorageImpl implements ModStorage {
    private final Database modDatabase;

    @Inject
    public ModStorageImpl(DatabaseFactory databaseFactory) {
        modDatabase = databaseFactory.create(false, false, "mods");
    }

    @Override
    public long getAddonId(String slug) {
        return modDatabase.getJdbi().withExtension(ModDao.class, dao -> dao.getAddonId(slug));
    }

    @Override
    public void setAddonId(long addonId, String slug) {
        modDatabase.getJdbi().useExtension(ModDao.class, dao -> dao.insert(addonId, slug));
    }
}
