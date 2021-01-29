package com.liquidforte.terra.app;

import com.google.inject.Inject;
import com.liquidforte.terra.command.CommandParser;
import com.liquidforte.terra.database.CacheDatabase;
import com.liquidforte.terra.database.DatabaseServer;
import com.liquidforte.terra.database.LockDatabase;

public class TerraApp extends AbstractApp {
    private final CommandParser parser;

    @Inject
    public TerraApp(CacheDatabase cacheDatabase, LockDatabase lockDatabase, DatabaseServer databaseServer, CommandParser parser) {
        super(cacheDatabase, lockDatabase, databaseServer);
        this.parser = parser;
    }

    @Override
    protected void doRun() {
        parser.run();
    }
}
