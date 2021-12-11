package com.application.service.creator.reader.impl;

import com.application.entity.Booking;
import com.application.dao.exception.DaoException;
import com.application.service.creator.reader.BookingReader;
import com.application.service.exception.ServiceException;
import com.application.dao.BookingDao;
import com.application.dao.DaoFactory;
import com.application.dao.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookingReaderImpl implements BookingReader {
    private final Logger logger = LogManager.getLogger(BookingReaderImpl.class);

    @Override
    public List<Booking> readAllByHotel(Integer hotelId, Transaction transaction) throws ServiceException {
        BookingDao bookingDao = transaction.createDao(DaoFactory.getInstance().getBookingDao());
        try {
            return bookingDao.readAllBookingByHotel(hotelId);
        } catch (DaoException e) {
            logger.debug(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Booking read(Integer number, Integer hotelId, Transaction transaction) throws ServiceException {
        Booking booking;
        BookingDao bookingDao = transaction.createDao(DaoFactory.getInstance().getBookingDao());
        try {
            booking = bookingDao.read(number,hotelId);
        } catch (DaoException e) {
            logger.debug(e);
            throw new ServiceException(e);
        }
        return booking;
    }
}
