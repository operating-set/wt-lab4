package com.application.service.creator;

import com.application.service.creator.writer.BookingWriter;
import com.application.service.creator.writer.OrderWriter;
import com.application.service.creator.writer.UserWriter;
import com.application.service.creator.writer.impl.BookingWriterImpl;
import com.application.service.creator.writer.impl.OrderWriterImpl;
import com.application.service.creator.writer.impl.UserWriterImpl;
import com.application.service.creator.reader.*;
import com.application.service.creator.reader.impl.*;

public final class CreatorFactory {
    private static final CreatorFactory instance = new CreatorFactory();
    private final UserReader userReaderImpl = new UserReaderImpl();
    private final HotelReader hotelReaderImpl = new HotelReaderImpl();
    private final UserWriter userWriterImpl = new UserWriterImpl();
    private final RoomReader roomReaderImpl = new RoomReaderImpl();
    private final BookingReader bookingReaderImpl = new BookingReaderImpl();
    private final BookingWriter bookingWriterImpl = new BookingWriterImpl();
    private final OrderWriter orderWriterImpl = new OrderWriterImpl();
    private final OrderReader orderReaderImpl = new OrderReaderImpl();

    private CreatorFactory() {}
    public static CreatorFactory getInstance(){
        return instance;
    }
    public UserReader getUserReader() { return userReaderImpl; }
    public HotelReader getHotelReader() { return hotelReaderImpl; }
    public UserWriter getUserWriter() { return userWriterImpl; }
    public RoomReader getRoomReader() { return roomReaderImpl; }
    public BookingReader getBookingReader() { return bookingReaderImpl; }
    public BookingWriter getBookingWriter() { return bookingWriterImpl; }
    public OrderWriter getOrderWriter() { return orderWriterImpl; }
    public OrderReader getOrderReader() { return orderReaderImpl; }
}
