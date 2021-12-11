package com.application.dao;

import com.application.entity.Entity;
import com.application.dao.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface Dao <Type extends Entity> {
    Integer create (Type entity) throws DaoException;

    Type read (Integer ... identity) throws DaoException;

    boolean update (Type entity) throws DaoException;

    boolean delete (Integer ... identity) throws DaoException;

    List<Type> read() throws DaoException;

    void setConnection(Connection connection);
}
