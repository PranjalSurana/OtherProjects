package com.fidelity.exceptions;

public class Calculator {
	public int parseAndCalculateResult(String arg1, String arg2) throws CalculationException {
	    try {
	        int val1 = Integer.parseInt(arg1.trim());
	        int val2 = Integer.parseInt(arg2.trim());
	        return val1 / val2;
	    } catch (NullPointerException e) {
	        throw new CalculationException("Not enough arguments given, cannot calculate", e);
	    } catch (NumberFormatException e) {
	    	throw new CalculationException("Invalid arguments given, cannot calculate", e);
	    } catch (Exception e) {
	    	throw new CalculationException("Unexpected error, cannot calculate", e);
	    }
	}
}
