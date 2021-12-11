package com.application.controller.command.implementation;

import com.application.entity.Room;
import com.application.controller.command.*;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ShowRooms implements Command {
    private static final String COUNT_ON_PAGE = "paginationCountEntityOnPage";
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> pageParam = Optional.ofNullable((String) request.getSession().getAttribute(SessionAttribute.PAGE_ROOMS));
        if (pageParam.isEmpty()){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_404);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        int page = Integer.parseInt(pageParam.get());
        int roomOnPage = Integer.parseInt(request.getServletContext().getInitParameter(COUNT_ON_PAGE));
        List<Room> roomListAll = (List<Room>) request.getServletContext().getAttribute(RequestParameter.LIST_ROOMS);
            List<Room> roomList = roomListAll.subList((page-1)*roomOnPage, Math.min((page) * roomOnPage, roomListAll.size()));
            request.setAttribute(RequestParameter.ENTITY_LIST, roomList);
            request.setAttribute(RequestParameter.CURRENT_PAGE, page);
            request.setAttribute(RequestParameter.COUNT_ALL_ENTITIES, roomListAll.size());
            request.setAttribute(RequestParameter.COUNT_PAGE,(int) Math.ceil(roomListAll.size()/(double) roomOnPage));
        return new Rout(PagePath.SHOW_ROOMS, RoutingType.FORWARD);
    }
}
