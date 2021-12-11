package com.application.dao;

import com.application.entity.UserInfo;
import com.application.dao.exception.DaoException;

import java.util.List;

public interface UserInfoDao extends Dao<UserInfo>{

    @Override
    Integer create(UserInfo userInfo) throws DaoException;

    @Override
    UserInfo read(Integer ... identity) throws DaoException;

    @Override
    boolean update(UserInfo userInfo) throws DaoException;

    @Override
    boolean delete(Integer ... identity) throws DaoException;

    @Override
    List<UserInfo> read() throws DaoException;
}
