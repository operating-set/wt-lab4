package com.application.controller.command.implementation;

import com.application.controller.command.Command;
import com.application.controller.command.ErrorKey;
import com.application.controller.command.PagePath;
import com.application.controller.command.SessionAttribute;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Error404 implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_404);
        return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
    }
}
