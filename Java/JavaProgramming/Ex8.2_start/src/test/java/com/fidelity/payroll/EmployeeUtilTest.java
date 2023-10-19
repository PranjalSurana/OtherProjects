package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmployeeUtilTest {

	private Employee[] emps;

	@BeforeEach
	void setUp() {
		emps = new Employee[] {
			new FullTimeEmployee("John Smith", LocalDate.of(2018, 8, 18), new BigDecimal("120000.0")),
			new PartTimeEmployee("Captain Jack", LocalDate.of(2019, 1, 1), new BigDecimal("30.0"), 100),
			new Consultant("Rose Tyler", LocalDate.of(2018, 7, 11), new BigDecimal("150000.0"), 10)
		};
	}

	@Test
	@DisplayName("Sort by employee name successfully")
	void testSortByName_Succeeds() {
		Employee[] empsSortedByName = new Employee[] {
			new PartTimeEmployee("Captain Jack", LocalDate.of(2019, 1, 1), new BigDecimal("30.0"), 100),
			new FullTimeEmployee("John Smith", LocalDate.of(2018, 8, 18), new BigDecimal("120000.0")),
			new Consultant("Rose Tyler", LocalDate.of(2018, 7, 11), new BigDecimal("150000.0"), 10)
		};
		
		EmployeeUtil.sortByName(emps);

		assertArrayEquals(empsSortedByName, emps);
	}

//	@Test
//	@DisplayName("Sort by monthly pay from high to low successfully")
//	void testSortByMonthlyPay_Succeeds() {
//		Employee[] empsSortedByMonthlyPay = new Employee[] {
//			new Consultant("Rose Tyler", LocalDate.of(2018, 7, 11), new BigDecimal("150000.0"), 10),
//			new FullTimeEmployee("John Smith", LocalDate.of(2018, 8, 18), new BigDecimal("120000.0")),
//			new PartTimeEmployee("Captain Jack", LocalDate.of(2019, 1, 1), new BigDecimal("30.0"), 100)
//		};
//		
//		EmployeeUtil.sortByMonthlyPay(emps);
//
//		assertArrayEquals(empsSortedByMonthlyPay, emps);
//	}

}
