package by.epam.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DSConnection {


    public DSConnection() {
    }


    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DSProperties.DRIVER);

        Connection connection = DriverManager.getConnection(DSProperties.URL, DSProperties.USERNAME, DSProperties.PASSWORD);

        return new ProxyConnection();
    }
}
