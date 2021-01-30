package com.liquidforte.terra.api.database;

public interface DatabaseFactory {
    Database create(boolean inMemory, boolean isLocal, String name);
}
