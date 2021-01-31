package com.liquidforte.terra.api.database;

public interface DatabaseServer extends AutoCloseable {
    void start();
    void stop();
}
