package com.fidelity.india.secondary.assessment.controller;

public class MarinaDatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MarinaDatabaseException() {
		super();
	}

	public MarinaDatabaseException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MarinaDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public MarinaDatabaseException(String message) {
		super(message);
	}

	public MarinaDatabaseException(Throwable cause) {
		super(cause);
	}

}
