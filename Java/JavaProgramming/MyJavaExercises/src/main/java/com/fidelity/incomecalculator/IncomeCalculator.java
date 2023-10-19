package com.fidelity.incomecalculator;

public class IncomeCalculator {

	private final double errorValue = 0.0;
	
	public double getAnnualPay(int hoursPerDay, double payPerHour, int daysUnpaidLeave) {
		if (daysUnpaidLeave < 0) {
			return errorValue;
		}		
		return getAnnualPay(52 * 5 - daysUnpaidLeave, hoursPerDay, payPerHour);
	}

	public double getAnnualPay(int daysWorked, int hoursPerDay, double payPerHour) {
		if (hoursPerDay < 0 || payPerHour < 0.0 || daysWorked < 0) {
			return errorValue;
		}
		return daysWorked * hoursPerDay * payPerHour;
	}
}
