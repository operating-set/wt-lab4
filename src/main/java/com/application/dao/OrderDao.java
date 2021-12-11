package com.application.dao;

import com.application.entity.Order;
import com.application.dao.exception.DaoException;

import java.util.List;

public interface OrderDao extends Dao<Order>{
    @Override
    Integer create(Order order) throws DaoException;

    @Override
    Order read(Integer ... identity) throws DaoException;

    @Override
    boolean update(Order order) throws DaoException;

    @Override
    boolean delete(Integer ... identity) throws DaoException;

    @Override
    List<Order> read() throws DaoException;

    List<Order> read(Integer userId, Integer page, Integer countOrderOnPage) throws DaoException;

    List<Order> readByUser(Integer userId) throws DaoException;

}
