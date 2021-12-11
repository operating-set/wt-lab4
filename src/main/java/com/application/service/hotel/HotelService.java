package com.application.service.hotel;

import com.application.entity.Hotel;
import com.application.entity.Room;
import com.application.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface HotelService {
    List<Hotel> readAll() throws ServiceException;
    List<Hotel> read(Integer page, Integer countHotel) throws ServiceException;
    List<Room> roomListForBooking(Integer hotelId, Date dateArrival, Date dateDepartment, Integer typeOfComfort, Integer typeOfAllocation) throws ServiceException;
}
