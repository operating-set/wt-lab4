package com.application.controller.command.implementation;

import com.application.entity.Role;
import com.application.controller.command.*;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;
import com.application.service.ServiceFactory;
import com.application.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ApprovalBooking implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> userId = Optional.ofNullable(request.getParameter(RequestParameter.USER_ID));
        Optional<String> orderId = Optional.ofNullable(request.getParameter(RequestParameter.ORDER_ID));
        Optional<String> role = Optional.ofNullable(request.getParameter(RequestParameter.ROLE));
        Optional<String> status = Optional.ofNullable(request.getParameter(RequestParameter.STATUS));
        Optional<Role> roleSession = Optional.ofNullable((Role) request.getSession().getAttribute(SessionAttribute.ROLE));
        if (userId.isEmpty() || orderId.isEmpty() || role.isEmpty() || status.isEmpty() || roleSession.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.INVALID_DATA);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        UserService userService = ServiceFactory.getInstance().getUserService();

        if (roleSession.get().equals(Role.valueOf(role.get()))){
            if (userService.approvalBooking(Integer.parseInt(userId.get()),Integer.parseInt(orderId.get()),Integer.parseInt(status.get()))){
                return new Rout(PagePath.SUCCESS_APPROVAL_BOOKING_REDIRECT, RoutingType.REDIRECT);
            } else {
                request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_APPROVAL_BOOKING);
                return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
            }
        } else {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_APPROVAL_BOOKING);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

    }
}
