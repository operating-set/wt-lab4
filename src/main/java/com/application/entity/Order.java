package com.application.entity;

import java.sql.Date;
import java.util.Objects;

public class Order extends Entity{
    private int userId;
    private int hotelId;
    private int number;
    private Date dateArrival;
    private Date dateDepartment;
    private int status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(Date dateArrival) {
        this.dateArrival = dateArrival;
    }

    public Date getDateDepartment() {
        return dateDepartment;
    }

    public void setDateDepartment(Date dateDepartment) {
        this.dateDepartment = dateDepartment;
    }

    public int getStatus() {
        return status;
    }

    public String getStrValStatus() {
        switch (status){
            case EntityConstant.ORDER_CANCEL: return "order cancel";
            case EntityConstant.ORDER_WAITING: return "order waiting";
            case EntityConstant.ORDER_ACCEPT: return "order accept";
            case EntityConstant.ORDER_EXECUTE: return "order execute";
            default: return "unknown status";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return userId == order.userId && hotelId == order.hotelId && number == order.number && status == order.status && dateArrival.equals(order.dateArrival) && dateDepartment.equals(order.dateDepartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, hotelId, number, dateArrival, dateDepartment, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", hotelId=" + hotelId +
                ", number=" + number +
                ", dateArrival=" + dateArrival +
                ", dateDepartment=" + dateDepartment +
                ", status=" + status +
                "} " + super.toString();
    }
}
