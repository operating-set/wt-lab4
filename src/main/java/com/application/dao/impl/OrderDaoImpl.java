package com.application.dao.impl;

import com.application.entity.Order;
import com.application.dao.OrderDao;
import com.application.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {
    private final Logger logger = LogManager.getLogger(OrderDaoImpl.class);
    private static final String SQL_INSERT = "INSERT INTO `orders`(`user_id`, `hotel_id`, `number`, `date_arrival`, `date_department`, `status`) VALUES (?,?,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM `orders` WHERE `id` = ?";
    private static final String SQL_SELECT_ALL = "SELECT `id`, `user_id`, `hotel_id`, `number`, `date_arrival`, `date_department`, `status` FROM `orders`";
    private static final String SQL_SELECT_BY_USER = "SELECT `id`, `user_id`, `hotel_id`, `number`, `date_arrival`, `date_department`, `status` FROM `orders` WHERE `user_id` = ?";
    private static final String SQL_SELECT_BY_USER_LIMIT_OFFSET = "SELECT `id`, `user_id`, `hotel_id`, `number`, `date_arrival`, `date_department`, `status` FROM `orders` WHERE `user_id` = ? LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ONE = "SELECT `id`, `user_id`, `hotel_id`, `number`, `date_arrival`, `date_department`, `status` FROM `orders` WHERE `id` = ?";
    private static final String SQL_UPDATE = "UPDATE `orders` SET `user_id`=?,`hotel_id`=?,`number`=?,`date_arrival`=?,`date_department`=?,`status`=? WHERE `id` = ?";

    @Override
    public Integer create(Order order) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,order.getUserId());
            statement.setInt(2,order.getHotelId());
            statement.setInt(3,order.getNumber());
            statement.setDate(4,order.getDateArrival());
            statement.setDate(5,order.getDateDepartment());
            statement.setInt(6,order.getStatus());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
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
    public Order read(Integer... identity) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Order order = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ONE);
            statement.setInt(1, identity[0]);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()){
                    order = new Order();
                    order.setId(resultSet.getInt(1));
                    order.setUserId(resultSet.getInt(2));
                    order.setHotelId(resultSet.getInt(3));
                    order.setNumber(resultSet.getInt(4));
                    order.setDateArrival(resultSet.getDate(5));
                    order.setDateDepartment(resultSet.getDate(6));
                    order.setStatus(resultSet.getInt(7));
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
        return order;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(7,order.getId());
            statement.setInt(1,order.getUserId());
            statement.setInt(2, order.getHotelId());
            statement.setInt(3, order.getNumber());
            statement.setDate(4, order.getDateArrival());
            statement.setDate(5, order.getDateDepartment());
            statement.setInt(6, order.getStatus());
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
    public List<Order> read() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt(1));
                    order.setUserId(resultSet.getInt(2));
                    order.setHotelId(resultSet.getInt(3));
                    order.setNumber(resultSet.getInt(4));
                    order.setDateArrival(resultSet.getDate(5));
                    order.setDateDepartment(resultSet.getDate(6));
                    order.setStatus(resultSet.getInt(7));
                    orderList.add(order);
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
        return orderList;
    }

    @Override
    public List<Order> read(Integer userId, Integer page, Integer countOrderOnPage) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_BY_USER_LIMIT_OFFSET);
            statement.setInt(1, userId);
            statement.setInt(2, countOrderOnPage);
            statement.setInt(3, (page-1)*countOrderOnPage);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt(1));
                    order.setUserId(resultSet.getInt(2));
                    order.setHotelId(resultSet.getInt(3));
                    order.setNumber(resultSet.getInt(4));
                    order.setDateArrival(resultSet.getDate(5));
                    order.setDateDepartment(resultSet.getDate(6));
                    order.setStatus(resultSet.getInt(7));
                    orderList.add(order);
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
        return orderList;
    }

    @Override
    public List<Order> readByUser(Integer userId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_BY_USER);
            statement.setInt(1, userId);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt(1));
                    order.setUserId(resultSet.getInt(2));
                    order.setHotelId(resultSet.getInt(3));
                    order.setNumber(resultSet.getInt(4));
                    order.setDateArrival(resultSet.getDate(5));
                    order.setDateDepartment(resultSet.getDate(6));
                    order.setStatus(resultSet.getInt(7));
                    orderList.add(order);
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
        return orderList;
    }

    @Override
    public void setConnection(Connection connection) {
        super.connection = connection;
    }
}
