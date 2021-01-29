package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.Cache;
import com.liquidforte.terra.api.database.Database;

public class TerraCacheImpl implements Cache {
    private Database database;

    @Inject
    public TerraCacheImpl(Database database) {
        this.database = database;
    }
}
