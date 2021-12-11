package com.application.controller.command.implementation;

import com.application.entity.Hotel;
import com.application.controller.command.*;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;
import com.application.service.ServiceFactory;
import com.application.service.exception.ServiceException;
import com.application.service.hotel.HotelService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class MainPage implements Command {
    private final static String COUNT_ON_PAGE = "paginationCountEntityOnPage";

    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response){
        Optional<String> pageParam = Optional.ofNullable((String) request.getSession().getAttribute(SessionAttribute.PAGE_MAIN));
        if (pageParam.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_404);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        int page = Integer.parseInt(pageParam.get());
        int hotelOnPage = Integer.parseInt(request.getServletContext().getInitParameter(COUNT_ON_PAGE));
        HotelService hotelService = ServiceFactory.getInstance().getHotelService();
        try {
            List<Hotel> hotelListAll = hotelService.readAll();
            List<Hotel> hotelList = hotelService.read(page, hotelOnPage);
            request.setAttribute(RequestParameter.ENTITY_LIST, hotelList);
            request.setAttribute(RequestParameter.CURRENT_PAGE, page);
            request.setAttribute(RequestParameter.COUNT_ALL_ENTITIES, hotelListAll.size());
            request.setAttribute(RequestParameter.COUNT_PAGE,(int) Math.ceil(hotelListAll.size()/(double)hotelOnPage));
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_500);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }
        return new Rout(PagePath.MAIN_PAGE, RoutingType.FORWARD);
    }
}
