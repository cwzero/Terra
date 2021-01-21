package com.liquidforte.terra.model;

import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public interface Database {
    DataSource getDataSource();

    DataSource getPooledDataSource();

    Jdbi getJdbi();
}
