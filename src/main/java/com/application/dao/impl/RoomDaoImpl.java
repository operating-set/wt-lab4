package com.application.dao.impl;

import com.application.entity.Room;
import com.application.dao.RoomDao;
import com.application.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl extends BaseDaoImpl implements RoomDao {
    private final Logger logger = LogManager.getLogger(RoomDaoImpl.class);
    private static final String SQL_INSERT = "INSERT INTO `rooms`(`number`, `hotel_id`, `type_of_allocation`, `type_of_comfort`) VALUES (?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM `rooms` WHERE `number` = ? AND `hotel_id` = ?";
    private static final String SQL_SELECT_ALL = "SELECT `number`, `hotel_id`, `type_of_allocation`, `type_of_comfort` FROM `rooms`";
    private static final String SQL_SELECT_ALL_BY_HOTEL = "SELECT `number`, `hotel_id`, `type_of_allocation`, `type_of_comfort` FROM `rooms` WHERE `hotel_id` = ?";
    private static final String SQL_SELECT_ONE = "SELECT `number`, `hotel_id`, `type_of_allocation`, `type_of_comfort` FROM `rooms` WHERE `number` = ? AND `hotel_id` = ?";
    private static final String SQL_UPDATE = "UPDATE `rooms` SET `type_of_allocation`=?,`type_of_comfort`=? WHERE `number` = ? AND `hotel_id` = ?";

    @Override
    public Integer create(Room room) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setInt(1,room.getNumber());
            statement.setInt(2,room.getHotelId());
            statement.setInt(3,room.getTypeAllocation());
            statement.setInt(4,room.getTypeComfort());
            statement.executeUpdate();
            return room.getNumber();
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
    }

    @Override
    public Room read(Integer ... identity) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Room room = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ONE);
            statement.setInt(1, identity[0]);
            statement.setInt(2, identity[1]);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()){
                    room = new Room();
                    room.setNumber(resultSet.getInt(1));
                    room.setHotelId(resultSet.getInt(2));
                    room.setTypeAllocation(resultSet.getInt(3));
                    room.setTypeComfort(resultSet.getInt(4));
                }
            }
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
        return room;
    }

    @Override
    public boolean update(Room room) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(3,room.getNumber());
            statement.setInt(4,room.getHotelId());
            statement.setInt(1,room.getTypeAllocation());
            statement.setInt(2,room.getTypeComfort());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
    }

    @Override
    public boolean delete(Integer ... identity) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, identity[0]);
            statement.setInt(2, identity[1]);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
    }

    @Override
    public List<Room> read() throws DaoException {
        List<Room> roomList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Room room = new Room();
                    room.setNumber(resultSet.getInt(1));
                    room.setHotelId(resultSet.getInt(2));
                    room.setTypeAllocation(resultSet.getInt(3));
                    room.setTypeComfort(resultSet.getInt(4));
                    roomList.add(room);
                }
            }
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
        return roomList;
    }

    @Override
    public void setConnection(Connection connection) {
        super.connection = connection;
    }

    @Override
    public List<Room> readAllRoomsByHotel(int hotelId) throws DaoException {
        List<Room> roomList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_BY_HOTEL);
            statement.setInt(1, hotelId);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Room room = new Room();
                    room.setNumber(resultSet.getInt(1));
                    room.setHotelId(resultSet.getInt(2));
                    room.setTypeAllocation(resultSet.getInt(3));
                    room.setTypeComfort(resultSet.getInt(4));
                    roomList.add(room);
                }
            }
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
        return roomList;
    }
}
