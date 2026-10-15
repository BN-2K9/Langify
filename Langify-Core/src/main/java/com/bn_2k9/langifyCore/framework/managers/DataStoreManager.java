package com.bn_2k9.langifyCore.framework.managers;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.utils.DataStoreTypes;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.io.File;
import java.sql.*;
import java.util.UUID;

public class DataStoreManager {

    @Getter
    static DataStoreTypes dataStoreTypes;

    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    public void init() {
        dataStoreTypes = LangifyCore.getInstance().getConfig().getDataStoreType();

        if (dataStoreTypes == DataStoreTypes.MySql) {
            // Create the database config.
            config.setJdbcUrl(LangifyCore.getInstance().getConfig().getDataStoreJdbcUrl());
            config.setUsername(LangifyCore.getInstance().getConfig().getDataStoreUsername());
            config.setPassword(LangifyCore.getInstance().getConfig().getDataStorePassword());
            config.addDataSourceProperty("checkPrepStmts", true);
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );

            // Create the datasource.
            dataSource = new HikariDataSource(config);
        }

        // Create the database table.
        Connection connection = getConnection();
        if (connection == null) {
            LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to get Database connection. Something went wrong.");
            return;
        }
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS users (uuid TEXT PRIMARY KEY, language TEXT)")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to create database table: users Error: " + e.getMessage());
        }
    }

    private static Connection getConnection() {

        if (dataStoreTypes == DataStoreTypes.MySql) {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to create DB connection. (Please check Mysql Settings in the config.)");
            }
        } else {
            File sqlLiteDb = LangifyCore.getInstance().getFileInterface().getSqlLiteFile();
            String connectionUrl = "jdbc:sqlite:" + sqlLiteDb.getAbsolutePath();
            try {
                return DriverManager.getConnection(connectionUrl);
            } catch (SQLException e) {
                LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to connect to SqlLite DB: " + e.getMessage());
            }
        }

        return null;
    }

    /**
     * Get the language filename for the player.
     * @param uuid The player to get the language filename from.
     * @return The language filename.
     */
    public String getSelectedLanguageForPlayer(UUID uuid) {

        Connection connection = getConnection();
        if (connection == null) {
            LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to get Database connection. Something went wrong.");
            return null;
        }
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT language FROM users WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("language");
                }

                return null;
            }

        } catch (SQLException e) {
            LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to get database value in table: users Error:" + e.getMessage());
        }

        return null;
    }

    public void setSelectedLanguageForPlayer(UUID uuid, String language) {
        Connection connection = getConnection();
        if (connection == null) {
            LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to get Database connection. Something went wrong.");
        }
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT OR REPLACE INTO users (uuid, language) VALUES (?, ?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, language);
            preparedStatement.execute();
        } catch (SQLException e) {
            LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to set database value in table: users Error: " + e.getMessage());
        }
    }

}
