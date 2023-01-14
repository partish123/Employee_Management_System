package com.user.management.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserException extends Exception{
    public static final Logger logger = LoggerFactory.getLogger(UserException.class);

    public UserException(String errorMessage, Throwable err) {
        super(errorMessage, err);
        logger.error(errorMessage,err);

    }

    public UserException(String errorMessage) {
        logger.error(errorMessage);
    }
}
