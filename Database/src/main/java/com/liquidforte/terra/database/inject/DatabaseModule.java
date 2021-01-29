package com.liquidforte.terra.database.inject;

import com.google.inject.AbstractModule;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.database.TerraDatabaseImpl;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Database.class).to(TerraDatabaseImpl.class);
    }
}
