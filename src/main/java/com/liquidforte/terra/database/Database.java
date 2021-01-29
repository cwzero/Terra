package com.liquidforte.terra.database;

import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    Connection getConnection() throws SQLException;

    DataSource getDataSource();

    DataSource getPooledDataSource();

    Jdbi getJdbi();
}
