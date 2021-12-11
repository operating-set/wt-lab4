package com.application.service.creator.writer;

import com.application.entity.Order;
import com.application.dao.Transaction;
import com.application.service.exception.ServiceException;

public interface OrderWriter {
    boolean booking (Order order, Transaction transaction) throws ServiceException;
    boolean update (Order order, Transaction transaction) throws  ServiceException;
}
