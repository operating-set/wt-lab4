package com.application.controller.command.implementation;

import com.application.entity.Role;
import com.application.entity.TourOrgInfo;
import com.application.entity.User;
import com.application.entity.UserInfo;
import com.application.controller.command.*;
import com.application.controller.command.rout.Rout;
import com.application.controller.command.rout.RoutingType;
import com.application.service.ServiceFactory;
import com.application.service.exception.ServiceException;
import com.application.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ContinueSignUp implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String email = Optional.ofNullable((String) session.getAttribute(SessionAttribute.EMAIL)).orElse("");
        String password = Optional.ofNullable((String) session.getAttribute(SessionAttribute.PASSWORD)).orElse("");
        String role = Optional.ofNullable((String) session.getAttribute(SessionAttribute.SIGN_UP_ROLE)).orElse("");
        String name = Optional.ofNullable(request.getParameter(RequestParameter.NAME)).orElse("");
        String surname = Optional.ofNullable(request.getParameter(RequestParameter.SURNAME)).orElse("");
        String middleName = Optional.ofNullable(request.getParameter(RequestParameter.MIDDLE_NAME)).orElse("");
        String phone = Optional.ofNullable(request.getParameter(RequestParameter.PHONE)).orElse("");
        String passport = Optional.ofNullable(request.getParameter(RequestParameter.PASSPORT)).orElse("");
        String dateOfBirthday = Optional.ofNullable(request.getParameter(RequestParameter.DATE_OF_BIRTHDAY)).orElse("");
        String gender = Optional.ofNullable(request.getParameter(RequestParameter.GENDER)).orElse("");
        String country = Optional.ofNullable(request.getParameter(RequestParameter.COUNTRY)).orElse("");
        String tourOrg = Optional.ofNullable(request.getParameter(RequestParameter.TOURORG)).orElse("");
        String licence = Optional.ofNullable(request.getParameter(RequestParameter.LICENCE)).orElse("");

        if (email.isEmpty() || password.isEmpty() || role.isEmpty() || name.isEmpty()
        || surname.isEmpty() || phone.isEmpty() || passport.isEmpty() || dateOfBirthday.isEmpty()
        || gender.isEmpty() || country.isEmpty() || (role.equals(Role.TOUROPERATOR.name()) && (tourOrg.isEmpty() || licence.isEmpty()))){
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.INVALID_SIGNUP_DATA);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.valueOf(role));

        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setSurname(surname);
        userInfo.setMiddleName(middleName);
        userInfo.setPhone(phone);
        userInfo.setPassport(passport);
        userInfo.setDateBirthDay(dateOfBirthday);
        userInfo.setSex(gender);
        userInfo.setCountry(country);

        TourOrgInfo tourOrgInfo = new TourOrgInfo();
        tourOrgInfo.setTourOrg(tourOrg);
        tourOrgInfo.setLicense(licence);

        UserService userService = ServiceFactory.getInstance().getUserService();

        try {
            user = userService.register(user,userInfo,tourOrgInfo);
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_DONT_REGISTRATION);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

        if (user != null){
            session.removeAttribute(SessionAttribute.EMAIL);
            session.removeAttribute(SessionAttribute.PASSWORD);
            session.removeAttribute(SessionAttribute.SIGN_UP_ROLE);

            String userName = user.getUserInfo().getName()+" "+user.getUserInfo().getSurname();
            session.setAttribute(SessionAttribute.USER_ID, user.getId());
            session.setAttribute(SessionAttribute.USER_NAME, userName);
            session.setAttribute(SessionAttribute.ROLE, user.getRole());
            return new Rout(PagePath.MAIN_PAGE_REDIRECT, RoutingType.REDIRECT);
        } else {
            request.getSession().setAttribute(SessionAttribute.ERROR, ErrorKey.ERROR_DONT_REGISTRATION);
            return new Rout(PagePath.ERROR_PAGE_REDIRECT, RoutingType.REDIRECT);
        }

    }
}
