package com.liquidforte.terra.database.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Named;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.api.database.DatabaseFactory;
import com.liquidforte.terra.api.storage.FileStorage;
import com.liquidforte.terra.api.storage.LockStorage;
import com.liquidforte.terra.api.storage.ModStorage;
import com.liquidforte.terra.api.database.DatabaseServer;
import com.liquidforte.terra.database.DatabaseServerImpl;
import com.liquidforte.terra.database.H2Database;
import com.liquidforte.terra.storage.FileStorageImpl;
import com.liquidforte.terra.storage.LockStorageImpl;
import com.liquidforte.terra.storage.ModStorageImpl;
import org.h2.tools.Server;

import java.sql.SQLException;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseServer.class).to(DatabaseServerImpl.class);
        install(new FactoryModuleBuilder()
                .implement(Database.class, H2Database.class)
                .build(DatabaseFactory.class));

        bind(FileStorage.class).to(FileStorageImpl.class);
        bind(LockStorage.class).to(LockStorageImpl.class);
        bind(ModStorage.class).to(ModStorageImpl.class);
    }

    @Provides
    @Named("files")
    @Singleton
    public Database getFileDatabase(DatabaseFactory databaseFactory) {
        return databaseFactory.create(false, false, "files");
    }

    @Provides
    @Named("mods")
    @Singleton
    public Database getModDatabase(DatabaseFactory databaseFactory) {
        return databaseFactory.create(false, false, "mods");
    }

    @Provides
    @Named("lock")
    @Singleton
    public Database getLockDatabase(DatabaseFactory databaseFactory) {
        return databaseFactory.create(true, true, "lock");
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
