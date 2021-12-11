package com.application.controller.command.implementation;

import com.application.entity.Role;
import com.application.controller.command.Command;
import com.application.controller.command.ErrorKey;
import com.application.controller.command.PagePath;
import com.application.controller.command.SessionAttribute;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ShowBooking implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.PAGE_MAIN);
        session.removeAttribute(SessionAttribute.PAGE_ROOMS);
        session.removeAttribute(SessionAttribute.PAGE_PERSONAL_AREA);
        Optional<Role> role = Optional.ofNullable((Role) session.getAttribute(SessionAttribute.ROLE));
        if (role.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.INVALID_ROLE_BOOKING);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        } else {
            return new Rout(PagePath.SHOW_BOOKING, RoutingType.FORWARD);
        }
    }
}
