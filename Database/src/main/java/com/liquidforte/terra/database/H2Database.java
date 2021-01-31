package com.liquidforte.terra.database;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.api.database.DatabaseServer;
import com.liquidforte.terra.api.options.AppOptions;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public class H2Database implements Database {
    private final AppOptions appOptions;
    private final boolean isLocal;
    private final boolean inMemory;
    private final String name;

    private DataSource dataSource;
    private DataSource pooledDataSource;

    private String getPath() {
        if (isLocal) {
            return appOptions.getLocalCachePath() + "/" + name;
        } else {
            return appOptions.getCachePath() + "/" + name;
        }
    }

    private String getUrl() {
        if (inMemory) {
            return "jdbc:h2:tcp://localhost/mem:" + name;
        } else {
            return "jdbc:h2:tcp://localhost/" + getPath();
        }
    }

    private DataSource getDataSource() {
        if (dataSource == null) {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(getUrl());
            ds.setUser("sa");
            ds.setPassword("sa");

            dataSource = ds;
        }
        return dataSource;
    }

    private DataSource getPooledDataSource() {
        if (pooledDataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setDataSource(getDataSource());
            config.setMaximumPoolSize(20);

            pooledDataSource = new HikariDataSource(config);
        }
        return pooledDataSource;
    }

    @Override
    public Jdbi getJdbi() {
        return Jdbi.create(getPooledDataSource()).installPlugins();
    }

    @Inject
    public H2Database(DatabaseServer databaseServer, AppOptions appOptions, @Assisted("inMemory") boolean inMemory, @Assisted("isLocal") boolean isLocal, @Assisted("name") String name) {
        this.appOptions = appOptions;
        this.inMemory = inMemory;
        this.isLocal = appOptions.isLocal() || isLocal;
        this.name = name;
        databaseServer.start();
    }
}
