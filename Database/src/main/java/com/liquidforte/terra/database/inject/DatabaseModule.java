package com.liquidforte.terra.database.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.api.database.DatabaseFactory;
import com.liquidforte.terra.database.DatabaseServer;
import com.liquidforte.terra.database.DatabaseServerImpl;
import com.liquidforte.terra.database.H2Database;
import org.h2.tools.Server;

import java.sql.SQLException;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseServer.class).to(DatabaseServerImpl.class).asEagerSingleton();
        install(new FactoryModuleBuilder()
                .implement(Database.class, H2Database.class)
                .build(DatabaseFactory.class));
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
