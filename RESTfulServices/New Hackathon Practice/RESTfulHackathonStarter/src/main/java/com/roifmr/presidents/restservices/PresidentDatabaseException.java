package com.roifmr.presidents.restservices;

public class PresidentDatabaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PresidentDatabaseException() {
        super();
    }

    public PresidentDatabaseException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PresidentDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresidentDatabaseException(String message) {
        super(message);
    }

    public PresidentDatabaseException(Throwable cause) {
        super(cause);
    }

}
