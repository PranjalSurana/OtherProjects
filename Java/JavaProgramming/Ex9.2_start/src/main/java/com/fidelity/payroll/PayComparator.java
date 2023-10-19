package com.fidelity.payroll;

import java.util.Comparator;

public class PayComparator implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		// Note that this is a descending comparison on pay (the reverse a normal comparison)
		int payComp = o2.calculateMonthlyPay().compareTo(o1.calculateMonthlyPay());
		if (payComp == 0) {
			return o1.getName().compareTo(o2.getName());
		}
		return payComp;
	}

}