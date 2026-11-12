package com.taskpro.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Common JDBC connection provider used by all DAO classes. */
public final class DBConnection {
    private static final Properties CONFIG = new Properties();
    static {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("Missing db.properties");
            CONFIG.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    private DBConnection() {}

    private static String configured(String envName, String property) {
        String fromEnv = System.getenv(envName);
        return fromEnv != null ? fromEnv : CONFIG.getProperty(property, "");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            configured("TASKPRO_DB_URL", "db.url"),
            configured("TASKPRO_DB_USER", "db.user"),
            configured("TASKPRO_DB_PASSWORD", "db.password"));
    }
}
