package com.application.service.mail.impl;

import com.application.entity.EntityConstant;
import com.application.controller.command.CommandName;
import com.application.controller.command.RequestParameter;
import com.application.service.exception.ServiceException;
import com.application.service.mail.MailService;
import com.application.service.mail.MailSessionCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class MailServiceImpl implements MailService {
    private final Logger logger = LogManager.getLogger(MailServiceImpl.class);
    private final Properties mailProperties = loadMailProperties();
    private static final String MAIL_USER_NAME = "mail.user.name";
    private static final String MAIL_MESSAGE_BUNDLE = "mailcontent";
    private static final String APPROVAL_REGISTRATION = "approval-registration-content";
    private static final String SUBJECT_REGISTRATION = "subject-registration";
    private static final String APPROVAL_BOOKING_ACCEPT = "approval-booking-accept-content";
    private static final String APPROVAL_BOOKING_CANCEL = "approval-booking-cancel-content";
    private static final String SUBJECT_BOOKING = "subject-booking";
    private static final String MESSAGE_CONTENT_TYPE = "text/plain; charset=UTF-8";
    private static final String URL="url";
    private static final String START_PARAM="?";
    private static final String NEXT_PARAM="&";
    private ResourceBundle mailBundle;
    private String linkRegistration ="";

    @Override
    public String getEmail() {
        if (!mailProperties.isEmpty()){
            return mailProperties.getProperty(MAIL_USER_NAME);
        } else {
            return "";
        }
    }

    @Override
    public int getLinkRegistrationHash() {
        return this.linkRegistration.hashCode();
    }

    @Override
    public boolean sendApprovalRegistration(String email, String password, String role, String locale) {
        if (!mailProperties.isEmpty()){
            try {
            MimeMessage message = createMessage(mailProperties);
            mailBundle = getResourceBundle(locale);
            String content = createContentRegistration(email, password, role);
            String subject = mailBundle.getString(SUBJECT_REGISTRATION);
            sendMessage(message, subject, content, email);
            } catch (ServiceException e){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean sendApprovalBooking(String email, Integer userId, String role, Integer orderId, String locale) {
        if (!mailProperties.isEmpty()){
            try {
                MimeMessage message = createMessage(mailProperties);
                mailBundle = getResourceBundle(locale);
                String content = createContentBooking(String.valueOf(userId),String.valueOf(orderId),role);
                String subject = mailBundle.getString(SUBJECT_BOOKING);
                sendMessage(message, subject, content, email);
            } catch (ServiceException e){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private Properties loadMailProperties() {
        Properties mailProperties = new Properties();
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("mail.properties");
        if (fileInputStream == null) {
            logger.error("File mail.properties doesn't exist");
            return new Properties();
        } else {
            try {
                mailProperties.load(fileInputStream);
            } catch (IOException e) {
                logger.error("Unable to read mail properties. {}", e.getMessage());
                return new Properties();
            }
            return mailProperties;
        }
    }

    private MimeMessage createMessage(Properties mailProperties) throws ServiceException {
        MailSessionCreator creator = new MailSessionCreator(mailProperties);
        Session session = creator.createSession();
        return new MimeMessage(session);
    }

    private ResourceBundle getResourceBundle(String locale) {
        Locale messageLocale = new Locale(locale);
        return ResourceBundle.getBundle(MAIL_MESSAGE_BUNDLE, messageLocale);
    }

    private void sendMessage(MimeMessage message, String subject, String content, String recipientEmail) throws ServiceException {
        try {
            message.setSubject(subject);
            message.setContent(content, MESSAGE_CONTENT_TYPE);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Unable to send message. {}", e.getMessage());
            throw new ServiceException("Unable to send message.", e);
        }
    }

    private String createContentRegistration(String email, String password, String role) throws ServiceException {
        return mailBundle.getString(APPROVAL_REGISTRATION)+" "+ createLinkRegistration(email,password,role);
    }

    private String createContentBooking(String userId, String orderId, String role) throws ServiceException{
        return mailBundle.getString(APPROVAL_BOOKING_ACCEPT)+" "+createLinkBookingAccept(userId,orderId,role) + "\n" +
                mailBundle.getString(APPROVAL_BOOKING_CANCEL)+" "+createLinkBookingCancel(userId,orderId,role);
    }

    private String createLinkRegistration(String email, String password, String role) throws ServiceException {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
            Properties config = new Properties();
            config.load(input);
            this.linkRegistration = config.getProperty(URL)+START_PARAM+ RequestParameter.COMMAND+"="+ CommandName.SHOW_CONTINUE_SIGN_UP+
                    NEXT_PARAM+RequestParameter.EMAIL+"="+email+
                    NEXT_PARAM+RequestParameter.PASSWORD+"="+password+
                    NEXT_PARAM+RequestParameter.ROLE+"="+role;
            return this.linkRegistration;
        } catch (IOException e){
            throw new ServiceException(e);
        }
    }

    private String createLinkBookingAccept(String userId, String orderId, String role) throws ServiceException {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
            Properties config = new Properties();
            config.load(input);
            return config.getProperty(URL)+START_PARAM+RequestParameter.COMMAND+"="+ CommandName.APPROVAL_BOOKING+
                    NEXT_PARAM+RequestParameter.USER_ID+"="+userId+
                    NEXT_PARAM+RequestParameter.ORDER_ID+"="+orderId+
                    NEXT_PARAM+RequestParameter.ROLE+"="+role +
                    NEXT_PARAM+RequestParameter.STATUS+"="+ EntityConstant.ORDER_ACCEPT;
        } catch (IOException e){
            throw new ServiceException(e);
        }
    }

    private String createLinkBookingCancel(String userId, String orderId, String role) throws ServiceException {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
            Properties config = new Properties();
            config.load(input);
            return config.getProperty(URL)+START_PARAM+RequestParameter.COMMAND+"="+ CommandName.APPROVAL_BOOKING+
                    NEXT_PARAM+RequestParameter.USER_ID+"="+userId+
                    NEXT_PARAM+RequestParameter.ORDER_ID+"="+orderId+
                    NEXT_PARAM+RequestParameter.ROLE+"="+role +
                    NEXT_PARAM+RequestParameter.STATUS+"="+ EntityConstant.ORDER_CANCEL;
        } catch (IOException e){
            throw new ServiceException(e);
        }
    }
}
