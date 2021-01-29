package com.liquidforte.terra.database;

import com.liquidforte.terra.model.lock.ModLock;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ModDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS ADDON(ADDON_ID BIGINT PRIMARY KEY, SLUG VARCHAR)")
    void createTable();

    @SqlUpdate("MERGE INTO ADDON(ADDON_ID, SLUG) VALUES (:addonId, :slug)")
    void insertMod(@BindBean ModLock mod);

    @SqlQuery("SELECT ADDON_ID FROM ADDON WHERE SLUG = ?")
    long getAddonIdBySlug(String slug);
}
