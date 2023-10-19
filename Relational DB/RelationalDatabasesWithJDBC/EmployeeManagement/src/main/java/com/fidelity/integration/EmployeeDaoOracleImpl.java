package com.fidelity.integration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fidelity.model.Employee;
import com.fidelity.model.PerformanceReviewResult;

public class EmployeeDaoOracleImpl extends EmployeeDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String queryForAllEmployees = """
			SELECT empno, ename, job, mgr, hiredate, 
			       sal, comm, deptno 
			  FROM emp
		  ORDER BY empno
		""";

    private final String insertEmployee = """
			INSERT 
			INTO emp (ename, job, mgr, hiredate, sal, comm, deptno, empno)
			VALUES (?, ?, ?, ?, ?, ?, ?, ?)
		""";

    private final String updateEmployee = """
			UPDATE emp 
			   SET ename = ?, job = ?, mgr = ?, hiredate = ?,
			       sal = ?, comm = ?, deptno = ? 
			 WHERE empno = ?
		""";

    private final String deleteEmployee = """
			DELETE 
			 FROM emp 
			WHERE empno = ?		
		""";

    private final String queryEmployeesByDepartment = """
			SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno 
			  FROM emp 
			 WHERE deptno = ?
			 ORDER BY empno
		""";

    private final String queryForEmployee = """
			SELECT e.empno, e.ename, e.job, e.mgr, e.hiredate,  
			       e.sal, e.comm, e.deptno
			  FROM emp e
			 WHERE e.empno = ?
			 ORDER BY e.empno				
		""";

    private final String queryByName = """
			SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno 
		 	  FROM emp 
			 WHERE ename = ?
			 ORDER BY empno
		""";

    private final String queryForEmployeeWithPerformance = """
			SELECT e.empno, e.ename, e.job, e.mgr, hiredate, 
			       e.sal, e.comm, e.deptno, p.perf_code
			  FROM emp e
			  LEFT JOIN emp_perf p
			    ON e.empno = p.empno
			 WHERE e.empno = ?
			 ORDER BY e.empno				
		""";


    public EmployeeDaoOracleImpl(DataSource ds) {
        super(ds);
    }

    @Override
    public List<Employee> queryAllEmployees() {
        List<Employee> emps = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement(queryForAllEmployees)) {
            emps = getAndHandleResults(stmt);
        }
        catch (SQLException e) {
            logger.error("Cannot execute queryAllEmployees: ", e);
            throw new DatabaseException("Cannot execute queryAllEmployees", e);
        }
        return emps;
    }

    @Override
    /*
     * This method only returns one Employee, but the generic query handler
     * (getAndHandleResults) returns a list.
     *
     * Return the first item in the list, if there is one. Doing it this way avoids
     * an index out of bounds exception.
     */
    public Employee queryEmployeeById(int empNo) {
        List<Employee> emps = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement(queryForEmployee)) {
            stmt.setInt(1, empNo);
            emps = getAndHandleResults(stmt);
        }
        catch (SQLException e) {
            logger.error("Cannot execute queryEmployeeById: for {}", empNo, e);
            throw new DatabaseException("Cannot execute queryEmployeeById", e);
        }
        return emps.size() > 0 ? emps.get(0) : null;
    }

    @Override
    public Employee queryEmployeeByIdWithPerformance(int empNo) {
        List<Employee> emps = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement(queryForEmployeeWithPerformance)) {
            stmt.setInt(1, empNo);
            emps = getAndHandleResultsWithPerformance(stmt);
        }
        catch (SQLException e) {
            logger.error("Cannot execute queryEmployeeById: for {}", empNo, e);
            throw new DatabaseException("Cannot execute queryEmployeeById", e);
        }
        return emps.size() > 0 ? emps.get(0) : null;
    }

    /***** DML Methods *****/
    @Override
    public Employee insertEmployee(Employee emp) {
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement(insertEmployee)) {
            stmt.setString(1, emp.getEmpName());
            stmt.setString(2, emp.getJob());
            stmt.setInt(3, emp.getMgrNumber());
            java.sql.Date hireDate = java.sql.Date.valueOf(emp.getHireDate());
            stmt.setDate(4, hireDate);
            BigDecimal salary = emp.getSalary().setScale(2, RoundingMode.HALF_UP);
            stmt.setBigDecimal(5, salary);
            BigDecimal commission = emp.getComm();
            if (commission != null) {
                commission.setScale(2, RoundingMode.HALF_UP);
            }
            stmt.setBigDecimal(6, commission);
            stmt.setInt(7, emp.getDeptNumber());
            stmt.setInt(8, emp.getEmpNumber());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DatabaseException("Unable to insert Employee with id=" + emp.getEmpNumber(), ex);
        }

        return emp;
    }

    @Override
    public Employee updateEmployee(Employee emp) {
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement(updateEmployee)) {
            stmt.setString(1, emp.getEmpName());
            stmt.setString(2, emp.getJob());
            stmt.setInt(3, emp.getMgrNumber());
            java.sql.Date hireDate = java.sql.Date.valueOf(emp.getHireDate());
            stmt.setDate(4, hireDate);
            stmt.setBigDecimal(5, emp.getSalary());
            stmt.setBigDecimal(6, emp.getComm());
            stmt.setInt(7, emp.getDeptNumber());
            stmt.setInt(8, emp.getEmpNumber());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DatabaseException("Unable to update Employee with id=" + emp.getEmpNumber(), ex);
        }

        return emp;
    }

    @Override
    public boolean deleteEmployee(int empNumber) {
        try(Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(deleteEmployee)) {
                stmt.setInt(1, empNumber);
                int rowsDeleted = stmt.executeUpdate();
                return rowsDeleted > 0;
            }
        }
        catch (SQLException ex) {
            throw new DatabaseException("Unable to delete Employee with id= " + empNumber, ex);
        }
    }

    /**
     * Query Employees by name
     */
    @Override
    public List<Employee> queryEmployeeByName(String name) {
        List<Employee> emps = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement(queryByName)) {
            stmt.setString(1, name);
            emps = getAndHandleResults(stmt);
        }
        catch (SQLException e) {
            logger.error("Cannot execute queryEmployeeByName: for {}", name, e);
            throw new DatabaseException("Cannot execute queryEmployeeByName", e);
        }
        return emps;
    }

    /**
     * Query Employees by Department
     */
    @Override
    public List<Employee> queryEmployeeByDepartment(int departmentId) {
        List<Employee> emps = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement(queryEmployeesByDepartment)) {
            stmt.setInt(1, departmentId);
            emps = getAndHandleResults(stmt);
        }
        catch (SQLException e) {
            logger.error("Cannot execute queryEmployeeByDepartment: for {}", departmentId, e);
            throw new DatabaseException("Cannot execute queryEmployeeByDepartment", e);
        }

        return emps;
    }

    /******* Utility Methods *******/
    private List<Employee> getAndHandleResults(PreparedStatement stmt) throws SQLException {
        List<Employee> emps = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int empNumber = rs.getInt("empno");
            String empName = rs.getString("ename");
            String job = rs.getString("job");
            int mgrNumber = rs.getInt("mgr");
            java.sql.Date dbDate = rs.getDate("hiredate");
            LocalDate hireDate = dbDate.toLocalDate();
            BigDecimal sal = rs.getBigDecimal("sal");
            BigDecimal comm = rs.getBigDecimal("comm");
            int deptNumber = rs.getInt("deptno");

            Employee emp = new Employee(empNumber, empName, job, mgrNumber, hireDate, sal, comm, deptNumber);

            emps.add(emp);
        }
        return emps;
    }

    private List<Employee> getAndHandleResultsWithPerformance(PreparedStatement stmt) throws SQLException {
        List<Employee> emps = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int empNumber = rs.getInt("empno");
            String empName = rs.getString("ename");
            String job = rs.getString("job");
            int mgrNumber = rs.getInt("mgr");
            java.sql.Date dbDate = rs.getDate("hiredate");
            LocalDate hireDate = dbDate.toLocalDate();
            BigDecimal sal = rs.getBigDecimal("sal");
            BigDecimal comm = rs.getBigDecimal("comm");
            int deptNumber = rs.getInt("deptno");
            PerformanceReviewResult performance = null;
            int perfCode = rs.getInt("perf_code");
            if (perfCode > 0) {
                performance = PerformanceReviewResult.of(perfCode);
            }

            Employee emp = new Employee(empNumber, empName, job, mgrNumber, hireDate,
                    sal, comm, deptNumber, performance);

            emps.add(emp);
        }
        return emps;
    }

}
