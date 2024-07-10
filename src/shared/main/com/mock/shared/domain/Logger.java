package com.mock.shared.domain;

import java.io.Serializable;
import java.util.HashMap;

public interface Logger {

    void info(String message);

    void info(Exception exception);

    void info(Throwable throwable);

    void debug(String message);

    void debug(String message,
               Object object);

    void warning(String message,
                 Exception exception);

    void warning(String message,
                 HashMap<String, Serializable> context,
                 Exception exception);

    void warning(Exception exception);

    void warning(String String);

    void error(String String);

    void error(String message,
               Exception exception);

    void error(String message,
               HashMap<String, Serializable> context,
               Exception exception);

    void error(Exception exception);
}
