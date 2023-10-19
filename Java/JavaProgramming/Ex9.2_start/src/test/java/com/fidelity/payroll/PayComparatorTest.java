package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayComparatorTest {

	PayComparator pc;
	
	Employee ft;
	Employee pt;
	Employee con;
	
	@BeforeEach
	void setUp() {
		pc = new PayComparator();
		ft = new FullTimeEmployee("John Smith", LocalDate.of(2018, 8, 18), new BigDecimal("10000.0"));
		pt = new PartTimeEmployee("Captain Jack", LocalDate.of(2019, 1, 1), new BigDecimal("10.0"), 100);
		con = new Consultant("Rose Tyler", LocalDate.of(2018, 7, 11), new BigDecimal("8000.0"), 10);
	}

	@Test
	void testPayLessThan() {
		assertTrue(pc.compare(pt, ft) < 0);
	}

	@Test
	void testPayGreaterThan() {
		assertTrue(pc.compare(con, ft) > 0);
	}

	@Test
	void testPayEqualNameLessThan() {
		Employee amy = new FullTimeEmployee("Amy Pond", LocalDate.of(2017, 8, 18), new BigDecimal("10000.0"));
		assertTrue(pc.compare(amy, ft) < 0);
	}
	
	@Test
	void testPayEqualNameGreaterThan() {
		Employee river = new FullTimeEmployee("River Song", LocalDate.of(2017, 8, 18), new BigDecimal("10000.0"));
		assertTrue(pc.compare(river, ft) > 0);
	}
	
	@Test
	void testPayEqualNameEqual() {
		Employee ft2 = new FullTimeEmployee("John Smith", LocalDate.of(2017, 8, 18), new BigDecimal("10000.0"));
		assertEquals(0, pc.compare(ft2, ft));
	}

}
