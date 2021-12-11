package com.application.controller.command.implementation;

import com.application.controller.command.*;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Optional;

public class ShowContinueSignUp implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String absoluteURL = getAbsoluteURL(request);
        int hashEmailRecieve = absoluteURL.hashCode();
        Integer hashCodeSend = (Integer) Optional.ofNullable(session.getAttribute(SessionAttribute.HASH_LINK)).orElse(0);
        if (hashCodeSend == hashEmailRecieve){
            session.setAttribute(SessionAttribute.EMAIL,request.getParameter(RequestParameter.EMAIL));
            session.setAttribute(SessionAttribute.PASSWORD,request.getParameter(RequestParameter.PASSWORD));
            session.setAttribute(SessionAttribute.SIGN_UP_ROLE,request.getParameter(RequestParameter.ROLE));
            return new Rout(PagePath.CONTINUE_SIGN_UP_PAGE, RoutingType.FORWARD);
        } else {
            session.setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_RECEIVE_MAIL);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }
    }

    private String getAbsoluteURL (HttpServletRequest request){
        StringBuilder str= new StringBuilder(request.getRequestURL() + "?");
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements())
        {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                str.append(paramName + "=" + paramValue);
            }
            str.append("&");
        }
        return str.substring(0,str.length()-1);
    }
}
