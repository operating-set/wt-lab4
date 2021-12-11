package com.application.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionLifeCycle implements HttpSessionListener {
    private final Logger logger = LogManager.getLogger(SessionLifeCycle.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session= sessionEvent.getSession();
        logger.info("Listener session created: Session Id" + session.getId());
        logger.info("Listener session created: Session InactiveInterval" + session.getMaxInactiveInterval());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        HttpSession session= sessionEvent.getSession();
        logger.info("Listener session destroyed: Session Id" + session.getId());
        logger.info("Listener session destroyed: Session InactiveInterval" + session.getMaxInactiveInterval());
    }
}
