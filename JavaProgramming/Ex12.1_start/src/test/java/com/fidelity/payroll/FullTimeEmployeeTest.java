package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FullTimeEmployeeTest {

	private BigDecimal zeroTwoDps;
	
	@BeforeEach
	void setUp() {
        zeroTwoDps = new BigDecimal("0.00");
	}

	@Test
	void testCalculateMonthlyPay1() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(new BigDecimal("1000.00"), ft.calculateMonthlyPay());
	}

	@Test
	void testCalculateMonthlyPay2() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10000.0"));
		assertEquals(new BigDecimal("833.33"), ft.calculateMonthlyPay());
	}

	/*
	 * Note that this test was not developed TDD. The superclass already provided this
	 * functionality. However this is a "characteristic" test added to describe the
	 * expected operation of the class's API. It is a type of regression test to protect
	 * against inadvertent changes in the future.
	 */
	@Test
	void testCalculateMonthlyPayBonus1() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10000.0"));
		assertEquals(new BigDecimal("843.33"), ft.calculateMonthlyPay(new BigDecimal("10.000")));
	}

	/*
	 * This test was implemented to test functionality previously tested by the superclass.
	 * Possibly not needed since the PartTimeEmployee class tests this already.
	 */
	@Test
	void testCalculateMonthlyPayBonus2() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10000.0"));
		assertEquals(new BigDecimal("833.33"), ft.calculateMonthlyPay(BigDecimal.ZERO));
	}

    @Test
    void testFTEmployeeEquality() {
    	Employee emp = new FullTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("1000.0"));
    	assertEquals(new FullTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("1000.00")), emp);
    	assertNotEquals(new FullTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("2000.00")),emp);
    }
	
    // Test functionality of superclass. Cannot test there because it is abstract.
    @Test
	void testCalculatePayToDate1() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(new BigDecimal("6000.00"), ft.calculatePayToDate(LocalDate.of(2019, 7, 11)));
	}

    @Test
	void testCalculatePayToDate2() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(new BigDecimal("7000.00"), ft.calculatePayToDate(LocalDate.of(2019, 8, 18)));
	}

    // negative test when current date is older than start date
    @Test
	void testCalculatePayToDateBeforeHireDate() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(zeroTwoDps, ft.calculatePayToDate(LocalDate.of(2018, 8, 18)));
	}
    
    // negative test when current date is null, now throws an exception
    @Test
	void testCalculatePayToDateWithNullDate() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
    	Exception e = assertThrows(NullPointerException.class, () -> {
    		ft.calculatePayToDate(null);
    	});
    	assertEquals("Current date cannot be null", e.getMessage());
    }

    // Test Comparable interface of superclass
	@Test
	void testCompareLessThan() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10.0"));
		Employee pt = new FullTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10.0"));
		
		assertTrue(ft.compareTo(pt) < 0);
	}

	@Test
	void testCompareGreaterThan() {
		Employee ft = new FullTimeEmployee("Mark Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10.0"));
		Employee pt = new FullTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10.0"));
		
		assertTrue(ft.compareTo(pt) > 0);
	}

	@Test
	void testCompareEqual() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("20.0"));
		Employee pt = new FullTimeEmployee("Jane Doe", LocalDate.of(2018, 1, 1), new BigDecimal("10.0"));
		
		assertEquals(0, ft.compareTo(pt));
	}

    // Tests for exceptions (fields of Employee are not tested in the other subclasses)
    @Test
    void testNullName() {
    	Exception e = assertThrows(NullPointerException.class, () -> {
    		new FullTimeEmployee(null, LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
    	});
    	assertEquals("Employee name cannot be null or empty", e.getMessage());
    }

    @Test
    void testEmptyName() {
    	Exception e = assertThrows(IllegalArgumentException.class, () -> {
    		new FullTimeEmployee(" ", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
    	});
    	assertEquals("Employee name cannot be null or empty", e.getMessage());
    }

    @Test
    void testNullHireDate() {
    	Exception e = assertThrows(NullPointerException.class, () -> {
    		new FullTimeEmployee("Jane Doe", null, new BigDecimal("12000.0"));
    	});
    	assertEquals("Employee hire date cannot be null", e.getMessage());
    }

    @Test
    void testNullSalary() {
    	Exception e = assertThrows(NullPointerException.class, () -> {
    		new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), null);
    	});
    	assertEquals("Employee salary must be positive", e.getMessage());
    }

    @Test
    void testNegativeSalary() {
    	Exception e = assertThrows(IllegalArgumentException.class, () -> {
    		new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("-100"));
    	});
    	assertEquals("Employee salary must be positive", e.getMessage());
    }

    @Test
    void testZeroSalary() {
    	Exception e = assertThrows(IllegalArgumentException.class, () -> {
    		new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), BigDecimal.ZERO);
    	});
    	assertEquals("Employee salary must be positive", e.getMessage());
    }

}
