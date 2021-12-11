package com.application.dao.impl;

import com.application.entity.UserInfo;
import com.application.dao.UserInfoDao;
import com.application.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDaoImpl extends BaseDaoImpl implements UserInfoDao {
    private final Logger logger = LogManager.getLogger(UserInfoDaoImpl.class);
    private static final String SQL_INSERT = "INSERT INTO `users_info`(`user_id`, `name`, `surname`, `middle_name`, `phone`, `passport`, `date_of_birthday`, `sex`,`code_country`) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM `users_info` WHERE `users_info`.`user_id` = ?";
    private static final String SQL_SELECT_ALL = "SELECT `user_id`, `name`, `surname`, `middle_name`, `phone`, `passport`, `date_of_birthday`, `sex`, `code_country` FROM `users_info`";
    private static final String SQL_SELECT_ONE = "SELECT `user_id`, `name`, `surname`, `middle_name`, `phone`, `passport`, `date_of_birthday`, `sex`, `code_country` FROM `users_info` WHERE `users_info`.`user_id` = ?";
    private static final String SQL_UPDATE = "UPDATE `users_info` SET `name`=?,`surname`=?,`middle_name`=?,`phone`=?,`passport`=?,`date_of_birthday`=?,`sex`=?,`code_country`=? WHERE `users_info`.`user_id` = ?";

    @Override
    public Integer create(UserInfo userInfo) throws DaoException { //TODO check create
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT, Statement.NO_GENERATED_KEYS);
            statement.setInt(1, userInfo.getId());
            statement.setString(2, userInfo.getName());
            statement.setString(3, userInfo.getSurname());
            statement.setString(4, userInfo.getMiddleName());
            statement.setLong(5, executeNumber(userInfo.getPhone()));
            statement.setString(6, userInfo.getPassport());
            statement.setDate(7, userInfo.getDateBirthDay());
            statement.setBoolean(8, userInfo.getSex());
            statement.setString(9, String.valueOf(userInfo.getCodeCountry()));
            statement.executeUpdate();
            return userInfo.getId();
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
    public UserInfo read(Integer... identity) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserInfo userInfo = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ONE);
            statement.setInt(1, identity[0]);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()){
                    userInfo = new UserInfo();
                    userInfo.setId(resultSet.getInt(1));
                    userInfo.setName(resultSet.getString(2));
                    userInfo.setSurname(resultSet.getString(3));
                    userInfo.setMiddleName(resultSet.getString(4));
                    userInfo.setPhone(resultSet.getLong(5));
                    userInfo.setPassport(resultSet.getString(6));
                    userInfo.setDateBirthDay(resultSet.getDate(7));
                    userInfo.setSex(resultSet.getBoolean(8));
                    userInfo.setCodeCountry(resultSet.getString(9));
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
        return userInfo;
    }

    @Override
    public boolean update(UserInfo userInfo) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(9, userInfo.getId());
            statement.setString(1, userInfo.getName());
            statement.setString(2, userInfo.getSurname());
            statement.setString(3, userInfo.getMiddleName());
            statement.setLong(4, executeNumber(userInfo.getPhone()));
            statement.setString(5, userInfo.getPassport());
            statement.setDate(6, userInfo.getDateBirthDay());
            statement.setBoolean(7, userInfo.getSex());
            statement.setString(8, String.valueOf(userInfo.getCodeCountry()));
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
    public List<UserInfo> read() throws DaoException {
        List<UserInfo> userInfoList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(resultSet.getInt(1));
                    userInfo.setName(resultSet.getString(2));
                    userInfo.setSurname(resultSet.getString(3));
                    userInfo.setMiddleName(resultSet.getString(4));
                    userInfo.setPhone(resultSet.getLong(5));
                    userInfo.setPassport(resultSet.getString(6));
                    userInfo.setDateBirthDay(resultSet.getDate(7));
                    userInfo.setSex(resultSet.getBoolean(8));
                    userInfo.setCodeCountry(resultSet.getString(9));
                    userInfoList.add(userInfo);
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
        return userInfoList;
    }

    @Override
    public void setConnection(Connection connection) {
        super.connection = connection;
    }

    private Long executeNumber(String phoneNumber) {
        StringBuilder phoneNumberProc = new StringBuilder();
        for (int i = 0; i < phoneNumber.length(); i++){
            if (phoneNumber.charAt(i) >= 48 && phoneNumber.charAt(i) <= 57){
                phoneNumberProc.append(phoneNumber.charAt(i));
            }
        }
        return Long.valueOf(phoneNumberProc.toString());
    }
}
