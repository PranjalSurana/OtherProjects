package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.fidelity.model.Employee;

class EmployeeDaoQueryTest {
    private EmployeeDao dao;
    private SimpleDataSource dataSource;

    private static Employee emp7369 = new Employee(7369, "SMITH", "CLERK", 7902,
            LocalDate.of(1980, 12, 17), new BigDecimal("800.00"), null, 20);
    private static Employee emp7934 = new Employee(7934, "MILLER", "CLERK", 7782,
            LocalDate.of(1982, 1, 23), new BigDecimal("1300.00"), null, 10);

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = new SimpleDataSource();
        dao = new EmployeeDaoOracleImpl(dataSource);
    }

    @AfterEach
    void tearDown() throws SQLException {
        dataSource.shutdown();
    }

    /***** Query Tests *****/

    @Test
    void testQueryAllEmployees() {

        List<Employee> emps = dao.queryAllEmployees();

        assertEquals(14, emps.size());
    }

    @Test
    void testQueryEmployeeByNumber_Success() {
        int id = 7369;

        Employee emp = dao.queryEmployeeById(id);

        assertEquals(emp7369, emp);
    }

    @Test
    void testQueryEmployeeByNumber_NotPresent() {
        int id = 8000;

        Employee emp = dao.queryEmployeeById(id);

        assertNull(emp);
    }

    @Test
    void testNoSqlInjection() {
        String name = "BobbyTables' OR '1' = '1";

        List<Employee> employees = dao.queryEmployeeByName(name);

        assertEquals(employees.size(), 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {7369, 7499, 7839})
    void testQueryEmployeeById(int id) {

        Employee emp = dao.queryEmployeeById(id);

        assertEquals(id, emp.getEmpNumber());
    }

    @ParameterizedTest
    @ValueSource(ints = {8000, 4242, 2001, 999})
    void testQueryEmployeeById_InvalidId(int id) {

        Employee emp = dao.queryEmployeeById(id);

        assertNull(emp);
    }

    @ParameterizedTest
    @CsvSource({   // pass 2 integer arguments to the test method each time it's called
            "10, 3",
            "20, 5",
            "30, 6",
            "40, 0"
    })
    void testQueryEmployeeByDepartment(int departmentId, int howManyEmps) {

        List<Employee> employees = dao.queryEmployeeByDepartment(departmentId);

        assertEquals(howManyEmps, employees.size());
        for (Employee employee : employees) {
            assertEquals(employee.getDeptNumber(), departmentId);
        }
    }

    @ParameterizedTest
    @MethodSource  // to generate arguments for the test method, JUnit calls
        // a static method with the same name as the test method
    void testQueryEmployeeByName(String name, Employee expectedEmp) {

        List<Employee> employees = dao.queryEmployeeByName(name);

        assertEquals(1, employees.size());
        assertEquals(expectedEmp, employees.get(0));
    }

    /**
     * Each time JUnit calls this method, it generates 2 arguments for the
     * testQueryEmployeeByName() method.
     */
    private static Stream<Arguments> testQueryEmployeeByName() {
        return Stream.of(
                Arguments.of("SMITH", emp7369),
                Arguments.of("MILLER", emp7934)
        );
    }

}