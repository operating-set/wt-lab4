package com.application.service.mail;

public interface MailService {
    String getEmail();
    int getLinkRegistrationHash();
    boolean sendApprovalRegistration (String email,String password, String role, String locale);
    boolean sendApprovalBooking (String email, Integer userId, String role, Integer orderId, String locale);
}
