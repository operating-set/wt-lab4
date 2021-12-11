package com.application.entity;

public class EntityConstant {
    private EntityConstant() {}

    public static final byte TYPE_ALLOCATION_STANDARD = 0b1000;
    public static final byte TYPE_ALLOCATION_FAMILY = 0b100;
    public static final byte TYPE_ALLOCATION_LUXE = 0b10;
    public static final byte TYPE_ALLOCATION_SUITE = 0b1;

    public static final byte HOTEL_NO = 0b10000;
    public static final byte HOTEL_BREAKFAST = 0b1000;
    public static final byte HOTEL_HALF_BOARD = 0b100;
    public static final byte HOTEL_FULL_BOARD = 0b10;
    public static final byte HOTEL_ALL_INCLUSIVE = 0b1;

    public static final byte HOTEL_SINGLE = 0b10000;
    public static final byte HOTEL_DOUBLE = 0b1000;
    public static final byte HOTEL_TRIPLE = 0b100;
    public static final byte HOTEL_EXTRA = 0b10;
    public static final byte HOTEL_CHILD = 0b1;

    public static final byte ORDER_CANCEL = 0;
    public static final byte ORDER_WAITING = 1;
    public static final byte ORDER_ACCEPT = 2;
    public static final byte ORDER_EXECUTE = 3;

    public static final byte BOOKING_WAITING = 0;
    public static final byte BOOKING_ACCEPT = 1;

}
