package com.application.service.hotel.impl;

import com.application.entity.Booking;
import com.application.entity.Hotel;
import com.application.entity.Room;
import com.application.dao.Transaction;
import com.application.dao.TransactionCreator;
import com.application.dao.exception.DaoException;
import com.application.service.creator.CreatorFactory;
import com.application.service.creator.reader.BookingReader;
import com.application.service.creator.reader.HotelReader;
import com.application.service.creator.reader.RoomReader;
import com.application.service.exception.ServiceException;
import com.application.service.hotel.HotelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class HotelServiceImpl implements HotelService {
    private final Logger logger = LogManager.getLogger(HotelServiceImpl.class);
    Transaction transaction;

    @Override
    public List<Hotel> readAll() throws ServiceException {
        HotelReader hotelReader = CreatorFactory.getInstance().getHotelReader();
        List<Hotel> hotelList;
        try {
            transaction = createTransaction();
            hotelList = hotelReader.readAll(transaction);
            transaction.commit();
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                logger.error("Error with rollback", e);
                throw new ServiceException("Error with rollback");
            }
            logger.error("Error read from DB", e);
            throw new ServiceException("Error read from DB" + e);
        } catch (DaoException e) {
            logger.error("Error with commit", e);
            throw new ServiceException("Error with commit");
        }
        return hotelList;
    }

    @Override
    public List<Hotel> read(Integer page, Integer countPage) throws ServiceException {
        HotelReader hotelReader = CreatorFactory.getInstance().getHotelReader();
        List<Hotel> hotelList;
        try {
            transaction = createTransaction();
            hotelList = hotelReader.read(page, countPage, transaction);
            transaction.commit();
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                logger.error("Error with rollback", e);
                throw new ServiceException("Error with rollback");
            }
            logger.error("Error read from DB", e);
            throw new ServiceException("Error read from DB" + e);
        } catch (DaoException e) {
            logger.error("Error with commit", e);
            throw new ServiceException("Error with commit");
        }
        return hotelList;
    }

    @Override
    public List<Room> roomListForBooking(Integer hotelId, Date dateArrival, Date dateDepartment, Integer typeOfComfort, Integer typeOfAllocation) throws ServiceException {
        HotelReader hotelReader = CreatorFactory.getInstance().getHotelReader();
        RoomReader roomReader = CreatorFactory.getInstance().getRoomReader();
        BookingReader bookingReader = CreatorFactory.getInstance().getBookingReader();
        List<Room> roomList = new ArrayList<>();
        try {
            transaction = createTransaction();
            Hotel hotel = hotelReader.read(hotelId, transaction);
            if (hotel != null && !hotel.getListRoom().isEmpty()) {
                List<Room> roomListByHotel = roomReader.readAllByHotel(hotelId, transaction);
                List<Booking> bookingList = bookingReader.readAllByHotel(hotelId, transaction);
                roomList = createListRoom(roomListByHotel,bookingList, dateArrival,dateDepartment,typeOfComfort,typeOfAllocation);
            }
            transaction.commit();
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                logger.error("Error with rollback", e);
                throw new ServiceException("Error with rollback");
            }
            logger.error("Error read from DB", e);
            throw new ServiceException("Error read from DB" + e);
        } catch (DaoException e) {
            logger.error("Error with commit", e);
            throw new ServiceException("Error with commit");
        }
        return roomList;
    }

    private List<Room> createListRoom(List<Room> roomListByHotel, List<Booking> bookingList, Date dateArrival, Date dateDepartment, Integer typeOfComfort, Integer typeOfAllocation) {
        List<Room> roomListSort = new ArrayList<>();
        for (Room room : roomListByHotel) {
            if ((room.getTypeComfort() == typeOfComfort) &&
                    (( (room.getTypeAllocation() <= typeOfAllocation)) || (room.getTypeAllocation()==17 && typeOfAllocation==16)) ) {
                roomListSort.add(room);
            }
        }
        for (Booking booking : bookingList) {
            if ( ( (dateArrival.compareTo(booking.getDateArrival()) >= 0) && (dateArrival.compareTo(booking.getDateDepartment()) <= 0) )
                    || ( (dateDepartment.compareTo(booking.getDateArrival()) >= 0) && (dateDepartment.compareTo(booking.getDateDepartment()) <= 0) ) )  {
                removeRoom(roomListSort,booking);
            }
        }
        return roomListSort;
    }

    private void removeRoom(List<Room> roomListSort, Booking booking) {
        Room room;
        for (int i = 0; i < roomListSort.size()-1; i++){
            room = roomListSort.get(i);
            if (room.getNumber() == booking.getNumber() && room.getHotelId() == booking.getHotelId()){
                roomListSort.remove(room);
            }
        }
    }

    private Transaction createTransaction() throws ServiceException {
        try {
            TransactionCreator transactionCreator = new TransactionCreator();
            return transactionCreator.create();
        } catch (DaoException e) {
            logger.error("Can't create transaction");
            throw new ServiceException("Can't create transaction");
        }
    }
}
