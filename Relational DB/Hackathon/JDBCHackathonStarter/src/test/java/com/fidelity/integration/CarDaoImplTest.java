package com.fidelity.integration;

import com.fidelity.model.Car;
import com.fidelity.model.CarBodyType;
import com.fidelity.model.CarEngine;
import com.fidelity.model.CarEngineType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

class CarDaoImplTest {
    JdbcTemplate jdbcTemplate;
    DbTestUtils dbTestUtils;
    CarDaoImpl carDao;
    SimpleDataSource dataSource;
    Connection connection;
    TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        dataSource = new SimpleDataSource();
        connection = dataSource.getConnection();
        transactionManager = new TransactionManager(dataSource);
        transactionManager.startTransaction();
        carDao = new CarDaoImpl(dataSource);

        dbTestUtils = new DbTestUtils(connection);
        jdbcTemplate = dbTestUtils.initJdbcTemplate();

        MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void tearDown() {
        transactionManager.rollbackTransaction();
        dataSource.shutdown();
    }

    @Test
    void getCars() {
        List<Car> carList = carDao.getCars();
        System.out.println(carList);
        assertEquals(7, carList.size());
    }

    @Test
    void insertCarTest() throws SQLException {
        int id = 8888;
        int oldSize = countRowsInTable(jdbcTemplate, "E_CAR");
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "E_CAR", "Id = " + id));
        CarEngine mockCarEngine = new CarEngine(id, CarEngineType.GASOLINE, 3.5f, 300);
        Car mockCar = new Car(id, "Aeromobil", "Camry", CarBodyType.OTHER.getName(), mockCarEngine, LocalDate.of(2023, 1, 1));

        Car car = carDao.insertCarAndEngine(mockCar, mockCarEngine);

        assertEquals(mockCar, car);
        int newSize = countRowsInTable(jdbcTemplate, "E_CAR");
        assertEquals(oldSize + 1, newSize);
        assertEquals(1, countRowsInTableWhere(jdbcTemplate, "E_CAR", """
					id = 8888
				and make = 'Aeromobil'
				and model = 'Camry'
			"""));
    }

    @Test
    void updateCarAndCarEngineTest() throws SQLException {
        int id = 6060;
        int oldSize = countRowsInTable(jdbcTemplate, "E_CAR");
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "E_CAR", "Id = " + id));
        CarEngine mockCarEngine = new CarEngine(id, CarEngineType.GASOLINE, 3.5f, 300);
        Car mockCar = new Car(id, "Aeromobil", "Camry", CarBodyType.OTHER.getName(), mockCarEngine, LocalDate.of(2023, 1, 1));
        carDao.insertCarAndEngine(mockCar, mockCarEngine);

        Car newMockCar = new Car(id, "newMake", "newModel", CarBodyType.OTHER.getName(), mockCarEngine, LocalDate.of(2023, 1, 1));

        Car carUpdated = carDao.updateCarAndCarEngine(newMockCar, mockCarEngine);

        assertEquals(newMockCar, carUpdated);
        int newSize = countRowsInTable(jdbcTemplate, "E_CAR");
        assertEquals(oldSize + 1, newSize);
        assertEquals(1, countRowsInTableWhere(jdbcTemplate, "E_CAR", """
					id = 6060
				and make = 'newMake'
				and model = 'newModel'
			"""));
    }

    @Test
    void testUpdateEmployeeThrowsException() throws SQLException {
        int invalidDeptId = 42;
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "E_CAR", "Id = " + invalidDeptId));
        CarEngine mockCarEngine = new CarEngine(invalidDeptId, CarEngineType.GASOLINE, 3.5f, 300);
        Car mockCar = new Car(invalidDeptId, "Aeromobil", "Camry", CarBodyType.OTHER.getName(), mockCarEngine, LocalDate.of(2023, 1, 1));

        assertThrows(DatabaseException.class, () -> {
            carDao.updateCarAndCarEngine(mockCar, mockCarEngine);
        });
    }

    @Test
    void deleteCar() throws SQLException {
        int carId = 7369;

        CarEngine mockCarEngine = new CarEngine(carId, CarEngineType.GASOLINE, 3.5f, 300);
        Car mockCar = new Car(carId, "Aeromobil", "Camry", CarBodyType.OTHER.getName(), mockCarEngine, LocalDate.of(2023, 1, 1));
        carDao.insertCarAndEngine(mockCar, mockCarEngine);


        int oldSize = countRowsInTable(jdbcTemplate, "E_CAR");
        assertEquals(1, countRowsInTableWhere(jdbcTemplate, "E_CAR", "Id = " + carId));

        boolean success = carDao.deleteCar(carId);

        assertTrue(success);
        assertEquals(oldSize - 1, countRowsInTable(jdbcTemplate, "E_CAR"));
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "E_CAR", "Id = " + carId));
    }

    @Test
    void testDeleteEmployeeNotPresent() throws SQLException {
        int oldSize = countRowsInTable(jdbcTemplate, "E_CAR");
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "E_CAR", "id = 9999"));

        boolean success = carDao.deleteCar(9999);

        assertFalse(success);
        assertEquals(oldSize, countRowsInTable(jdbcTemplate, "E_CAR"));
    }

}