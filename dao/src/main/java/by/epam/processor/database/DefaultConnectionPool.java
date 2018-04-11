package by.epam.processor.database;

import by.epam.exception.DaoException;
import by.epam.processor.CPException;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;

public class DefaultConnectionPool {


    private static DefaultConnectionPool instance;
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

        for (int i = 0; i < poolSize; i++) {
            free.offer(connector.getConnection());
        }

    }

    public Connection getConnection() throws CPException {
        try {
            Connection connection = free.take();
            inUse.add(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new CPException("dsada");
        }
    }


    private void closeConnections(ArrayBlockingQueue<Connection> queue) {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            

        }

    }


}
