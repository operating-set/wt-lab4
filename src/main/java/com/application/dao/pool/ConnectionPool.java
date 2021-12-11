package com.application.dao.pool;

import com.application.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();

    private static final String DB_URL_PROP = "db.url";
    private static final String DB_USER_PROP = "db.user";
    private static final String DB_PASSWORD_PROP = "db.password";
    private static final String DB_DRIVER_PROP = "db.jdbc-driver";
    private static final String DB_POOL_SIZE = "db.pool-size";
    private static final String DB_MAX_SIZE = "db.max-size";
    private static final String DB_CONNECTION_TIMEOUT="db.connection-timeout";
    private int maxSize;
    private int checkConnectionTimeout;

    private final BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private final Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();
    private Properties dbProperties;
    private final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final ReentrantLock locker = new ReentrantLock();

    private ConnectionPool() {}

    public Connection getConnection() throws DaoException {
        locker.lock();
        PooledConnection connection = null;
        while(connection == null) {
            try {
                if(!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if(!connection.isValid(checkConnectionTimeout)) {
                        try {
                            connection.getConnection().close();
                        } catch(SQLException e) {
                            logger.warn("Connection not closed",e);
                        }
                        connection = null;
                    }
                } else if(usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
                    logger.error("The limit of number of database connections is exceeded");
                    locker.unlock();
                    throw new DaoException("The limit of number of database connections is exceeded");
                }
            } catch(InterruptedException | SQLException e) {
                logger.error("It is impossible to connect to a database", e);
                locker.unlock();
                throw new DaoException(e);
            }
        }
        usedConnections.add(connection);
        logger.debug(String.format("Connection was received from pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
        locker.unlock();
        return connection;
    }

     void freeConnection(PooledConnection connection) {
        locker.lock();
        try {
            if(connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.debug(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
            }
        } catch(SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch(SQLException e2) {
                logger.warn("Connection not closed");
            }
        } finally {
            locker.unlock();
        }
    }

    public void init() throws DaoException {
        locker.lock();
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            dbProperties = new Properties();
            dbProperties.load(input);
            destroy();
            Class.forName(dbProperties.getProperty(DB_DRIVER_PROP));
            this.maxSize = Integer.parseInt(dbProperties.getProperty(DB_MAX_SIZE));
            this.checkConnectionTimeout = Integer.parseInt(dbProperties.getProperty(DB_CONNECTION_TIMEOUT));
            for(int counter = 0; counter < Integer.parseInt(dbProperties.getProperty(DB_POOL_SIZE)); counter++) {
                freeConnections.put(createConnection());
            }
        } catch(ClassNotFoundException | InterruptedException | IOException | NumberFormatException e) {
            logger.fatal("It is impossible to initialize connection pool", e);
            throw new DaoException(e);
        } finally {
            locker.unlock();
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private PooledConnection createConnection() throws DaoException {
        locker.lock();
        try {
            return new PooledConnection(DriverManager.getConnection(dbProperties.getProperty(DB_URL_PROP),
                    dbProperties.getProperty(DB_USER_PROP), dbProperties.getProperty(DB_PASSWORD_PROP)));
        } catch (SQLException e){
            logger.warn("Cant create connection",e);
            throw new DaoException("Cant create connection",e);
        } finally {
            locker.unlock();
        }

    }

    public void destroy() throws DaoException {
        locker.lock();
        try {
            usedConnections.addAll(freeConnections);
            freeConnections.clear();
            for(PooledConnection connection : usedConnections) {
                    connection.getConnection().close();
            }
            usedConnections.clear();
        } catch(SQLException e) {
            logger.warn("Connection not close");
            throw new DaoException("Connection not close",e);
        } finally {
            locker.unlock();
        }
    }
}
