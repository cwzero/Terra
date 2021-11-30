package com.liquidforte.terra.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.liquidforte.terra.api.database.DatabaseServer;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Singleton
public class DatabaseServerImpl implements DatabaseServer {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseServerImpl.class);
    private final Server server;
    private boolean starting = false;
    private boolean started = false;

    @Inject
    public DatabaseServerImpl(Server server) {
        this.server = server;
    }

    public synchronized boolean isStarting() {
        return starting && server.getStatus().trim().toLowerCase().contentEquals("not started");
    }

    public synchronized boolean isStarted() {
        return started && !server.getStatus().trim().toLowerCase().contentEquals("not started");
    }

    @Override
    public synchronized void start() {
        if (!isStarted() && !isStarting()) {
            starting = true;

            LOG.info("Database Server Starting...");
            try {
                server.start();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            LOG.info("Database Server Started.");

            started = true;
        }
    }

    @Override
    public synchronized void stop() {
        if (isStarting() || isStarted()) {
            server.stop();
        }
    }

    @Override
    public synchronized void close() throws Exception {
        if (isStarting() || isStarted()) {
            LOG.info("Database Server Stopping...");
            stop();
            LOG.info("Database Server Stopped.");
        }
    }
}
