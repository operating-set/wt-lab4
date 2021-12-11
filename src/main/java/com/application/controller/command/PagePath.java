package com.application.controller.command;

public final class PagePath {
    public static final String MAIN_PAGE = "WEB-INF/pages/main.jsp";
    public static final String MAIN_PAGE_REDIRECT = "/controller?command=MAIN";

    public static final String SIGN_IN_PAGE = "WEB-INF/pages/signin.jsp";
    public static final String SIGN_IN_PAGE_REDIRECT = "/controller?command=SHOW_SIGN_IN";

    public static final String ERROR_PAGE = "WEB-INF/pages/error.jsp";
    public static final String ERROR_PAGE_REDIRECT = "/controller?command=SHOW_ERROR_PAGE";

    public static final String SIGN_UP_PAGE = "WEB-INF/pages/startsignup.jsp";

    public static final String CONTINUE_SIGN_UP_PAGE = "WEB-INF/pages/continuesignup.jsp";
    public static final String CONTINUE_SIGN_UP_PAGE_REDIRECT ="/controller?command=SHOW_CONTINUE_SIGN_UP";

    public static final String MAIL_CHECK = "WEB-INF/pages/mailcheck.jsp";
    public static final String MAIL_CHECK_REDIRECT = "/controller?command=SHOW_CHECK_MAIL_PAGE";

    public static final String EDIT_PAGE = "WEB-INF/pages/edit.jsp";
    public static final String EDIT_PAGE_REDIRECT="/controller?command=SHOW_EDIT";

    public static final String SHOW_EDIT_PASSWORD = "WEB-INF/pages/editpassword.jsp";
    public static final String SHOW_EDIT_PASSWORD_REDIRECT="/controller?command=SHOW_EDIT_PASSWORD";

    public static final String SUCCESS_EDIT_PASSWORD = "WEB-INF/pages/successeditpassword.jsp";
    public static final String SUCCESS_EDIT_PASSWORD_REDIRECT = "/controller?command=SHOW_SUCCESS_PASSWORD_PAGE";

    public static final String SHOW_BOOKING = "WEB-INF/pages/booking.jsp";
    public static final String SHOW_BOOKING_REDIRECT = "/controller?command=SHOW_BOOKING";

    public static final String SHOW_ROOMS = "WEB-INF/pages/showrooms.jsp";
    public static final String SHOW_ROOMS_REDIRECT = "/controller?command=SHOW_ROOMS";

    public static final String WAIT_APPROVAL_BOOKING = "WEB-INF/pages/waitapprovalbooking.jsp";
    public static final String WAIT_APPROVAL_BOOKING_REDIRECT = "/controller?command=WAIT_APPROVAL_BOOKING";

    public static final String SUCCESS_APPROVAL_BOOKING = "WEB-INF/pages/successaprovalbooking.jsp";
    public static final String SUCCESS_APPROVAL_BOOKING_REDIRECT = "/controller?command=SUCCESS_APPROVAL_BOOKING";

    public static final String SHOW_PERSONAL_AREA = "WEB-INF/pages/personalarea.jsp";
    public static final String SHOW_PERSONAL_AREA_REDIRECT = "/controller?command=SHOW_PERSONAL_AREA";

    public static final String SHOW_ADD_HOTEL = "WEB-INF/pages/addhotel.jsp";
    public static final String SHOW_ADD_HOTEL_REDIRECT = "/controller?command=SHOW_ADD_HOTEL";

    private PagePath(){};
}
