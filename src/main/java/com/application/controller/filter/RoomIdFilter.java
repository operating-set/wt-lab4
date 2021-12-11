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
public class RoomIdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Optional<String> requestedRoomId = Optional.ofNullable(request.getParameter(RequestParameter.ROOM_ID));
        if (requestedRoomId.isPresent()) {
            session.setAttribute(SessionAttribute.ROOM_ID, requestedRoomId.get());
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
            if (!entry.getKey().equals(RequestParameter.ROOM_ID)){
                requestString.append(entry.getKey()).append("=").append(entry.getValue()[0]).append("&");
            }
        }
        requestString.deleteCharAt(requestString.length()-1);
        return requestString.toString();
    }
}
