package com.application.dao;

import com.application.entity.TourOrgInfo;
import com.application.dao.exception.DaoException;

import java.util.List;

public interface TourOrgInfoDao extends Dao<TourOrgInfo>{
    @Override
    Integer create(TourOrgInfo tourOrgInfo) throws DaoException;

    @Override
    TourOrgInfo read(Integer... identity) throws DaoException;

    @Override
    boolean update(TourOrgInfo tourOrgInfo) throws DaoException;

    @Override
    boolean delete(Integer ... identity) throws DaoException;

    @Override
    List<TourOrgInfo> read() throws DaoException;
}
