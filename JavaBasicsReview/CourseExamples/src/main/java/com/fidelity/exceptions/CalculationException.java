package com.fidelity.exceptions;

public class CalculationException extends Exception {

	private static final long serialVersionUID = 1L;

	public CalculationException() {
		super();
	}

	protected CalculationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CalculationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CalculationException(String message) {
		super(message);
	}

	public CalculationException(Throwable cause) {
		super(cause);
	}
}
