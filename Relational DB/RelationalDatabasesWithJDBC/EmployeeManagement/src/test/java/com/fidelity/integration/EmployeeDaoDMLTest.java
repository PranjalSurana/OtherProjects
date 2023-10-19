package com.fidelity.integration;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import com.fidelity.model.Employee;

class EmployeeDaoDMLTest {
	JdbcTemplate jdbcTemplate;
	DbTestUtils dbTestUtils;
	EmployeeDao dao;
	SimpleDataSource dataSource;
	Connection connection;
	TransactionManager transactionManager;

	@BeforeEach
	void setUp() throws SQLException {
		dataSource = new SimpleDataSource();
		connection = dataSource.getConnection();
		transactionManager = new TransactionManager(dataSource);
		transactionManager.startTransaction();

		dbTestUtils = new DbTestUtils(connection);
		jdbcTemplate = dbTestUtils.initJdbcTemplate();

		dao = new EmployeeDaoOracleImpl(dataSource);
	}

	@AfterEach
	void tearDown() throws SQLException {
		transactionManager.rollbackTransaction();
		dataSource.shutdown();
	}

	/***** DML Tests *****/

	@Test
	void testInsertEmployee() throws SQLException {
		int oldSize = countRowsInTable(jdbcTemplate, "emp");
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "emp", "empno = 8000"));
		Employee new8000 = new Employee(8000,
				"HEYES",
				"ANALYST",
				7934,
				LocalDate.of(2023, 1, 17),
				new BigDecimal("50000.00"),
				new BigDecimal("200.00"),
				10);

		Employee employee = dao.insertEmployee(new8000);

		assertEquals(new8000, employee);
		int newSize = countRowsInTable(jdbcTemplate, "emp");
		assertEquals(oldSize + 1, newSize);
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "emp", """
					empno = 8000
				and ename = 'HEYES'
				and job = 'ANALYST'
				and mgr = 7934
				and hiredate = '17-JAN-2023'
				and sal = 50000
				and comm = 200
				and deptno = 10
			"""));
	}

	@Test
	void testInsertEmployeeThrowsException() throws SQLException {
		int dupePk = 7369;
		Employee upd7369 = new Employee(dupePk, "HEYES", "ANALYST", 7934, LocalDate.of(2023, 1, 17),
				new BigDecimal("500.00"), new BigDecimal("2"), 10);

		assertThrows(DatabaseException.class, () -> {
			dao.insertEmployee(upd7369);
		});
	}

	@Test
	void testUpdateEmployee() throws SQLException {
		int oldSize = countRowsInTable(jdbcTemplate, "emp");
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "emp",
				"empno = 7369 and ename = 'HEYES'"));
		Employee upd7369 = new Employee(7369,
				"LEE",
				"FSE",
				7566,
				LocalDate.of(2023, 2, 1),
				new BigDecimal("10000.00"),
				new BigDecimal("3000.00"),
				20);

		Employee employee = dao.updateEmployee(upd7369);

		assertEquals(upd7369, employee);

		int newSize = countRowsInTable(jdbcTemplate, "emp");
		assertEquals(oldSize, newSize);
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "emp", """
					empno = 7369
				and ename = 'LEE'
				and job = 'FSE'
				and mgr = 7566
				and hiredate = '01-FEB-2023'
				and sal = 10000
				and comm = 3000
				and deptno = 20
			"""));
	}

	@Test
	void testUpdateEmployeeThrowsException() throws SQLException {
		int invalidDeptId = 42;
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "emp", "empno = 7369"));
		Employee upd7369 = new Employee(7369, "LEE", "FSE", 7566, LocalDate.of(2023, 2, 1), new BigDecimal("100000.00"),
				new BigDecimal("3000.00"), invalidDeptId);

		assertThrows(DatabaseException.class, () -> {
			dao.updateEmployee(upd7369);
		});
	}

	@Test
	void testDeleteEmployee() throws SQLException {
		int oldSize = countRowsInTable(jdbcTemplate, "emp");
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "emp", "empno = 7369"));

		boolean success = dao.deleteEmployee(7369);

		assertTrue(success);
		assertEquals(oldSize - 1, countRowsInTable(jdbcTemplate, "emp"));
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "emp", "empno = 7369"));
	}

	@Test
	void testDeleteEmployeeNotPresent() throws SQLException {
		int oldSize = countRowsInTable(jdbcTemplate, "emp");
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "emp", "empno = 9999"));

		boolean success = dao.deleteEmployee(9999);

		assertFalse(success);
		assertEquals(oldSize, countRowsInTable(jdbcTemplate, "emp"));
	}
}
