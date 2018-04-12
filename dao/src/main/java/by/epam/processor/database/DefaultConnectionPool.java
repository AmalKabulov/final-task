package by.epam.processor.database;

import by.epam.dao.exception.DaoException;
import by.epam.processor.CPException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultConnectionPool {


    private static DefaultConnectionPool instance;

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private ArrayBlockingQueue<Connection> free;
    private ArrayBlockingQueue<Connection> inUse;
    private DSConnector connector = DSConnector.getINSTANCE();
    private int poolSize;


    private DefaultConnectionPool() {
        poolSize = DSProperties.MAX_POOL_SIZE;
        free = new ArrayBlockingQueue<>(poolSize);
        inUse = new ArrayBlockingQueue<>(poolSize);
    }

    public static DefaultConnectionPool getInstance() {
        if (instance == null) {
            synchronized (DefaultConnectionPool.class) {
                if (instance == null) {
                    instance = new DefaultConnectionPool();
                }
            }
        }
        return instance;
    }


    public void init() throws DaoException {

        if (!initialized.get()) {
            for (int i = 0; i < poolSize; i++) {
                free.offer(connector.getConnection());
            }
            initialized.set(true);
        }

    }

    public Connection getConnection() throws CPException {

        if (!initialized.get()) {
            throw new CPException("Connection pool not initialized");
        }
        try {
            Connection connection = free.take();
            inUse.add(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new CPException("Could not get connection");
        }
    }





    private void closeConnections(ArrayBlockingQueue<Connection> queue) throws CPException {
        if (initialized.get()) {
            Connection connection;
            while ((connection = queue.poll()) != null) {
                try {
                    if (!connection.getAutoCommit()) ;
                    {
                        connection.commit();
                    }

                    ((DefaultConnection) connection).closeDown();
                } catch (SQLException e) {
                    throw new CPException("Could not close connection");
                }
            }
        }
    }

    public void destroy() throws CPException {
        if (initialized.get()) {
            closeConnections(free);
            closeConnections(inUse);

            initialized.set(false);
        }

    }


    public void freeConnection(Connection connection) throws CPException {
        try {
            if (connection.isClosed()) {
                throw new CPException("Connection is already closed. This is incorrect action");
            }

            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }

            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }

            if (inUse.contains(connection)) {
                if (!inUse.remove(connection)) {
                    throw new CPException("Could not remove connection from using connection pool.");
                }
            }

            if (!free.offer(connection)) {
                throw new CPException("Could not return connection to free connection pool.");
            }

        } catch (SQLException e) {
            throw new CPException("dsada");
        }
    }


}
