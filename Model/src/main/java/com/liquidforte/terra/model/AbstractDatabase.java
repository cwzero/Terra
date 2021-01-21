package com.liquidforte.terra.model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public abstract class AbstractDatabase implements Database {
    @Override
    public DataSource getPooledDataSource() {
        var result = new HikariDataSource(new HikariConfig());
        result.setDataSource(getDataSource());
        result.setMaximumPoolSize(6);
        return result;
    }

    @Override
    public Jdbi getJdbi() {
        return Jdbi.create(getPooledDataSource()).installPlugins();
    }
}
