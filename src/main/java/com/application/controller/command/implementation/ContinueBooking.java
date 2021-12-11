package com.application.controller.command.implementation;

import com.application.entity.Room;
import com.application.controller.command.*;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;
import com.application.service.ServiceFactory;
import com.application.service.exception.ServiceException;
import com.application.service.hotel.HotelService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ContinueBooking implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        //Optional<Integer> userId = Optional.ofNullable((Integer) request.getSession().getAttribute(SessionAttribute.USER_ID));
        Optional<String> hotelId = Optional.ofNullable((String) request.getSession().getAttribute(SessionAttribute.HOTEL_ID));
        Optional<String> dateArrival = Optional.ofNullable(request.getParameter(RequestParameter.DATE_ARRIVAL));
        Optional<String> dateDepartment = Optional.ofNullable(request.getParameter(RequestParameter.DATE_DEPARTMENT));
        Optional<String> typeOfComfort = Optional.ofNullable(request.getParameter(RequestParameter.TYPE_OF_COMFORT));
        Optional<String> adults = Optional.of(request.getParameter(RequestParameter.ADULTS));
        Optional<String> child = Optional.of(request.getParameter(RequestParameter.CHILD));

        if ( hotelId.isEmpty() || dateArrival.isEmpty() || dateDepartment.isEmpty()
                || typeOfComfort.isEmpty() || adults.isEmpty() || child.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.INVALID_DATA);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionAttribute.DATE_ARRIVAL,dateArrival.get());
        session.setAttribute(SessionAttribute.DATE_DEPARTMENT,dateDepartment.get());

        byte [] typeOfAllocationArr = new byte[5];
        typeOfAllocationArr[Integer.parseInt(adults.get())-1] = 1;
        if (Integer.parseInt(child.get())!=0){
            typeOfAllocationArr[typeOfAllocationArr.length-1] = 1;
        }
        int typeOfAllocation = makeTypeOfAllocation(typeOfAllocationArr);

        HotelService hotelService = ServiceFactory.getInstance().getHotelService();


        try {
            if (currentDate().compareTo(Date.valueOf(dateArrival.get())) <= 0 &&
                    Date.valueOf(dateArrival.get()).compareTo(Date.valueOf(dateDepartment.get())) <= 0 ){
                List<Room> roomList =  hotelService.roomListForBooking(Integer.parseInt(hotelId.get()), Date.valueOf(dateArrival.get()),Date.valueOf(dateDepartment.get()),Integer.parseInt(typeOfComfort.get()),typeOfAllocation);
                if (!roomList.isEmpty()){
                    request.getServletContext().setAttribute(RequestParameter.LIST_ROOMS,roomList);
                    return new Rout(PagePath.SHOW_ROOMS_REDIRECT, RoutingType.REDIRECT);
                } else {
                    request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_SEARCH_BOOKING);
                    return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
                }
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.INVALID_DATA);
                return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
            }

        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_500);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

    }

    private int makeTypeOfAllocation(byte [] typeOfAllocationArr){
        StringBuffer typeOfAllocationStr = new StringBuffer();
        for(byte bit : typeOfAllocationArr){
            typeOfAllocationStr.append(bit);
        }
        return Integer.parseInt(typeOfAllocationStr.toString(),2);
    }

    private Date currentDate(){
        return Date.valueOf(LocalDateTime.now().toLocalDate().toString());
    }

}
