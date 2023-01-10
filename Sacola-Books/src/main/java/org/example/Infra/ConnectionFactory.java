package org.example.Infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private ConnectionFactory() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://sacolabooks-rds-mysql.c0z9tie9fbsp.sa-east-1.rds.amazonaws.com:3306/sacolaBooks", "masterAdmin", "Postedeenergia3");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
