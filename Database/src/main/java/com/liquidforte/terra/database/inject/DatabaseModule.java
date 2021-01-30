package com.liquidforte.terra.database.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.database.DatabaseServer;
import com.liquidforte.terra.database.DatabaseServerImpl;
import com.liquidforte.terra.database.TerraDatabaseImpl;
import org.h2.tools.Server;

import java.sql.SQLException;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Database.class).to(TerraDatabaseImpl.class);
        bind(DatabaseServer.class).to(DatabaseServerImpl.class).asEagerSingleton();
    }

    @Provides
    public Server getServer() {
        String[] args = { "-ifNotExists" };

        try {
            return Server.createTcpServer(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
