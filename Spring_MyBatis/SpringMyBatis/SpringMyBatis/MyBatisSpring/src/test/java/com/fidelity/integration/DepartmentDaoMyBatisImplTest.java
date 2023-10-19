package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.fidelity.business.Department;
import com.fidelity.business.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
class DepartmentDaoMyBatisImplTest {

	@Autowired
	DepartmentDaoMyBatisImpl dao;

	@Test
	public void queryFullDatabaseTest() {
		List<Department> departments = dao.queryAllDepartments();
		assertEquals(4, departments.size());
	}

	@Test
	public void empObjects() {
		List<Employee> employees = dao.queryAllEmployees(10);
		assertEquals(3, employees.size());
	}

}