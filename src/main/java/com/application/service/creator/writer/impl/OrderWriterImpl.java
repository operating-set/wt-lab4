package com.application.service.creator.writer.impl;

import com.application.entity.Order;
import com.application.dao.exception.DaoException;
import com.application.service.creator.writer.OrderWriter;
import com.application.service.exception.ServiceException;
import com.application.dao.DaoFactory;
import com.application.dao.OrderDao;
import com.application.dao.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderWriterImpl implements OrderWriter{
    private final Logger logger = LogManager.getLogger(OrderWriterImpl.class);
    @Override
    public boolean booking(Order order, Transaction transaction) throws ServiceException {
        OrderDao orderDao = transaction.createDao(DaoFactory.getInstance().getOrderDao());
        try {
            Integer orderId = orderDao.create(order);
            if (orderId != null){
                order.setId(orderId);
                return true;
            } else {
                return false;
            }
        } catch (DaoException e) {
            logger.error("Can't booking",e);
            throw new ServiceException("Can't booking",e);
        }
    }

    @Override
    public boolean update(Order order, Transaction transaction) throws ServiceException {
        OrderDao orderDao = transaction.createDao(DaoFactory.getInstance().getOrderDao());
        try {
            return orderDao.update(order);
        } catch (DaoException e) {
            logger.error("Can't booking",e);
            throw new ServiceException("Can't booking",e);
        }
    }
}
