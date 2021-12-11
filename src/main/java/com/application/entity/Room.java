package com.application.entity;

import java.util.Objects;

public class Room extends Entity{
    private int hotelId;
    private int typeAllocation;
    private int typeComfort;
    private int price;

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

    public int getTypeAllocation() {
        return typeAllocation;
    }

    public String getStrValTypeAllocation(){
        return makeStrTypeAllocation();
    }

    public void setTypeAllocation(int typeAllocation) {
        this.typeAllocation = typeAllocation;
    }

    public int getTypeComfort() {
        return typeComfort;
    }

    public String getStrValTypeComfort(){
        return makeStrTypeComfort();
    }

    public void setTypeComfort(int typeComfort) {
        this.typeComfort = typeComfort;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private String makeStrTypeComfort(){
        if ((typeComfort & EntityConstant.TYPE_ALLOCATION_STANDARD) != 0){
            return ("standard");
        }
        if ((typeComfort & EntityConstant.TYPE_ALLOCATION_FAMILY) != 0){
            return ("family");
        }
        if ((typeComfort & EntityConstant.TYPE_ALLOCATION_LUXE) !=0){
            return ("luxe");
        }
        if ((typeComfort & EntityConstant.TYPE_ALLOCATION_SUITE) !=0){
            return ("suite");
        }
        return "unknown";
    }

    private String makeStrTypeAllocation(){
        StringBuffer typeAllocationStr = new StringBuffer();
        if ((typeAllocation & EntityConstant.HOTEL_SINGLE) != 0){
            typeAllocationStr.append("single ");
        }
        if ((typeAllocation & EntityConstant.HOTEL_DOUBLE) != 0){
            typeAllocationStr.append("double ");
        }
        if ((typeAllocation & EntityConstant.HOTEL_TRIPLE) !=0){
            typeAllocationStr.append("triple ");
        }
        if ((typeAllocation & EntityConstant.HOTEL_EXTRA) !=0){
            typeAllocationStr.append("extra ");
        }
        if ((typeAllocation & EntityConstant.HOTEL_CHILD) !=0){
            typeAllocationStr.append("child ");
        }
        return typeAllocationStr.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Room room = (Room) o;
        return getNumber() == room.getNumber() && hotelId == room.hotelId && typeAllocation == room.typeAllocation && typeComfort == room.typeComfort;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hotelId, typeAllocation, typeComfort);
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + getNumber() +
                "hotelId=" + hotelId +
                ", typeAllocation=" + typeAllocation +
                ", typeComfort=" + typeComfort +
                "}";
    }
}
