package com.job.management.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobException extends Exception{
    public static final Logger logger = LoggerFactory.getLogger(JobException.class);

    public JobException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        logger.error(errorMessage,cause);

    }

    public JobException(String errorMessage) {
        logger.error(errorMessage);
    }

}
