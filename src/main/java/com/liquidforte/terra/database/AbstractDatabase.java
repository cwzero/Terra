package com.liquidforte.terra.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class AbstractDatabase implements Database {
    @Override
    public DataSource getPooledDataSource() {
        var config = new HikariConfig();
        config.setDataSource(getDataSource());
        config.setMaximumPoolSize(6);

        return new HikariDataSource(config);
    }

    @Override
    public Jdbi getJdbi() {
        return Jdbi.create(getPooledDataSource()).installPlugins();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getPooledDataSource().getConnection();
    }

    public void runTransaction(Consumer<Connection> transaction) {
        try (Connection conn = getConnection()) {
            transaction.accept(conn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void runJdbiTransaction(Consumer<Jdbi> transaction) {
        runTransaction(conn -> {
            transaction.accept(Jdbi.create(conn).installPlugins());
        });
    }
}