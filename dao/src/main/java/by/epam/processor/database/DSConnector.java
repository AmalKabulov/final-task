package by.epam.processor.database;

import by.epam.exception.DaoException;
import by.epam.processor.util.ReflectionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DSConnector {

    private static final DSConnector INSTANCE = new DSConnector();


    private DSConnector() {
    }


    public DefaultConnection getConnection() throws DaoException {
        try {
            ReflectionUtil.newClass(DSProperties.DRIVER);
            Connection connection = DriverManager.getConnection(DSProperties.URL, DSProperties.USERNAME, DSProperties.PASSWORD);
            return new DefaultConnection(connection);
        } catch (SQLException e) {
            throw new DaoException("Error while creating connection ", e);
        }

    }

    public static DSConnector getINSTANCE() {
        return INSTANCE;
    }
}
