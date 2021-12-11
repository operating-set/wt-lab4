package com.application.controller.command;

import com.application.controller.command.rout.Rout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    Rout execute(HttpServletRequest request, HttpServletResponse response);
}
