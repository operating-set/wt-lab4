package com.application.service.creator.writer;

import com.application.entity.TourOrgInfo;
import com.application.entity.User;
import com.application.entity.UserInfo;
import com.application.dao.Transaction;
import com.application.service.exception.ServiceException;

public interface UserWriter {
    User register(User user, UserInfo userInfo, TourOrgInfo tourOrgInfo, Transaction transaction) throws ServiceException;
    boolean changePassword(User user, Transaction transaction);
}
