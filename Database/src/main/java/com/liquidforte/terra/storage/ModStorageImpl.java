package com.liquidforte.terra.storage;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.api.database.DatabaseFactory;
import com.liquidforte.terra.api.database.ModDao;
import com.liquidforte.terra.api.storage.ModStorage;
import org.jdbi.v3.core.Jdbi;

public class ModStorageImpl implements ModStorage {
    private final Database modDatabase;

    @Inject
    public ModStorageImpl(@Named("mods") Database modDatabase) {
        this.modDatabase = modDatabase;
        modDatabase.getJdbi().useExtension(ModDao.class, dao -> dao.createTable());
    }

    @Override
    public long getAddonId(String slug) {
        try {
            return modDatabase.getJdbi().withExtension(ModDao.class, dao -> dao.getAddonId(slug));
        } catch (Exception ex) {
        }
        return -1;
    }

    @Override
    public void setAddonId(long addonId, String slug) {
        if (addonId > 0) {
            modDatabase.getJdbi().useExtension(ModDao.class, dao -> dao.insert(addonId, slug));
        } else {
            throw new RuntimeException("Tried to store an invalid mod: {addonId:" + addonId + ",slug:" +slug + "}");
        }
    }
}
