package com.fidelity.payroll;

public class PayrollException extends RuntimeException {

	private static final long serialVersionUID = 6743072889929915332L;

	public PayrollException() {
	}

	public PayrollException(String message) {
		super(message);
	}

	public PayrollException(Throwable cause) {
		super(cause);
	}

	public PayrollException(String message, Throwable cause) {
		super(message, cause);
	}

	protected PayrollException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
