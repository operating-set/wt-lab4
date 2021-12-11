package com.application.dao;

import com.application.dao.exception.DaoException;

public interface CountriesDao {
    String read (String countryCode) throws DaoException;
    char[] readCountryCode(String country) throws DaoException;
}
