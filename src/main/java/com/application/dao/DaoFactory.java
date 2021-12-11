package com.application.dao;

import com.application.entity.Entity;
import com.application.dao.impl.*;

public final class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final UserDao userDaoImpl = new UserDaoImpl();
    private final UserInfoDao userInfoDaoImpl = new UserInfoDaoImpl();
    private final TourOrgInfoDao tourOrganizationInfoDaoImpl = new TourOrgInfoDaoImpl();
    private final HotelDao hotelDaoImpl = new HotelDaoImpl();
    private final CountriesDao countriesDaoImpl = new CountriesDaoImpl();
    private final RoomDao roomDaoImpl = new RoomDaoImpl();
    private final BookingDao bookingDaoImpl = new BookingDaoImpl();
    private final OrderDao orderDaoImpl = new OrderDaoImpl();

    private DaoFactory() {}
    public static DaoFactory getInstance(){
        return instance;
    }
    public <T extends Dao<? extends Entity>> T getUserDao(){
        return (T) userDaoImpl;
    }
    public <T extends Dao<? extends Entity>> T getUserInfoDao() { return (T)userInfoDaoImpl; }
    public <T extends Dao<? extends Entity>> T getTourOrganizationInfoDao() {return (T)tourOrganizationInfoDaoImpl; }
    public <T extends Dao<? extends Entity>> T getHotelDao() { return (T)hotelDaoImpl; }
    public <T extends Dao<? extends Entity>> T getRoomDao() {  return (T)roomDaoImpl; }
    public <T extends Dao<? extends Entity>> T getBookingDao() {  return (T) bookingDaoImpl; }
    public <T extends Dao<? extends Entity>> T getOrderDao() { return (T) orderDaoImpl; }
    public CountriesDao getCountriesDao() { return countriesDaoImpl; }

}
