package com.job.management.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookException extends Exception{
    public static final Logger logger = LoggerFactory.getLogger(BookException.class);

    public BookException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        logger.error(errorMessage,cause);

    }

    public BookException(String errorMessage) {
        logger.error(errorMessage);
    }

}
