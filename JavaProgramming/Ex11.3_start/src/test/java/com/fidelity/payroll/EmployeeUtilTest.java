package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeUtilTest {

	private List<Employee> emps;
	private List<Employee> expected = new ArrayList<>();
	private Employee ft;
	private Employee pt;
	private Employee con;
	private Employee amy;
	
	@BeforeEach
	void setUp() {
		emps = new ArrayList<>();
		ft = new FullTimeEmployee("John Smith", LocalDate.of(2018, 8, 18), new BigDecimal("10000.0"));
		pt = new PartTimeEmployee("Captain Jack", LocalDate.of(2019, 1, 1), new BigDecimal("10.0"), 100);
		con = new Consultant("Rose Tyler", LocalDate.of(2018, 7, 11), new BigDecimal("8000.0"), 10);
		emps.add(ft);
		emps.add(pt);
		emps.add(con);
		amy = new FullTimeEmployee("Amy Pond", LocalDate.of(2019, 1, 20), new BigDecimal("12000.0"));
		expected = new ArrayList<>();
		expected.add(pt);
		expected.add(ft);
		expected.add(con);
	}

	@Test
	void testTotalSalary1() {
		assertEquals(new BigDecimal("2633.33"), EmployeeUtil.calcTotalMonthlyPay(emps));
	}

	@Test
	void testTotalSalary2() {
		emps.add(new FullTimeEmployee("Amy Pond", LocalDate.of(2019, 1, 20), new BigDecimal("12000.0")));
		assertEquals(new BigDecimal("3633.33"), EmployeeUtil.calcTotalMonthlyPay(emps));
	}

	// Optional exercise to calculate total pay to date
	@Test
	void testTotalToDate1() {
		assertEquals(new BigDecimal("7333.32"), EmployeeUtil.calcTotalPayToDate(emps, LocalDate.of(2019, 1, 1)));
	}

	@Test
	void testTotalToDate2() {
		assertEquals(new BigDecimal("800.00"), EmployeeUtil.calcTotalPayToDate(emps, LocalDate.of(2018, 8, 11)));
	}
	
	@Test
	void testSort() {
		EmployeeUtil.sort(emps);
		assertEquals(pt, emps.get(0));
		assertEquals(ft, emps.get(1));
		assertEquals(con, emps.get(2));
	}

	// Test whole list in a single assertion
	@Test
	void testSortWithSingleAssertion() {
		EmployeeUtil.sort(emps);
		assertIterableEquals(expected, emps);
	}

	@Test
	void testSortWithComparator() {
		emps.add(amy);
		expected.add(0, amy);
		EmployeeUtil.sort(emps, new PayComparator());
		assertIterableEquals(expected, emps);
	}

	@Test
	void testSortWithAnonymousInnerClass() {
		emps.add(amy);
		expected.add(0, amy);
		EmployeeUtil.sort(emps, new Comparator<Employee>() {
			@Override
			public int compare(Employee o1, Employee o2) {
				// Note that this is a descending comparison on pay (the reverse a normal comparison)
				int payComp = o2.calculateMonthlyPay().compareTo(o1.calculateMonthlyPay());
				if (payComp == 0) {
					return o1.getName().compareTo(o2.getName());
				}
				return payComp;
			}
		});
		assertIterableEquals(expected, emps);
	}

	// This isn't asked for in the question, but it is a useful example
	@Test
	void testSortWithNamedInnerClass() {
		emps.add(amy);
		expected.add(0, amy);
		EmployeeUtil.sort(emps, new Employee.PayComparator());
		assertIterableEquals(expected, emps);
	}

	// below negative tests to make sure arrays are valid and in bounds, now throw exceptions
	@Test
	void testTotalToDateNull() {
    	Exception e = assertThrows(NullPointerException.class, () -> {
    		EmployeeUtil.calcTotalPayToDate(null, LocalDate.of(2018, 8, 11));
    	});
    	assertEquals("Cannot calculate total pay for null list", e.getMessage());
	}

	@Test
	void testTotalSalaryNull() {
    	Exception e = assertThrows(NullPointerException.class, () -> {
    		EmployeeUtil.calcTotalMonthlyPay(null);
    	});
    	assertEquals("Cannot calculate total pay for null list", e.getMessage());
	}

}
