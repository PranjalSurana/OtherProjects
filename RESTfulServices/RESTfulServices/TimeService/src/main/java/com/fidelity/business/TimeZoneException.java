package com.fidelity.business;

public class TimeZoneException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeZoneException() {
		super();
	}

	public TimeZoneException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TimeZoneException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeZoneException(String message) {
		super(message);
	}

	public TimeZoneException(Throwable cause) {
		super(cause);
	}

}
