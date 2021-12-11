package com.application.dao.impl;

import com.application.entity.User;
import com.application.dao.UserDao;
import com.application.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final String SQL_INSERT = "INSERT INTO `users` (`email`, `password`, `role`) VALUES (?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM `users` WHERE `users`.`id` = ?";
    private static final String SQL_SELECT_ALL = "SELECT `id`, `email`, `password`, `role` FROM `users`";
    private static final String SQL_SELECT_ONE = "SELECT `id`, `email`, `password`, `role` FROM `users` WHERE `users`.`id` = ?";
    private static final String SQL_SELECT_EMAIL = "SELECT `id`, `email`, `password`, `role` FROM `users` WHERE `users`.`email` = ?";
    private static final String SQL_UPDATE = "UPDATE `users` SET `email`=?,`password`=?,`role`=? WHERE `users`.`id` = ?";

    @Override
    public Integer create(User user) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getValue());
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
    public User read(Integer... identity) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ONE);
            statement.setInt(1, identity[0]);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()){
                    user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setEmail(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setRole(resultSet.getInt(4));
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
        return user;
    }

    @Override
    public User read(String email) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_EMAIL);
            statement.setString(1, email);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()){
                    user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setEmail(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setRole(resultSet.getInt(4));
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
        return user;
    }

    @Override
    public boolean update(User user) throws DaoException {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getValue());
            statement.setInt(4, user.getId());
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
    public boolean delete(Integer ... identity) throws DaoException { // make return boolean
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
    public List<User> read() throws DaoException {
        List<User> userList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setEmail(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setRole(resultSet.getInt(4));
                    userList.add(user);
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
        return userList;
    }

    @Override
    public void setConnection(Connection connection) {
        super.connection = connection;
    }
}
