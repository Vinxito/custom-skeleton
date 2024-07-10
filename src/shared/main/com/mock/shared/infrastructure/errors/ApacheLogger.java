package com.mock.shared.infrastructure.errors;

import com.mock.shared.domain.Injectable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;

@Injectable
public class ApacheLogger implements com.mock.shared.domain.Logger {

    private final Logger apacheLogger;

    public ApacheLogger() {
        this.apacheLogger = LogManager.getLogger(getClass());
    }

    public void warning(Exception exception) {
        apacheLogger.warn(exception);
    }

    @Override
    public void warning(String String) {
        apacheLogger.warn(String);
    }


    public void error(String message) {
        apacheLogger.error(message);
    }

    public void warning(String message,
                        Exception exception) {
        apacheLogger.warn(message,
                          exception);
    }

    public void warning(String message,
                        HashMap<String, Serializable> context,
                        Exception exception) {
        apacheLogger.warn(message,
                          context,
                          exception);
    }

    public void error(Exception exception) {
        apacheLogger.error(exception);
    }

    public void error(String message,
                      Exception exception) {
        apacheLogger.error(message,
                           exception);
    }

    public void error(String message,
                      HashMap<String, Serializable> context,
                      Exception exception) {
        apacheLogger.error(message,
                           context,
                           exception);
    }

    public void debug(String message) {
        apacheLogger.debug(message);
    }

    public void debug(String message,
                      Object object) {
        apacheLogger.debug(message,
                           object);
    }

    public void info(String message) {
        apacheLogger.info(message);
    }

    public void info(Exception exception) {
        apacheLogger.info(exception.getMessage());
    }

    public void info(Throwable throwable) {
        apacheLogger.info(throwable.getMessage());
    }
}
