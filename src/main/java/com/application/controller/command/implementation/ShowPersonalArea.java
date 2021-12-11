package com.application.controller.command.implementation;

import com.application.entity.Order;
import com.application.controller.command.*;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;
import com.application.service.ServiceFactory;
import com.application.service.exception.ServiceException;
import com.application.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class ShowPersonalArea implements Command {
    private static final String COUNT_ON_PAGE = "paginationCountEntityOnPage";

    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.PAGE_MAIN);
        session.removeAttribute(SessionAttribute.PAGE_ROOMS);
        Optional<Integer> userId = Optional.ofNullable((Integer) session.getAttribute(SessionAttribute.USER_ID));
        Optional<String> pageParam = Optional.ofNullable((String) request.getSession().getAttribute(SessionAttribute.PAGE_PERSONAL_AREA));

        if (userId.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.INVALID_DATA);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        if (pageParam.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_404);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        int page = Integer.parseInt(pageParam.get());
        int orderOnPage = Integer.parseInt(request.getServletContext().getInitParameter(COUNT_ON_PAGE));
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            List<Order> orderListAll = userService.readAllOrder(userId.get());
            List<Order> orderList = userService.readOrder(userId.get(),page, orderOnPage);
            request.setAttribute(RequestParameter.ENTITY_LIST, orderList);
            request.setAttribute(RequestParameter.CURRENT_PAGE, page);
            request.setAttribute(RequestParameter.COUNT_ALL_ENTITIES, orderListAll.size());
            request.setAttribute(RequestParameter.COUNT_PAGE,(int) Math.ceil(orderListAll.size()/(double)orderOnPage));
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_500);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }
        return new Rout(PagePath.SHOW_PERSONAL_AREA, RoutingType.FORWARD);


    }
}
