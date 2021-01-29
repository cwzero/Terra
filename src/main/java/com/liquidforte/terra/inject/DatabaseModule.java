package com.liquidforte.terra.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.liquidforte.terra.database.CacheDatabase;
import com.liquidforte.terra.database.DatabaseServer;
import com.liquidforte.terra.database.LockDatabase;
import org.h2.tools.Server;

import java.sql.SQLException;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseServer.class).in(Singleton.class);
        bind(CacheDatabase.class).in(Singleton.class);
        bind(LockDatabase.class).in(Singleton.class);
    }

    @Provides
    public Server getServer() {
        String[] args = {"-ifNotExists"};
        try {
            return Server.createTcpServer(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
