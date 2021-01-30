package com.liquidforte.terra.database;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.liquidforte.terra.api.database.Database;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.util.FileUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Paths;

public class H2Database implements Database {
    private final AppOptions appOptions;
    private final boolean isLocal;
    private final boolean inMemory;
    private final String name;

    private DataSource dataSource;
    private DataSource pooledDataSource;

    private String getPath() {
        if (isLocal) {
            return FileUtil.getRelativePath(new File("."), appOptions.getLocalCachePath().toFile()) + "/" + name;
        } else {
            return FileUtil.getRelativePath(Paths.get("~").toFile(), appOptions.getCachePath().toFile()) + "/" + name;
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

            pooledDataSource = new HikariDataSource(config);
        }
        return pooledDataSource;
    }

    @Override
    public Jdbi getJdbi() {
        return Jdbi.create(getPooledDataSource());
    }

    @Inject
    public H2Database(AppOptions appOptions, @Assisted boolean inMemory, @Assisted boolean isLocal, @Assisted String name) {
        this.appOptions = appOptions;
        this.inMemory = inMemory;
        this.isLocal = appOptions.isLocal() || isLocal;
        this.name = name;
    }
}
