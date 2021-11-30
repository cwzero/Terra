package com.liquidforte.terra.api.database;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ForgeDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS FORGE(MINECRAFT_VERSION VARCHAR PRIMARY KEY, FORGE_VERSION VARCHAR NOT NULL)")
    void createTable();

    @SqlQuery("SELECT FORGE_VERSION FROM FORGE WHERE MINECRAFT_VERSION = :minecraftVersion")
    String getForgeVersion(@Bind("minecraftVersion") String minecraftVersion);

    @SqlUpdate("MERGE INTO FORGE (MINECRAFT_VERSION, FORGE_VERSION) VALUES (:minecraftVersion, :forgeVersion)")
    void insert(@Bind("minecraftVersion") String minecraftVersion, @Bind("forgeVersion") String forgeVersion);
}
