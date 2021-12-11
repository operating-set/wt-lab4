package com.application.dao.impl;

import com.application.entity.Hotel;
import com.application.dao.HotelDao;
import com.application.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDaoImpl extends BaseDaoImpl implements HotelDao { // TODO think about update without admin_id, check null fields
    private final Logger logger = LogManager.getLogger(HotelDaoImpl.class);
    private static final String SQL_INSERT = "INSERT INTO `hotels`(`admin_id`, `name`, `stars`, `type_of_food`, `type_of_allocation`, `type_of_comfort`, `price_of_room`, `price_of_comfort`, `reward_for_tour_operator`, `parking`, `wifi`, `pets`, `business_center`, `countries_id`, `city`, `street`, `house`, `building`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM `hotels` WHERE `hotels`.`id`=?";
    private static final String SQL_SELECT_ALL = "SELECT `id`, `admin_id`, `name`, `stars`, `type_of_food`, `type_of_allocation`, `type_of_comfort`, `price_of_room`, `price_of_comfort`, `reward_for_tour_operator`, `parking`, `wifi`, `pets`, `business_center`, `countries_id`, `city`, `street`, `house`, `building` FROM `hotels`";
    private static final String SQL_SELECT_LIMIT_OFFSET = "SELECT `id`, `admin_id`, `name`, `stars`, `type_of_food`, `type_of_allocation`, `type_of_comfort`, `price_of_room`, `price_of_comfort`, `reward_for_tour_operator`, `parking`, `wifi`, `pets`, `business_center`, `countries_id`, `city`, `street`, `house`, `building` FROM `hotels` LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_ONE = "SELECT `id`, `admin_id`, `name`, `stars`, `type_of_food`, `type_of_allocation`, `type_of_comfort`, `price_of_room`, `price_of_comfort`, `reward_for_tour_operator`, `parking`, `wifi`, `pets`, `business_center`, `countries_id`, `city`, `street`, `house`, `building` FROM `hotels` WHERE `id`=?";
    private static final String SQL_UPDATE = "UPDATE `hotels` SET `name`=?,`stars`=?,`type_of_food`=?,`type_of_allocation`=?,`type_of_comfort`=?,`price_of_room`=?,`price_of_comfort`=?,`reward_for_tour_operator`=?,`parking`=?,`wifi`=?,`pets`=?,`business_center`=?,`countries_id`=?,`city`=?,`street`=?,`house`=?,`building`=? WHERE `hotels`.`id`=?";

    @Override
    public Integer create(Hotel hotel) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,hotel.getAdminId());
            statement.setString(2,hotel.getName());
            statement.setInt(3,hotel.getStars());
            statement.setInt(4,hotel.getFacilities().getTypeFood());
            statement.setInt(5,hotel.getFacilities().getTypeAllocation());
            statement.setInt(6,hotel.getFacilities().getTypeComfort());
            statement.setString(7,hotel.getPrices().getPriceAllocation());
            statement.setString(8,hotel.getPrices().getPriceComfort());
            statement.setInt(9,hotel.getPrices().getRewardTourOperator());
            statement.setBoolean(10,hotel.getFacilities().isParking());
            statement.setBoolean(11,hotel.getFacilities().isWifi());
            statement.setBoolean(12,hotel.getFacilities().isPets());
            statement.setBoolean(13,hotel.getFacilities().isBusiness());
            statement.setString(14,hotel.getAddress().getCountryCode());
            statement.setString(15,hotel.getAddress().getCity());
            statement.setString(16,hotel.getAddress().getStreet());
            statement.setInt(17,hotel.getAddress().getHouse());
            statement.setInt(18,hotel.getAddress().getBuilding());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
    }

    @Override
    public Hotel read(Integer... identity) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Hotel hotel = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ONE);
            statement.setInt(1, identity[0]);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                if (resultSet.next()){
                    hotel = new Hotel();
                    hotel.setId(resultSet.getInt(1));
                    hotel.setAdminId(resultSet.getInt(2));
                    hotel.setName(resultSet.getString(3));
                    hotel.setStars(resultSet.getInt(4));
                    hotel.getFacilities().setTypeFood(resultSet.getInt(5));
                    hotel.getFacilities().setTypeAllocation(resultSet.getInt(6));
                    hotel.getFacilities().setTypeComfort(resultSet.getInt(7));
                    hotel.getPrices().setPriceAllocation(resultSet.getString(8));
                    hotel.getPrices().setPriceComfort(resultSet.getString(9));
                    hotel.getPrices().setRewardTourOperator(resultSet.getInt(10));
                    hotel.getFacilities().setParking(resultSet.getBoolean(11));
                    hotel.getFacilities().setWifi(resultSet.getBoolean(12));
                    hotel.getFacilities().setPets(resultSet.getBoolean(13));
                    hotel.getFacilities().setBusiness(resultSet.getBoolean(14));
                    hotel.getAddress().setCountryCode(resultSet.getString(15));
                    hotel.getAddress().setCity(resultSet.getString(16));
                    hotel.getAddress().setStreet(resultSet.getString(17));
                    hotel.getAddress().setHouse(resultSet.getInt(18));
                    hotel.getAddress().setBuilding(resultSet.getInt(19));
                }
            }
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
        return hotel;
    }

    @Override
    public boolean update(Hotel hotel) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(18,hotel.getId());
            statement.setString(1,hotel.getName());
            statement.setInt(2,hotel.getStars());
            statement.setInt(3,hotel.getFacilities().getTypeFood());
            statement.setInt(4,hotel.getFacilities().getTypeAllocation());
            statement.setInt(5,hotel.getFacilities().getTypeComfort());
            statement.setString(6,hotel.getPrices().getPriceAllocation());
            statement.setString(7,hotel.getPrices().getPriceComfort());
            statement.setInt(8,hotel.getPrices().getRewardTourOperator());
            statement.setBoolean(9,hotel.getFacilities().isParking());
            statement.setBoolean(10,hotel.getFacilities().isWifi());
            statement.setBoolean(11,hotel.getFacilities().isPets());
            statement.setBoolean(12,hotel.getFacilities().isBusiness());
            statement.setString(13,hotel.getAddress().getCountryCode());
            statement.setString(14,hotel.getAddress().getCity());
            statement.setString(15,hotel.getAddress().getStreet());
            statement.setInt(16,hotel.getAddress().getHouse());
            statement.setInt(17,hotel.getAddress().getBuilding());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
    }

    @Override
    public boolean delete(Integer ... identity) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, identity[0]);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
    }

    @Override
    public List<Hotel> read() throws DaoException {
        List<Hotel> hotelList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Hotel hotel = new Hotel();
                    hotel.setId(resultSet.getInt(1));
                    hotel.setAdminId(resultSet.getInt(2));
                    hotel.setName(resultSet.getString(3));
                    hotel.setStars(resultSet.getInt(4));
                    hotel.getFacilities().setTypeFood(resultSet.getInt(5));
                    hotel.getFacilities().setTypeAllocation(resultSet.getInt(6));
                    hotel.getFacilities().setTypeComfort(resultSet.getInt(7));
                    hotel.getPrices().setPriceAllocation(resultSet.getString(8));
                    hotel.getPrices().setPriceComfort(resultSet.getString(9));
                    hotel.getPrices().setRewardTourOperator(resultSet.getInt(10));
                    hotel.getFacilities().setParking(resultSet.getBoolean(11));
                    hotel.getFacilities().setWifi(resultSet.getBoolean(12));
                    hotel.getFacilities().setPets(resultSet.getBoolean(13));
                    hotel.getFacilities().setBusiness(resultSet.getBoolean(14));
                    hotel.getAddress().setCountryCode(resultSet.getString(15));
                    hotel.getAddress().setCity(resultSet.getString(16));
                    hotel.getAddress().setStreet(resultSet.getString(17));
                    hotel.getAddress().setHouse(resultSet.getInt(18));
                    hotel.getAddress().setBuilding(resultSet.getInt(19));
                    hotelList.add(hotel);
                }
            }
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
        return hotelList;
    }

    @Override
    public List<Hotel> read(Integer page, Integer countHotelOnPage) throws DaoException {
        List<Hotel> hotelList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_LIMIT_OFFSET);
            statement.setInt(1, countHotelOnPage);
            statement.setInt(2, (page-1)*countHotelOnPage);
            if (statement.execute()) {
                resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Hotel hotel = new Hotel();
                    hotel.setId(resultSet.getInt(1));
                    hotel.setAdminId(resultSet.getInt(2));
                    hotel.setName(resultSet.getString(3));
                    hotel.setStars(resultSet.getInt(4));
                    hotel.getFacilities().setTypeFood(resultSet.getInt(5));
                    hotel.getFacilities().setTypeAllocation(resultSet.getInt(6));
                    hotel.getFacilities().setTypeComfort(resultSet.getInt(7));
                    hotel.getPrices().setPriceAllocation(resultSet.getString(8));
                    hotel.getPrices().setPriceComfort(resultSet.getString(9));
                    hotel.getPrices().setRewardTourOperator(resultSet.getInt(10));
                    hotel.getFacilities().setParking(resultSet.getBoolean(11));
                    hotel.getFacilities().setWifi(resultSet.getBoolean(12));
                    hotel.getFacilities().setPets(resultSet.getBoolean(13));
                    hotel.getFacilities().setBusiness(resultSet.getBoolean(14));
                    hotel.getAddress().setCountryCode(resultSet.getString(15));
                    hotel.getAddress().setCity(resultSet.getString(16));
                    hotel.getAddress().setStreet(resultSet.getString(17));
                    hotel.getAddress().setHouse(resultSet.getInt(18));
                    hotel.getAddress().setBuilding(resultSet.getInt(19));
                    hotelList.add(hotel);
                }
            }
        } catch (SQLException e) {
            logger.debug(e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.debug(e);
                throw new DaoException(e);
            }
        }
        return hotelList;
    }

    @Override
    public void setConnection(Connection connection) {
        super.connection = connection;
    }
}
