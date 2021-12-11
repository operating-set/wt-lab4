package com.application.service.creator.reader;

import com.application.entity.Hotel;
import com.application.dao.Transaction;
import com.application.service.exception.ServiceException;

import java.util.List;

public interface HotelReader {
    Hotel read(Integer identity, Transaction transaction) throws ServiceException;

    List<Hotel> readAll(Transaction transaction) throws ServiceException;

    List<Hotel> read(Integer page, Integer countHotelOnPage, Transaction transaction) throws ServiceException;
}
