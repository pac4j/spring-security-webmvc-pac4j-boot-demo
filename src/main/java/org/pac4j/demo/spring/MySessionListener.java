package org.pac4j.demo.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener {

    private  static final Logger logger = LoggerFactory.getLogger(MySessionListener.class);

    @Override
    public void sessionCreated(final HttpSessionEvent event) {
        logger.debug("Session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent var1) {
        logger.debug("Session destroyed");
    }
}
