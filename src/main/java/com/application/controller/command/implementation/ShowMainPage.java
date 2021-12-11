package com.application.controller.command.implementation;

import com.application.controller.command.Command;
import com.application.controller.command.PagePath;
import com.application.controller.command.SessionAttribute;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowMainPage implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.PAGE_ROOMS);
        return new Rout(PagePath.MAIN_PAGE_REDIRECT, RoutingType.REDIRECT);
    }
}
