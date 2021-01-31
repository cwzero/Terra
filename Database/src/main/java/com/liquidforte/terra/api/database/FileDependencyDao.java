package com.liquidforte.terra.api.database;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Map;

public interface FileDependencyDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS FILE_DEPENDENCY(SOURCE_ADDON_ID BIGINT, SOURCE_FILE_ID BIGINT, ADDON_ID BIGINT, PRIMARY KEY (SOURCE_ADDON_ID, ADDON_ID))")
    void createTable();

    @SqlUpdate("INSERT INTO FILE_DEPENDENCY (SOURCE_ADDON_ID, SOURCE_FILE_ID, ADDON_ID) VALUES (:sourceAddonId, :sourceFileId, :addonId)")
    void insert(@Bind("sourceAddonId") long sourceAddonId, @Bind("sourceFileId") long sourceFileId, @Bind("addonId") long addonId);

    @SqlQuery("SELECT ADDON_ID FROM FILE_DEPENDENCY WHERE SOURCE_ADDON_ID = :sourceAddonId AND SOURCE_FILE_ID = :sourceFileId")
    List<Long> getAddonId(@Bind("sourceAddonId") long sourceAddonId, @Bind("sourceFileId") long sourceFileId);
}
