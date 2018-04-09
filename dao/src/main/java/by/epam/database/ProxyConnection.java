package by.epam.database;

import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class ProxyConnection extends ConnectionImpl {

    private Connection connection;

    protected ProxyConnection() {

    }

    @Override
    public void close() throws SQLException {
        super.close();
    }

    @Override
    public boolean isClosed() {
        return super.isClosed();
    }
}
