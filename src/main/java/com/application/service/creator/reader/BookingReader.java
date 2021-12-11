package com.application.service.creator.reader;

import com.application.entity.Booking;
import com.application.dao.Transaction;
import com.application.service.exception.ServiceException;

import java.util.List;

public interface BookingReader {
    List<Booking> readAllByHotel (Integer hotelId, Transaction transaction) throws ServiceException;
    Booking read(Integer number, Integer hotelId, Transaction transaction) throws ServiceException;
}
