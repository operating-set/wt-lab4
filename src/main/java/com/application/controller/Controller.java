package com.application.controller;

import com.application.controller.command.rout.Rout;
import com.application.service.exception.ServiceException;
import com.application.service.wrapper.InitWrapper;
import com.application.controller.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final CommandProvider commandProvider = new CommandProvider();
    private final Logger logger = LogManager.getLogger(Controller.class);

    public Controller() {
        super();
    }

    @Override
    public void init() throws ServletException {
        try {
            InitWrapper.getInstance().init();
            super.init();
        } catch (ServiceException e) {
            logger.fatal("Servlet can't init");
            throw new ServletException("Servlet can't init");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processCommand(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processCommand(request, response);
    }

    private static void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<String> commandName = Optional.ofNullable(request.getParameter(RequestParameter.COMMAND));
        if (commandName.isPresent()) {
            Command command = commandProvider.getCommand(commandName.get());
            Rout rout = command.execute(request, response);

            String pathToResource = rout.getPathToResource();
            switch (rout.getRoutingType()) {
                case FORWARD:
                    request.getRequestDispatcher(pathToResource).forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(request.getContextPath() + pathToResource);
                    break;
            }
        } else {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_404);
            request.getRequestDispatcher(PagePath.ERROR_PAGE).forward(request, response);
        }
    }
}
