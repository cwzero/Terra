package com.liquidforte.terra.api.database;

import com.google.inject.assistedinject.Assisted;

public interface DatabaseFactory {
    Database create(@Assisted("inMemory") boolean inMemory, @Assisted("isLocal") boolean isLocal, @Assisted("name") String name);
}
