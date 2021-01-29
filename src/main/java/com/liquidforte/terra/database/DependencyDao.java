package com.liquidforte.terra.database;

import com.liquidforte.terra.model.lock.FileDependency;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface DependencyDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS FILE_DEPENDENCY(SOURCE_ADDON_ID BIGINT, SOURCE_FILE_ID BIGINT, ADDON_ID BIGINT, FILE_ID BIGINT, PRIMARY KEY(SOURCE_FILE_ID, FILE_ID))")
    void createTable();

    @SqlUpdate("MERGE INTO FILE_DEPENDENCY(SOURCE_ADDON_ID, SOURCE_FILE_ID, ADDON_ID, FILE_ID) VALUES (:sourceAddonId, :sourceFileId, :addonId, :fileId)")
    void insertDependency(@BindBean FileDependency dependency);

    @SqlQuery("SELECT * FROM FILE_DEPENDENCY WHERE SOURCE_FILE_ID = ?")
    @RegisterBeanMapper(FileDependency.class)
    List<FileDependency> getDependencies(long sourceFileId);

    @SqlQuery("SELECT * FROM FILE_DEPENDENCY")
    @RegisterBeanMapper(FileDependency.class)
    List<FileDependency> getDependencies();
}
