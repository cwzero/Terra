package com.liquidforte.terra.app;

import com.liquidforte.terra.database.CacheDatabase;
import com.liquidforte.terra.database.DatabaseServer;
import com.liquidforte.terra.database.LockDatabase;

public abstract class AbstractApp implements App {
    private final CacheDatabase cacheDatabase;
    private final LockDatabase lockDatabase;
    private final DatabaseServer databaseServer;

    public AbstractApp(CacheDatabase cacheDatabase, LockDatabase lockDatabase, DatabaseServer databaseServer) {
        this.cacheDatabase = cacheDatabase;
        this.lockDatabase = lockDatabase;
        this.databaseServer = databaseServer;
    }

    protected DatabaseServer getDatabaseServer() {
        return databaseServer;
    }

    protected abstract void doRun();

    public void init() {
        cacheDatabase.init();
        lockDatabase.init();
    }

    public void run() {
        try (databaseServer) {
            databaseServer.start();

            init();
            doRun();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                databaseServer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
