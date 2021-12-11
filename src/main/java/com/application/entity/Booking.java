package com.application.entity;


import java.sql.Date;
import java.util.Objects;

public class Booking extends Entity{
    private int hotelId;
    private Date dateArrival;
    private Date dateDepartment;
    private int status;

    public int getNumber() {
        return getId();
    }

    public void setNumber(int number) {
        setId(number);
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
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

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Booking booking = (Booking) o;
        return hotelId == booking.hotelId && dateArrival == booking.dateArrival && dateDepartment == booking.dateDepartment && status == booking.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hotelId, dateArrival, dateDepartment,status);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "hotelId=" + hotelId +
                ", dateArrival=" + dateArrival +
                ", dateDepartment=" + dateDepartment +
                ", status=" + status +
                "} " + super.toString();
    }
}


