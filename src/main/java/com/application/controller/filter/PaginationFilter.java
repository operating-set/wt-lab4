package com.application.controller.filter;

import com.application.controller.command.RequestParameter;
import com.application.controller.command.SessionAttribute;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebFilter( urlPatterns = "/*")
public class PaginationFilter implements Filter {
    private static final String DEFAULT_PAGE = "defaultPage";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionAttribute.PAGE_MAIN) == null || request.getSession().getAttribute(SessionAttribute.PAGE_MAIN).equals("")) {
            session.setAttribute(SessionAttribute.PAGE_MAIN, request.getServletContext().getInitParameter(DEFAULT_PAGE));
        }

        if (session.getAttribute(SessionAttribute.PAGE_ROOMS) == null || request.getSession().getAttribute(SessionAttribute.PAGE_ROOMS).equals("")) {
            session.setAttribute(SessionAttribute.PAGE_ROOMS, request.getServletContext().getInitParameter(DEFAULT_PAGE));
        }

        if (session.getAttribute(SessionAttribute.PAGE_PERSONAL_AREA) == null || request.getSession().getAttribute(SessionAttribute.PAGE_PERSONAL_AREA).equals("")) {
            session.setAttribute(SessionAttribute.PAGE_PERSONAL_AREA, request.getServletContext().getInitParameter(DEFAULT_PAGE));
        }

        Optional<String> requestedPageMain = Optional.ofNullable(request.getParameter(RequestParameter.PAGE_MAIN_PAGINATION));
        if (requestedPageMain.isPresent()) {
            session.setAttribute(SessionAttribute.PAGE_MAIN, requestedPageMain.get());
            String requestString = removeLocaleParameter(request);
            response.sendRedirect(requestString);
            return;
        }

        Optional<String> requestedPageRooms = Optional.ofNullable(request.getParameter(RequestParameter.PAGE_ROOMS_PAGINATION));
        if (requestedPageRooms.isPresent()) {
            session.setAttribute(SessionAttribute.PAGE_ROOMS, requestedPageRooms.get());
            String requestString = removeLocaleParameter(request);
            response.sendRedirect(requestString);
            return;
        }

        Optional<String> requestedPagePersonalArea = Optional.ofNullable(request.getParameter(RequestParameter.PAGE_PERSONAL_AREA_PAGINATION));
        if (requestedPagePersonalArea.isPresent()) {
            session.setAttribute(SessionAttribute.PAGE_PERSONAL_AREA, requestedPagePersonalArea.get());
            String requestString = removeLocaleParameter(request);
            response.sendRedirect(requestString);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String removeLocaleParameter(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder requestString = new StringBuilder(request.getContextPath() + "/controller?");
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (!entry.getKey().equals(RequestParameter.PAGE_MAIN_PAGINATION) &&
                    !entry.getKey().equals(RequestParameter.PAGE_ROOMS_PAGINATION) &&
                    !entry.getKey().equals(RequestParameter.PAGE_PERSONAL_AREA_PAGINATION)){
                requestString.append(entry.getKey()).append("=").append(entry.getValue()[0]).append("&");
            }
        }
        requestString.deleteCharAt(requestString.length()-1);
        return requestString.toString();
    }
}
