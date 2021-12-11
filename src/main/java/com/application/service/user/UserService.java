package com.application.service.user;

import com.application.entity.*;
import com.application.service.exception.ServiceException;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

public interface UserService {
    User login(String email, String password) throws ServiceException;
    boolean sendMailForRegister(HttpSession session, String email, String password, Role role, String locale);
    User register(User user, UserInfo userInfo, TourOrgInfo tourOrgInfo) throws ServiceException;
    boolean editPassword(Integer userId, String oldPassword, String newPassword);
    boolean booking (Integer userId, Integer hotelId, Integer roomId, Date dateArrival, Date dateDepartment, String locale);
    boolean approvalBooking(Integer userId, Integer orderId, Integer status);
    List<Order> readAllOrder (Integer userId) throws ServiceException;
    List<Order> readOrder (Integer userId, Integer page, Integer countPage) throws ServiceException;
}
