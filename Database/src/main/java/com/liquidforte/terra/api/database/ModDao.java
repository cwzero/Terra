package com.liquidforte.terra.api.database;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ModDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS ADDON(ADDON_ID BIGINT PRIMARY KEY, SLUG VARCHAR NOT NULL UNIQUE)")
    void createTable();

    @SqlUpdate("MERGE INTO ADDON(ADDON_ID, SLUG) VALUES (:addonId, :slug)")
    void insert(@Bind("addonId") long addonId, @Bind("slug") String slug);

    @SqlQuery("SELECT ADDON_ID FROM ADDON WHERE SLUG = :slug")
    long getAddonId(@Bind("slug") String slug);

    @SqlQuery("SELECT COUNT (*) FROM ADDON")
    long getModCount();

    @SqlUpdate("DELETE FROM ADDON WHERE SLUG = :slug")
    void deleteAddonId(@Bind("slug") String slug);
}
