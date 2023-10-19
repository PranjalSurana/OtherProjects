package com.fidelity.integration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fidelity.business.Department;

@Repository("departmentsDao")
public class DepartmentDaoMyBatisImpl {
	@Autowired
	private Logger logger;

	@Autowired
	private DepartmentMapper departmentMapper;

	public List<Department> getAllDepartments() {
		logger.debug("enter");
		return departmentMapper.getAllDepartments();
	}

	public List<Department> getAllDepartmentsAndEmployees() {
		logger.debug("enter");
		return departmentMapper.getAllDepartmentsAndEmployees();
	}

	public boolean insertDepartment(Department dept) {
		logger.debug("inserting department " + dept);

		return departmentMapper.insertDepartment(dept) == 1;
	}

	public boolean updateDepartment(Department dept) {
		logger.debug("updating department " + dept);

		return departmentMapper.updateDepartment(dept) == 1;
	}

	// Not asked for, but allows us to illustrate the constraint violation
	public boolean deleteDepartment(int deptId) {
		logger.debug("inserting department " + deptId);

		return departmentMapper.deleteDepartment(deptId) == 1;
	}

	public void reassignEmployeesDeleteDepartment(int deptDelete, int deptAssign) {
		logger.debug("reassigning department " + deptDelete + " to " + deptAssign);

		Map<Object, Object> parameterMap = new HashMap<>(2);
		parameterMap.put("deptDelete", deptDelete);
		parameterMap.put("deptAssign", deptAssign);
		departmentMapper.reassignEmployeesDeleteDepartment(parameterMap);
	}

}
