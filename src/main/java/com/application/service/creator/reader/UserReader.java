package com.application.service.creator.reader;

import com.application.entity.User;
import com.application.dao.Transaction;
import com.application.service.exception.ServiceException;

import java.util.List;

public interface UserReader {

    User read(Integer identity, Transaction transaction) throws ServiceException;


    List<User> readAll(Transaction transaction) throws ServiceException;

    User readByEmail(String email, Transaction transaction) throws ServiceException;
}
