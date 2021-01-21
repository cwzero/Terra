package com.liquidforte.terra.database;

import com.liquidforte.terra.model.AbstractDatabase;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerDatabase extends AbstractDatabase {
    public Connection getRootConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://postgres/", "postgres", "terra");
    }

    public void initDb() throws ClassNotFoundException, SQLException {
        var connection = getRootConnection();
        var result = connection.createStatement().executeQuery("SELECT DATNAME FROM PG_DATABASE");
        var tables = new ArrayList<String>();

        while (result.next()) {
            tables.add(result.getString(1));
        }

        if (!tables.stream().anyMatch(t -> t.toUpperCase().contentEquals("TERRA"))) {
            connection.createStatement().execute("CREATE DATABASE TERRA");
        }
    }

    public DataSource getDataSource() {
        while(true) {
            try {
                initDb();
                break;
            } catch (PSQLException ex) {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        var result = new PGSimpleDataSource();
        result.setServerNames(new String[]{"postgres"});
        result.setDatabaseName("terra");
        result.setUser("postgres");
        result.setPassword("terra");
        return result;
    }
}
