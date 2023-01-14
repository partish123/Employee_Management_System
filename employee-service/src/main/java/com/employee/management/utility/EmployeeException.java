package com.employee.management.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeException extends Exception{
    public static final Logger logger = LoggerFactory.getLogger(EmployeeException.class);

    public EmployeeException(String errorMessage, Throwable err) {
        super(errorMessage, err);
        logger.error(errorMessage,err);

    }

    public EmployeeException(String errorMessage) {
        logger.error(errorMessage);
    }
}
