package com.application.service.creator.reader;

import com.application.entity.Order;
import com.application.dao.Transaction;
import com.application.service.exception.ServiceException;

import java.util.List;

public interface OrderReader {
    Order read(Integer identity, Transaction transaction) throws ServiceException;
    List<Order> readAllOrderByUserId(Integer userId, Transaction transaction) throws ServiceException;
    List<Order> readOrderByUserId(Integer userId, Integer page, Integer countPage, Transaction transaction) throws ServiceException;
}
