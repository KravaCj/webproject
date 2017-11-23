package com.mysite.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {

    private static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.jdbc.Driver";


    public Connection getConnection() throws ClassNotFoundException {

         Class.forName(DRIVER);

        try {
            con = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
