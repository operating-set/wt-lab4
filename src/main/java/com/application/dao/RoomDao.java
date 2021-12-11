package com.application.dao;

import com.application.entity.Room;
import com.application.dao.exception.DaoException;

import java.util.List;

public interface RoomDao extends Dao<Room>{

    /**
     * @param room
     * @return number of create room
     * @throws DaoException
     */
    Integer create(Room room) throws DaoException;

    /**
     * @param identity - first identity - number of room, second - hotelId
     * @return
     * @throws DaoException
     */
    Room read(Integer ... identity) throws DaoException;

    boolean update(Room room) throws DaoException;

    /**
     * @param identity - first identity - number of room, second - hotelId
     * @return
     * @throws DaoException
     */
    boolean delete(Integer ... identity) throws DaoException;

    List<Room> read() throws DaoException;

    List<Room> readAllRoomsByHotel (int hotelId) throws DaoException;
}
