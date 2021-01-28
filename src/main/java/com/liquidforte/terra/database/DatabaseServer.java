package com.liquidforte.terra.database;

import com.google.inject.Inject;
import org.h2.tools.Server;

import java.sql.SQLException;

public class DatabaseServer implements AutoCloseable {
    private boolean started = false;
    private Server server;

    @Inject
    public DatabaseServer(Server server) {
        this.server = server;
    }

    public boolean isStarted() {
        return started || !server.getStatus().trim().toLowerCase().contentEquals("not started");
    }

    public void start() {
        if (!isStarted()) {
            try {
                server.start();
                started = true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception {
        if (isStarted()) {
            server.stop();
        }
    }
}
