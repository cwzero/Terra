package com.liquidforte.terra.api.database;

import org.jdbi.v3.core.Jdbi;

public interface Database {
    Jdbi getJdbi();
}
