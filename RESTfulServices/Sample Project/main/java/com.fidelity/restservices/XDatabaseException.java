package com.fidelity.restservices;

public class XYZDatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public XYZDatabaseException() {
		super();
	}

	public XYZDatabaseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public XYZDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public XYZDatabaseException(String message) {
		super(message);
	}

	public XYZDatabaseException(Throwable cause) {
		super(cause);
	}

}
