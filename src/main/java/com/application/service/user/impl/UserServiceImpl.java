package com.application.service.user.impl;


import com.application.entity.*;
import com.application.controller.command.SessionAttribute;
import com.application.dao.exception.DaoException;
import com.application.service.ServiceFactory;
import com.application.service.creator.reader.BookingReader;
import com.application.service.creator.reader.OrderReader;
import com.application.service.creator.writer.BookingWriter;
import com.application.service.creator.writer.OrderWriter;
import com.application.service.creator.writer.UserWriter;
import com.application.service.exception.ServiceException;
import com.application.service.hashing.HashingService;
import com.application.service.mail.MailService;
import com.application.service.creator.CreatorFactory;
import com.application.service.creator.reader.UserReader;
import com.application.service.user.UserService;
import com.application.dao.Transaction;
import com.application.dao.TransactionCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    Transaction transaction;

    @Override
    public User login(String email, String passwordMD5) throws ServiceException {
        User user = null;
        if (email != null || passwordMD5 != null) {
            UserReader userReader = CreatorFactory.getInstance().getUserReader();
            try {
                transaction = createTransaction();
                User userRead = userReader.readByEmail(email, transaction);
                transaction.commit();
                if (userRead != null && passwordMD5.equals(userRead.getPassword())) {
                    user = userRead;
                }
            } catch (ServiceException e) {
                try {
                    transaction.rollback();
                } catch (DaoException ex) {
                    logger.error("Error with rollback", e);
                    throw new ServiceException("Error with rollback");
                }
                logger.error("Error read from DB", e);
                throw new ServiceException("Error read from DB" + e);
            } catch (DaoException e) {
                logger.error("Error with commit", e);
                throw new ServiceException("Error with commit");
            }
        }
        return user;
    }

    public boolean sendMailForRegister(HttpSession session, String email, String password, Role role, String locale) {
        MailService mailService = ServiceFactory.getInstance().getMailService();
        HashingService sha256 = ServiceFactory.getInstance().getSHA256Hashing();
        UserReader userReader = CreatorFactory.getInstance().getUserReader();
        User user;
        try {
            transaction = createTransaction();
            user = userReader.readByEmail(email, transaction);
            transaction.commit();
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                logger.error("Error with rollback", e);
                return false;
            }
            return false;
        } catch (DaoException e) {
            logger.error("Error with commit", e);
            return false;
        }

        if (user == null && !email.equals(mailService.getEmail())) {
            if (mailService.sendApprovalRegistration(email, sha256.hashing(password), role.toString(), locale)) {
                session.setAttribute(SessionAttribute.HASH_LINK, mailService.getLinkRegistrationHash());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public User register(User user, UserInfo userInfo, TourOrgInfo tourOrgInfo) throws ServiceException {
        User registerUser;
        if (user != null && userInfo != null && tourOrgInfo != null) {
            UserWriter userWriter = CreatorFactory.getInstance().getUserWriter();
            try {
                transaction = createTransaction();
                registerUser = userWriter.register(user, userInfo, tourOrgInfo, transaction);
                transaction.commit();
            } catch (ServiceException e) {
                try {
                    transaction.rollback();
                } catch (DaoException ex) {
                    logger.error("Error with rollback", e);
                    throw new ServiceException("Error with rollback");
                }
                throw new ServiceException("Error read from DB" + e);
            } catch (DaoException e) {
                logger.error("Error with commit", e);
                throw new ServiceException("Error with commit");
            }
            return registerUser;
        } else {
            return null;
        }
    }

    @Override
    public boolean editPassword(Integer userId, String oldPassword, String newPassword) {
        boolean result;
        if (userId != null){
            UserReader userReader = CreatorFactory.getInstance().getUserReader();
            UserWriter userWriter = CreatorFactory.getInstance().getUserWriter();
            try {
                transaction = createTransaction();
                User user = userReader.read(userId,transaction);
                if (user != null && user.getPassword().equals(oldPassword)){
                    user.setPassword(newPassword);
                    result = userWriter.changePassword(user,transaction);
                    if (result){
                        transaction.commit();
                    } else {
                        transaction.rollback();
                    }
                } else {
                    transaction.rollback();
                    return false;
                }
            } catch (ServiceException e) {
                try {
                    transaction.rollback();
                } catch (DaoException ex) {
                    logger.error("Error with rollback", e);
                    return false;
                }
                return false;
            } catch (DaoException e) {
                logger.error("Error with commit", e);
                return false;
            }
            return result;
        } else {
            return false;
        }

    }

    @Override
    public boolean booking(Integer userId, Integer hotelId, Integer roomId, Date dateArrival, Date dateDepartment, String locale) {
        if (userId != null && hotelId != null && roomId != null && dateArrival != null && dateDepartment != null){
            Order order = new Order();
            order.setUserId(userId);
            order.setHotelId(hotelId);
            order.setNumber(roomId);
            order.setDateArrival(dateArrival);
            order.setDateDepartment(dateDepartment);
            order.setStatus(EntityConstant.ORDER_WAITING);

            Booking booking = new Booking();
            booking.setNumber(roomId);
            booking.setHotelId(hotelId);
            booking.setDateArrival(dateArrival);
            booking.setDateDepartment(dateDepartment);
            booking.setStatus(EntityConstant.BOOKING_WAITING);

            OrderWriter orderWriter = CreatorFactory.getInstance().getOrderWriter();
            BookingWriter bookingWriter = CreatorFactory.getInstance().getBookingWriter();
            UserReader userReader = CreatorFactory.getInstance().getUserReader();

            try {
                transaction = createTransaction();
                User user = userReader.read(userId,transaction);
                if (orderWriter.booking(order,transaction) && bookingWriter.booking(booking, transaction)
                        && sendMailForBooking(user.getEmail(), userId, user.getRole().name(), order.getId(), locale)){
                    transaction.commit();
                    return true;
                } else {
                    transaction.rollback();
                    return false;
                }
            } catch (ServiceException e) {
                try {
                    transaction.rollback();
                } catch (DaoException ex) {
                    logger.error("Error with rollback", e);
                    return false;
                }
                return false;
            } catch (DaoException e) {
                logger.error("Error with commit", e);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean approvalBooking(Integer userId, Integer orderId, Integer status) {
        boolean result = false;
        if (userId != null && orderId != null && status != null){
            OrderReader orderReader = CreatorFactory.getInstance().getOrderReader();
            BookingReader bookingReader = CreatorFactory.getInstance().getBookingReader();
            OrderWriter orderWriter = CreatorFactory.getInstance().getOrderWriter();
            BookingWriter bookingWriter = CreatorFactory.getInstance().getBookingWriter();

            try {
                transaction = createTransaction();
                Order order = orderReader.read(orderId,transaction);
                Booking booking = bookingReader.read(order.getNumber(),order.getHotelId(),transaction);
                if (status == EntityConstant.ORDER_ACCEPT){
                    order.setStatus(EntityConstant.ORDER_ACCEPT);
                    booking.setStatus(EntityConstant.BOOKING_ACCEPT);
                    result = orderWriter.update(order,transaction) && bookingWriter.update(booking,transaction);
                } else if (status == EntityConstant.ORDER_CANCEL) {
                    order.setStatus(EntityConstant.ORDER_CANCEL);
                    result = orderWriter.update(order,transaction) && bookingWriter.delete(booking,transaction);
                }
                if (result){
                    transaction.commit();
                } else {
                    transaction.rollback();
                }
                return result;
            } catch (ServiceException e) {
                try {
                    transaction.rollback();
                } catch (DaoException ex) {
                    logger.error("Error with rollback", e);
                    return false;
                }
                return false;
            } catch (DaoException e) {
                logger.error("Error with commit", e);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<Order> readAllOrder(Integer userId) throws ServiceException {
        OrderReader orderReader = CreatorFactory.getInstance().getOrderReader();
        List<Order> orderList;
        try {
            transaction = createTransaction();
            orderList = orderReader.readAllOrderByUserId(userId,transaction);
            transaction.commit();
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                logger.error("Error with rollback", e);
                throw new ServiceException("Error with rollback");
            }
            logger.error("Error read from DB", e);
            throw new ServiceException("Error read from DB" + e);
        } catch (DaoException e) {
            logger.error("Error with commit", e);
            throw new ServiceException("Error with commit");
        }
        return orderList;
    }

    @Override
    public List<Order> readOrder(Integer userId, Integer page, Integer countPage) throws ServiceException {
        OrderReader orderReader = CreatorFactory.getInstance().getOrderReader();
        List<Order> orderList;
        try {
            transaction = createTransaction();
            orderList = orderReader.readOrderByUserId(userId,page, countPage,transaction);
            transaction.commit();
        } catch (ServiceException e) {
            try {
                transaction.rollback();
            } catch (DaoException ex) {
                logger.error("Error with rollback", e);
                throw new ServiceException("Error with rollback");
            }
            logger.error("Error read from DB", e);
            throw new ServiceException("Error read from DB" + e);
        } catch (DaoException e) {
            logger.error("Error with commit", e);
            throw new ServiceException("Error with commit");
        }
        return orderList;
    }

    private boolean sendMailForBooking(String email, Integer userId, String role, Integer orderId, String locale) {
        MailService mailService = ServiceFactory.getInstance().getMailService();
        return mailService.sendApprovalBooking(email, userId, role, orderId, locale);
    }

    private Transaction createTransaction() throws ServiceException {
        try {
            TransactionCreator transactionCreator = new TransactionCreator();
            return transactionCreator.create();
        } catch (DaoException e) {
            logger.error("Can't create transaction");
            throw new ServiceException("Can't create transaction");
        }
    }

}
