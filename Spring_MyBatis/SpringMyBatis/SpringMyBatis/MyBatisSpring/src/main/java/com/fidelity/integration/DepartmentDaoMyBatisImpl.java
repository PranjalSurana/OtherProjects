package com.fidelity.integration;

import com.fidelity.business.Department;
import com.fidelity.business.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("departmentsDao")
public class DepartmentDaoMyBatisImpl {

    @Autowired
    DepartmentMapper mapper;

    public List<Department> queryAllDepartments() {
        return mapper.getDepartments();
    }

    public List<Employee> queryAllEmployees(int i) {
        return mapper.getEmployees(10);
    }
}