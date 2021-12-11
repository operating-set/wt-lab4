package com.application.service.wrapper;

import com.application.dao.exception.DaoException;
import com.application.dao.pool.ConnectionPool;
import com.application.service.exception.ServiceException;

public class InitWrapper {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final InitWrapper instance = new InitWrapper();

    private InitWrapper(){}

    public static InitWrapper getInstance() {
        return instance;
    }

    public void init () throws ServiceException {
        try {
            connectionPool.init();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}
