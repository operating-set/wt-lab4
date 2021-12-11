package com.application.dao.impl;

import com.application.dao.CountriesDao;
import com.application.dao.exception.DaoException;
import com.application.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDaoImpl implements CountriesDao {
    private final Logger logger = LogManager.getLogger(CountriesDaoImpl.class);
    private static final String SQL_SELECT_COUNTRY = "SELECT `name` FROM `countries` WHERE `code` = ?";
    private static final String SQL_SELECT_CODE = "SELECT `code` FROM `countries` WHERE `name` = ?";

    @Override
    public String read(String countryCode) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String countryName = "";
        try {
            statement = ConnectionPool.getInstance().getConnection().prepareStatement(SQL_SELECT_COUNTRY);
            statement.setString(1, countryCode);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()){
                    countryName = resultSet.getString(1);
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
        return countryName;
    }

    @Override
    public char[] readCountryCode(String country) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        char[] countryCode = new char[3];
        try {
            statement = ConnectionPool.getInstance().getConnection().prepareStatement(SQL_SELECT_CODE);
            statement.setString(1, country);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()){
                    countryCode = resultSet.getString(1).toCharArray();
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
        return countryCode;
    }
}
