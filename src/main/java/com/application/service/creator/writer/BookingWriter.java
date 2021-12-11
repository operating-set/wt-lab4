package com.application.service.creator.writer;

import com.application.entity.Booking;
import com.application.dao.Transaction;
import com.application.service.exception.ServiceException;

public interface BookingWriter {
    boolean booking (Booking booking, Transaction transaction) throws ServiceException;
    boolean update (Booking booking, Transaction transaction) throws ServiceException;
    boolean delete (Booking booking, Transaction transaction) throws  ServiceException;
}
