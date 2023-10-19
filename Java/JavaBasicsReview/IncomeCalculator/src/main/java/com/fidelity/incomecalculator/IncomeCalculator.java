package com.fidelity.incomecalculator;

public class IncomeCalculator {
	
	public double errorValue = 0.0;

	public double getAnnualPay(int hoursWorkedPerDay, double hourlyPay, int unpaidHolidays) {
		if (unpaidHolidays < 0) {
			return errorValue;
		}		
		return getAnnualPay(52 * 5 - unpaidHolidays, hoursWorkedPerDay, hourlyPay);
	}
	
	public double getAnnualPay(int daysWorked, int hoursWorkedPerDay, double hourlyPay) {
		if (hoursWorkedPerDay < 0 || hourlyPay < 0.0 || daysWorked < 0) {
			return errorValue;
		}
		return daysWorked * hoursWorkedPerDay * hourlyPay;
	}
}