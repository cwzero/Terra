package com.liquidforte.terra.api.database;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ModDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS MOD(ADDON_ID BIGINT PRIMARY KEY, SLUG VARCHAR NOT NULL UNIQUE)")
    void createTable();

    @SqlUpdate("INSERT INTO MOD(ADDON_ID, SLUG) VALUES (:addonId, :slug)")
    void insert(@Bind long addonId, @Bind String slug);

    @SqlQuery("SELECT ADDON_ID FROM MOD WHERE SLUG = :slug")
    long getAddonId(@Bind String slug);
}
