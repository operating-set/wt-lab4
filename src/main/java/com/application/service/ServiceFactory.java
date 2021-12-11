package com.application.service;

import com.application.service.hashing.HashingService;
import com.application.service.hashing.impl.SHA256Hashing;
import com.application.service.hotel.HotelService;
import com.application.service.hotel.impl.HotelServiceImpl;
import com.application.service.mail.MailService;
import com.application.service.mail.impl.MailServiceImpl;
import com.application.service.user.UserService;
import com.application.service.user.impl.*;
import com.application.service.user.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final UserService userService = new UserServiceImpl();
    private final HotelService hotelService = new HotelServiceImpl();
    private final MailService mailService = new MailServiceImpl();
    private final HashingService SHA256Hashing = new SHA256Hashing();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
    public UserService getUserService() {
        return userService;
    }
    public MailService getMailService() { return  mailService; }
    public HashingService getSHA256Hashing() {return SHA256Hashing;}
    public HotelService getHotelService() { return hotelService;}

}
