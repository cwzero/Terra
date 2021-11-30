package com.liquidforte.terra.api.database;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface LockDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS LOCK(ADDON_ID BIGINT NOT NULL, FILE_ID BIGINT NOT NULL, PRIMARY KEY (ADDON_ID, FILE_ID))")
    void createTable();

    @SqlQuery("SELECT FILE_ID FROM LOCK WHERE ADDON_ID = :addonId")
    long getLock(@Bind("addonId") long addonId);

    @SqlUpdate("MERGE INTO LOCK(ADDON_ID, FILE_ID) VALUES (:addonId, :fileId)")
    void setLock(@Bind("addonId") long addonId, @Bind("fileId") long fileId);

    @SqlQuery("SELECT ADDON_ID FROM LOCK")
    List<Long> getAddons();
}
