package com.application.controller.command.implementation;

import com.application.entity.Role;
import com.application.controller.command.Command;
import com.application.controller.command.ErrorKey;
import com.application.controller.command.PagePath;
import com.application.controller.command.SessionAttribute;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;
import com.application.service.ServiceFactory;
import com.application.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Optional;

public class EndBooking implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<Integer> userId = Optional.ofNullable((Integer) request.getSession().getAttribute(SessionAttribute.USER_ID));
        Optional<String> hotelId = Optional.ofNullable((String) request.getSession().getAttribute(SessionAttribute.HOTEL_ID));
        Optional<String> roomId = Optional.ofNullable((String) request.getSession().getAttribute(SessionAttribute.ROOM_ID));
        Optional<Role> role = Optional.ofNullable((Role) request.getSession().getAttribute(SessionAttribute.ROLE));
        Optional<String> dateArrival = Optional.ofNullable((String) request.getSession().getAttribute(SessionAttribute.DATE_ARRIVAL));
        Optional<String> dateDepartment = Optional.ofNullable((String) request.getSession().getAttribute(SessionAttribute.DATE_DEPARTMENT));

        if ( hotelId.isEmpty() || userId.isEmpty() || roomId.isEmpty() || role.isEmpty() || dateArrival.isEmpty() || dateDepartment.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.INVALID_DATA);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        UserService userService = ServiceFactory.getInstance().getUserService();

        if (userService.booking(userId.get(), Integer.parseInt(hotelId.get()), Integer.parseInt(roomId.get()), Date.valueOf(dateArrival.get()), Date.valueOf(dateDepartment.get()), (String) request.getSession().getAttribute(SessionAttribute.LOCALE))){
            return new Rout(PagePath.WAIT_APPROVAL_BOOKING_REDIRECT, RoutingType.REDIRECT);
        } else {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_BOOKING);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }
    }
}
