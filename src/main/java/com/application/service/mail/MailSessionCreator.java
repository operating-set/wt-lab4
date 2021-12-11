package com.application.service.mail;

import com.application.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class MailSessionCreator {
    private static final Logger logger = LogManager.getLogger();
    private static final String DEFAULT_PORT = "465";
    private static final String DEFAULT_HOST = "smtp.gmail.com";
    private static final String SMPT_HOST_PROP = "mail.smtp.host";
    private static final String SMPT_PORT_PROP = "mail.smtp.port";
    private static final String USER_NAME_PROP = "mail.user.name";
    private static final String USER_PASS_PROP = "mail.user.password";
    private static final String SMTP_SOCKET_FACTORY_PORT_PROP = "mail.smtp.socketFactory.port";
    private static final String SMTP_AUTH_PROP = "mail.smtp.auth";
    private static final String SMTP_SOCKET_FACTORY_CLASS_PROP = "mail.smtp.socketFactory.class";
    private static final String SMTP_SSL_PROTOCOLS_PROP = "mail.smtp.ssl.protocols";
    private static final String SMTP_STRATTLS_ENABLE_PROP = "mail.smtp.starttls.enable";
    private static final String SMTP_SSL_TRUST_PROP = "mail.smtp.ssl.trust";
    private static final String SMTP_SERVER_IDENTITY_PROP = "mail.smtp.ssl.checkserveridentity";

    private final String userName;
    private final String userPassword;
    private final Properties sessionProperties;

    public MailSessionCreator(Properties configProperties) throws ServiceException {
        String smtpHost = configProperties.getProperty(SMPT_HOST_PROP);
        String smtpPort = configProperties.getProperty(SMPT_PORT_PROP);
        userName = configProperties.getProperty(USER_NAME_PROP);
        userPassword = configProperties.getProperty(USER_PASS_PROP);

        if (userName == null || userPassword == null) {
            logger.error("Wrong mail authentication properties.");
            throw new ServiceException("Wrong mail authentication properties.");
        }

        if (smtpHost == null || smtpPort == null) {
            smtpHost = DEFAULT_HOST;
            smtpPort = DEFAULT_PORT;
        }

        sessionProperties = new Properties();
        sessionProperties.put(SMPT_HOST_PROP, smtpHost);
        sessionProperties.put(SMPT_PORT_PROP, smtpPort);
        sessionProperties.put(SMTP_SOCKET_FACTORY_PORT_PROP, smtpPort);
        sessionProperties.put(SMTP_AUTH_PROP, true);
        sessionProperties.put(SMTP_SSL_PROTOCOLS_PROP, "TLSv1.2");
        sessionProperties.put(SMTP_STRATTLS_ENABLE_PROP, true);
        sessionProperties.put(SMTP_SSL_TRUST_PROP, smtpHost);
        sessionProperties.put(SMTP_SERVER_IDENTITY_PROP, true);
        sessionProperties.put(SMTP_SOCKET_FACTORY_CLASS_PROP, "javax.net.ssl.SSLSocketFactory");
    }

    public Session createSession() {
        return Session.getDefaultInstance(sessionProperties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });
    }
}