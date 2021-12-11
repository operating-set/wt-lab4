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

public class SignUp implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> email = Optional.ofNullable(request.getParameter(RequestParameter.EMAIL));
        Optional<String> password = Optional.ofNullable(request.getParameter(RequestParameter.PASSWORD));
        Optional<String> role = Optional.ofNullable(request.getParameter(RequestParameter.ROLE));

        if (email.isEmpty() || password.isEmpty() || role.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.INVALID_SIGNUP_DATA);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        UserService userService = ServiceFactory.getInstance().getUserService();

        if (userService.sendMailForRegister(request.getSession(),email.get(),password.get(), Role.valueOf(role.get()), (String) request.getSession().getAttribute(SessionAttribute.LOCALE))){
               return new Rout(PagePath.MAIL_CHECK_REDIRECT, RoutingType.REDIRECT);
        } else {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_DONT_REGISTRATION);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

    }
}
