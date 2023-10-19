package com.fidelity.integration;

import com.fidelity.model.PhoneContract;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

class PhoneContractDaoImplTest {

    PhoneContractDao phoneContractDao;
    JdbcTemplate jdbcTemplate;
    DbTestUtils dbTestUtils;
    SimpleDataSource dataSource;
    Connection connection;
    TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        dataSource = new SimpleDataSource();
        connection = dataSource.getConnection();
        dbTestUtils = new DbTestUtils(connection);
        jdbcTemplate = dbTestUtils.initJdbcTemplate();
        transactionManager = new TransactionManager(dataSource);

        // start the transaction
        transactionManager.startTransaction();

        phoneContractDao = new PhoneContractDaoImpl(dataSource);

//        PhoneContract phoneContract = new PhoneContract(pcid, pcname, ratesname, hours_quantity, ratesprice);
    }

    @AfterEach
    void tearDown() {
        transactionManager.rollbackTransaction();
        dataSource.shutdown();
    }

    @Test
    @DisplayName("getFullPhoneContracts Test Success")
    void getFullPhoneContractsTest_Success() throws SQLException {
        List<PhoneContract> clients = phoneContractDao.getFullPhoneContracts();
        assertEquals(6, clients.size());
    }

    @Test
    @DisplayName("getPhoneContractByID Test Success")
    void getPhoneContractByIDTest_Success() {
        List<PhoneContract> clients = phoneContractDao.getPhoneContractByID(1);
        assertEquals(2, clients.size());
    }

    @Test
    @DisplayName("getPhoneContractByID Test Success: Zero Records if ID Not Exists")
    void getPhoneContractByIDTest_Success_IDnotExists() {
        List<PhoneContract> clients = phoneContractDao.getPhoneContractByID(12);
        assertEquals(0, clients.size());
    }

    @Test
    @DisplayName("insertPhoneContract Test: Success")
    void insertPhoneContractTest_Success() {
        int pcID = 13;
        int oldSize = countRowsInTable(jdbcTemplate, "b_hours");
        PhoneContract phoneContract = new PhoneContract(pcID, "Test", "Rate", 23, new BigDecimal(56.00));
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "b_hours", "pcID = " + pcID));
        phoneContractDao.insertPhoneContract(phoneContract);
        int newSize = countRowsInTable(jdbcTemplate, "b_hours");
        System.out.println(oldSize);
        System.out.println(newSize);
        assertEquals(oldSize + 1, newSize);
    }

    @Test
    @DisplayName("insertPhoneContract Test: Success")
    void insertPhoneContractTest_DuplicateRecords() {
        int pcID = 13;
        int oldSize = countRowsInTable(jdbcTemplate, "b_hours");
        PhoneContract phoneContract = new PhoneContract(pcID, "Test", "Rate", 23, new BigDecimal(56.00));
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "b_hours", "pcID = " + pcID));
        phoneContractDao.insertPhoneContract(phoneContract);

        PhoneContract phoneContract2 = new PhoneContract(pcID, "Test", "Rate", 23, new BigDecimal(56.00));

        assertThrows(DatabaseException.class, () -> {
            phoneContractDao.insertPhoneContract(phoneContract2);
        });
    }

    @Test
    @DisplayName("deletePhoneContract Test Success")
    void deletePhoneContractTest_Success() {
//        int oldSizePhoneContracts = countRowsInTable(jdbcTemplate, "b_phoneContracts");
        int oldSizeHours = countRowsInTable(jdbcTemplate, "b_hours");

        int id = 12;

        PhoneContract phoneContract = new PhoneContract(id, "Test", "Rate", 23, new BigDecimal(56.00));

        phoneContractDao.insertPhoneContract(phoneContract);

        int oldSizeHoursAdd = countRowsInTable(jdbcTemplate, "b_hours");

        assertEquals(oldSizeHoursAdd, oldSizeHours);

        phoneContractDao.deletePhoneContract(id);

//        int newSizePhoneContracts = countRowsInTable(jdbcTemplate, "b_phoneContracts");
        int newSizeHours = countRowsInTable(jdbcTemplate, "b_hours");

//        assertEquals(oldSizePhoneContracts - 1, newSizePhoneContracts);
        assertEquals(oldSizeHours - 2, newSizeHours);
    }

    @Test
    @DisplayName("deletePhoneContractTest IdNotExists")
    void deletePhoneContractTest_IdNotExists() {
//        int oldSizePhoneContracts = countRowsInTable(jdbcTemplate, "b_phoneContracts");
        int oldSizeHours = countRowsInTable(jdbcTemplate, "b_hours");

        int id = 200;

        assertThrows(DatabaseException.class, () -> {
            phoneContractDao.deletePhoneContract(id);
        });
    }

//    @Test
//    @DisplayName("getPhoneContractByID Test Failure: ID Doesn't Exist")
//    void getPhoneContractByIDTest_Failure_IDNotExists() {
//        assertThrows(DatabaseException.class, () -> {
//            phoneContractDao.getPhoneContractByID(12);
//        });
//    }

}