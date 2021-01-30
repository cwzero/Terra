package com.liquidforte.terra.database;

public interface DatabaseServer extends AutoCloseable {
    void start();
}
