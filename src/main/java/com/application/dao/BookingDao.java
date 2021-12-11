package com.application.dao;

import com.application.entity.Booking;
import com.application.dao.exception.DaoException;

import java.util.List;

public interface BookingDao extends Dao<Booking> {
    @Override
    Integer create(Booking booking) throws DaoException;

    @Override
    Booking read(Integer... identity) throws DaoException;

    @Override
    boolean update(Booking booking) throws DaoException;

    @Override
    boolean delete(Integer... identity) throws DaoException;

    @Override
    List<Booking> read() throws DaoException;

    List<Booking> readAllBookingByHotel (int hotelId) throws DaoException;
}
