package com.liquidforte.terra.api.database;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface FileDataDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS FILE_DATA(FILE_ID BIGINT PRIMARY KEY, DATA BYTEA NOT NULL)")
    void createTable();

    @SqlUpdate("MERGE INTO FILE_DATA(FILE_ID, DATA) VALUES (:fileId, :data)")
    void insert(@Bind("fileId") long fileId, @Bind("data") byte[] data);

    @SqlQuery("SELECT DATA FROM FILE_DATA WHERE FILE_ID = :fileId")
    byte[] getData(@Bind("fileId") long fileId);

    @SqlUpdate("DELETE FROM FILE_DATA WHERE FILE_ID = :fileId")
    void delete(@Bind("fileId") long fileId);
}
