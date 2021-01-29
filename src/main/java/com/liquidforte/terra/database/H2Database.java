package com.liquidforte.terra.database;

import com.liquidforte.terra.config.DatabaseConfig;
import lombok.val;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class H2Database extends AbstractDatabase {
    private DatabaseConfig config;

    public H2Database(DatabaseConfig config) {
        this.config = config;
    }

    @Override
    public DataSource getDataSource() {
        val result = new JdbcDataSource();

        result.setURL(config.getUrl());
        result.setUser(config.getUsername());
        result.setPassword(config.getPassword());

        return result;
    }
}
