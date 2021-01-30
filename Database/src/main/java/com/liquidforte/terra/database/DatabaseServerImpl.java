package com.liquidforte.terra.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.SQLException;

@Singleton
public class DatabaseServerImpl implements DatabaseServer {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseServerImpl.class);
    private final Server server;

    @Inject
    public DatabaseServerImpl(Server server) {
        this.server = server;
    }

    public boolean isStarted() {
        return !server.getStatus().trim().toLowerCase().contentEquals("not started");
    }

    @Override
    @PostConstruct
    public void start() {
        if (!isStarted()) {
            LOG.info("Database Server Starting...");
            try {
                server.start();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            LOG.info("Database Server Started.");
        }
    }

    @Override
    @PreDestroy
    public void close() throws Exception {
        if (isStarted()) {
            LOG.info("Database Server Stopping...");
            server.stop();
            LOG.info("Database Server Stopped.");
        }
    }
}
