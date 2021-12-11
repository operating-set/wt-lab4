package com.application.controller.command.implementation;

import com.application.controller.command.Command;
import com.application.controller.command.PagePath;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowSignUp implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        return new Rout(PagePath.SIGN_UP_PAGE, RoutingType.FORWARD);
    }
}
