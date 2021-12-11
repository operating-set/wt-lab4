package com.application.dao;

import com.application.entity.Hotel;
import com.application.dao.exception.DaoException;

import java.util.List;


public interface HotelDao extends Dao<Hotel> {
    @Override
    Integer create(Hotel hotel) throws DaoException;

    @Override
    Hotel read(Integer... identity) throws DaoException;

    @Override
    boolean update(Hotel hotel) throws DaoException;

    @Override
    boolean delete(Integer ... identity) throws DaoException;

    @Override
    List<Hotel> read() throws DaoException;

    List<Hotel> read(Integer page, Integer countHotelOnPage) throws DaoException;
}
