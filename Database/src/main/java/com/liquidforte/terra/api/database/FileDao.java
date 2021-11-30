package com.liquidforte.terra.api.database;

import com.liquidforte.terra.api.model.File;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(File.class)
public interface FileDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS FILE(FILE_ID BIGINT PRIMARY KEY, ADDON_ID BIGINT NOT NULL, FILE_NAME VARCHAR NOT NULL UNIQUE, FILE_DATE DATETIME NOT NULL, FILE_LENGTH BIGINT NOT NULL, DOWNLOAD_URL VARCHAR NOT NULL, FINGERPRINT BIGINT NOT NULL)")
    void createTable();

    @SqlUpdate("MERGE INTO FILE(FILE_ID, ADDON_ID, FILE_NAME, FILE_DATE, FILE_LENGTH, DOWNLOAD_URL, FINGERPRINT) VALUES (:fileId, :addonId, :fileName, :fileDate, :fileLength, :downloadUrl, :fingerprint)")
    void insert(@Bind("addonId") long addonId, @BindBean File file);
    
    @SqlQuery("SELECT * FROM FILE WHERE FILE_ID = :fileId")
    File getFile(@Bind("fileId") long fileId);

    @SqlQuery("SELECT FILE_ID FROM FILE WHERE FILE_ID = :fileId")
    long getAddonId(@Bind("fileId") long fileId);
}
