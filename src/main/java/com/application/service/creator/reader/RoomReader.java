package com.application.service.creator.reader;
import com.application.entity.Room;
import com.application.dao.Transaction;
import com.application.service.exception.ServiceException;

import java.util.List;

public interface RoomReader {
    List<Room> readAllByHotel (Integer hotelId, Transaction transaction) throws ServiceException;
}
