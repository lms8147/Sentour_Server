package com.tou4u.sentour.db.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A simple data source for getting database connections.
 */

public class SimpleDataSource {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    /**
     * Initializes the data source.
     *
     * @param driver
     * @param url
     * @param username
     * @param password
     */
    public static void init(String driver, String url, String username, String password) {

        SimpleDataSource.driver = driver;
        SimpleDataSource.url = url;
        SimpleDataSource.username = username;
        SimpleDataSource.password = password;

    }

    /**
     * Gets a connection to the database.
     *
     * @return the database connection
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Connection conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        return conn;
    }
}