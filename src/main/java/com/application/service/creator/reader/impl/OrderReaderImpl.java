package com.application.service.creator.reader.impl;

import com.application.entity.Order;
import com.application.dao.exception.DaoException;
import com.application.service.creator.reader.OrderReader;
import com.application.service.exception.ServiceException;
import com.application.dao.DaoFactory;
import com.application.dao.OrderDao;
import com.application.dao.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderReaderImpl implements OrderReader {
    private final Logger logger = LogManager.getLogger(OrderReaderImpl.class);
    @Override
    public Order read(Integer identity, Transaction transaction) throws ServiceException {
        Order order;
        OrderDao orderDao = transaction.createDao(DaoFactory.getInstance().getOrderDao());
        try {
            order = orderDao.read(identity);
        } catch (DaoException e) {
            logger.debug(e);
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public List<Order> readAllOrderByUserId(Integer userId, Transaction transaction) throws ServiceException {
        OrderDao orderDao = transaction.createDao(DaoFactory.getInstance().getOrderDao());
        List<Order> orderList;
        try {
            orderList = orderDao.readByUser(userId);
        } catch (DaoException e) {
            logger.debug(e);
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public List<Order> readOrderByUserId(Integer userId, Integer page, Integer countPage, Transaction transaction) throws ServiceException {
        OrderDao orderDao = transaction.createDao(DaoFactory.getInstance().getOrderDao());
        List<Order> orderList;
        try {
            orderList = orderDao.read(userId,page,countPage);
        } catch (DaoException e) {
            logger.debug(e);
            throw new ServiceException(e);
        }
        return orderList;
    }
}
