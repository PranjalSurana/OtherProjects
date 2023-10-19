package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.business.Department;
import com.fidelity.business.Employee;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class DepartmentDaoMyBatisImplTest {

	@Autowired
	private DepartmentDaoMyBatisImpl dao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private List<Department> allDepartmentsOnly = Arrays.asList(
			new Department(10, "ACCOUNTING", "NEW YORK"),
			new Department(20, "RESEARCH", "DALLAS"),
			new Department(30, "SALES", "CHICAGO"),
			new Department(40, "OPERATIONS", "BOSTON")
	);

	@Test
	void testGetAllDepartments() {

		List<Department> departments = dao.getAllDepartments();

		// getAllDepartments() doesn't select Employees, so all Departments employees lists are null
		assertEquals(allDepartmentsOnly, departments);
	}

	@Test
	void testGetAllDepartmentsAndEmployees() {
		// ARRANGE
		var emp7782 = new Employee(7782, "CLARK", "MANAGER", 7839, LocalDate.of(1981, 6, 9), 2450.0, 0.0, 10);
		var emp7839 = new Employee(7839, "KING", "PRESIDENT", 0, LocalDate.of(1981, 11, 17), 5000.0, 0.0, 10);
		var emp7934 = new Employee(7934, "MILLER", "CLERK", 7782, LocalDate.of(1982, 1, 23), 1300.0, 0.0, 10);

		var dept10 = new Department(10, "ACCOUNTING", "NEW YORK",
				Arrays.asList(emp7782, emp7839, emp7934));
		var dept40 = new Department(40, "OPERATIONS", "BOSTON", Arrays.asList());

		// ACT
		var departments = dao.getAllDepartmentsAndEmployees();

		// ASSERT
		assertEquals(4, departments.size());
		assertTrue(departments.contains(dept10));  // verifies Department properties, including Employees list
		assertTrue(departments.contains(dept40));
	}

	@Test
	void testInsertDepartment() {
		// ARRANGE
		var expectedRowCount = countRowsInTable(jdbcTemplate, "dept") + 1;
		var newDeptId = jdbcTemplate.queryForObject("select max(deptno) from dept", Integer.class) + 10;
		// new department to be inserted into the database
		var newDept = new Department(newDeptId, "Test Dept", "Test Loc");

		// ACT
		var success = dao.insertDepartment(newDept);

		// ASSERT
		assertTrue(success);
		// verify that the number of department rows increased by 1
		assertEquals(expectedRowCount, countRowsInTable(jdbcTemplate, "dept"));
		// Verify that all Department properties were inserted correctly.
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "dept", String.format("""
				 deptno = %d
			 and dname = 'Test Dept'
			 and loc = 'Test Loc'
		 """, newDept.getId())));

//		// First, select the new row from the database.
//		Map<String, Object> deptFromDb = jdbcTemplate.queryForMap(
//			"select deptno, dname, loc from dept where deptno = " + newDept.getId()
//		);
//		// Next, create a Map of the new row's expected column values.
//		Map<String, Object> expectedDept = Map.of(
//			"deptno", newDept.getId(),
//			"dname", newDept.getName(),
//			"loc", newDept.getLocation()
//		);
//		// Finally, compare the two maps using a helper function.
//		assertTrue(DbTestUtils.mapsAreEqual(expectedDept, deptFromDb));
	}

	@Test
	void testUpdateDepartment() {
		// ARRANGE
		var expectedRowCount = countRowsInTable(jdbcTemplate, "dept");
		var updateDept = new Department(10, "Updated", "New York");
		// verify updated record does not exist in the database
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "dept",
				"deptno = 10 and dname = 'Updated'"));

		// ACT
		var success = dao.updateDepartment(updateDept);

		// ASSERT
		assertTrue(success);
		// verify the number of department records has not changed
		assertEquals(expectedRowCount, countRowsInTable(jdbcTemplate, "dept"));
		// verify all Department properties were updated correctly
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "dept", """
				 deptno = 10
			 and dname = 'Updated'
			 and loc = 'New York'
		"""));

//		var deptFromDb = jdbcTemplate.queryForMap(
//			"select deptno, dname, loc from dept where deptno = " + updateDept.getId()
//		);
//		Map<String, Object> expectedDept = Map.of(
//			"deptno", updateDept.getId(),
//			"dname", updateDept.getName(),
//			"loc", updateDept.getLocation()
//		);
//		assertTrue(DbTestUtils.mapsAreEqual(expectedDept, deptFromDb));

	}

	// This is the test the exercise asks for
	@Test
	void testReassignEmployeesDeleteDepartment() {
		// ARRANGE
		// We want to be sure the emp updates are correct, so after calling the stored procedure
		// we'll verify that every employee who was in dept 10 is now in dept 20.
		var oldDept10RowCount = countRowsInTableWhere(jdbcTemplate, "dept", "deptno = 10");
		// Create Sets with the ids of employees in dept 10 and dept 20 for use later.
		var empIdsInDept10 = new HashSet<>(jdbcTemplate.queryForList(
				"SELECT empno FROM emp WHERE deptno = 10", Integer.class));
		var empIdsInDept20 = new HashSet<>(jdbcTemplate.queryForList(
				"SELECT empno FROM emp WHERE deptno = 20", Integer.class));

		// ACT
		dao.reassignEmployeesDeleteDepartment(10, 20);

		// ASSERT
		// Verify number of rows in dept table is 1 less than before
		assertEquals(oldDept10RowCount - 1,
				countRowsInTableWhere(jdbcTemplate, "dept", "deptno = 10"));
		// Verify dept 10 is gone from the dept table
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "dept", "deptno = 10"));
		// Verify there are no employees in dept id 10
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "emp", "deptno = 10"));

		// Now verify all dept 10 employees were moved to dept 20.
		// First, create a Set that is the union of employee ids formerly in depts 10 and 20
		empIdsInDept20.addAll(empIdsInDept10);
		// Next, create a Set that the ids of all employess now in dept 20
		var empIdsInDept20Now = new HashSet<>(jdbcTemplate.queryForList(
				"SELECT empno FROM emp WHERE deptno = 20", Integer.class));
		// Finally, verify that the two sets are equal.
		assertEquals(empIdsInDept20, empIdsInDept20Now);
	}
}
