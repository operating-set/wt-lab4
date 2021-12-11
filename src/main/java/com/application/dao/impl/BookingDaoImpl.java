package com.application.dao.impl;

import com.application.entity.Booking;
import com.application.dao.BookingDao;
import com.application.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl extends BaseDaoImpl implements BookingDao {
    private final Logger logger = LogManager.getLogger(BookingDaoImpl.class);
    private static final String SQL_INSERT = "INSERT INTO `booking`(`number`, `hotel_id`, `date_arrival`, `date_department`, `status`) VALUES (?,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM `booking` WHERE `number` = ? AND `hotel_id` = ?";
    private static final String SQL_SELECT_ALL = "SELECT `number`, `hotel_id`, `date_arrival`, `date_department`, `status` FROM `booking`";
    private static final String SQL_SELECT_ALL_BY_HOTEL = "SELECT `number`, `hotel_id`, `date_arrival`, `date_department`, `status` FROM `booking` WHERE `hotel_id` = ?";
    private static final String SQL_SELECT_ONE = "SELECT `number`, `hotel_id`, `date_arrival`, `date_department`, `status` FROM `booking` WHERE `number` = ? AND `hotel_id` = ?";
    private static final String SQL_UPDATE = "UPDATE `booking` SET `date_arrival`=?,`date_department`=?, `status`=? WHERE `number` = ? AND `hotel_id` = ?";

    @Override
    public Integer create(Booking booking) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setInt(1, booking.getNumber());
            statement.setInt(2, booking.getHotelId());
            statement.setDate(3, booking.getDateArrival());
            statement.setDate(4, booking.getDateDepartment());
            statement.setInt(5, booking.getStatus());
            statement.executeUpdate();
            return booking.getNumber();
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
    public Booking read(Integer... identity) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Booking booking = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ONE);
            statement.setInt(1, identity[0]);
            statement.setInt(2, identity[1]);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    booking = new Booking();
                    booking.setNumber(resultSet.getInt(1));
                    booking.setHotelId(resultSet.getInt(2));
                    booking.setDateArrival(resultSet.getDate(3));
                    booking.setDateDepartment(resultSet.getDate(4));
                    booking.setStatus(resultSet.getInt(5));
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
        return booking;
    }

    @Override
    public boolean update(Booking booking) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(4, booking.getNumber());
            statement.setInt(5, booking.getHotelId());
            statement.setDate(1, booking.getDateArrival());
            statement.setDate(2, booking.getDateDepartment());
            statement.setInt(3, booking.getStatus());
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
    public boolean delete(Integer... identity) throws DaoException {
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
    public List<Booking> read() throws DaoException {
        List<Booking> bookingList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Booking booking = new Booking();
                    booking.setNumber(resultSet.getInt(1));
                    booking.setHotelId(resultSet.getInt(2));
                    booking.setDateArrival(resultSet.getDate(3));
                    booking.setDateDepartment(resultSet.getDate(4));
                    booking.setStatus(resultSet.getInt(5));
                    bookingList.add(booking);
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
        return bookingList;
    }

    @Override
    public List<Booking> readAllBookingByHotel(int hotelId) throws DaoException {
        List<Booking> bookingList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_BY_HOTEL);
            statement.setInt(1, hotelId);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Booking booking = new Booking();
                    booking.setNumber(resultSet.getInt(1));
                    booking.setHotelId(resultSet.getInt(2));
                    booking.setDateArrival(resultSet.getDate(3));
                    booking.setDateDepartment(resultSet.getDate(4));
                    booking.setStatus(resultSet.getInt(5));
                    bookingList.add(booking);
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
        return bookingList;
    }

    @Override
    public void setConnection(Connection connection) {
        super.connection = connection;
    }
}
