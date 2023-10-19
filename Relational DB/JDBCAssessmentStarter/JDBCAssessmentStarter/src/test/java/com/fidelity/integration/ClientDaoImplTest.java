package com.fidelity.integration;

import com.fidelity.model.Client;
import com.fidelity.model.ClientRisk;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

class ClientDaoImplTest {

    ClientDao clientDao;
    JdbcTemplate jdbcTemplate;
    DbTestUtils dbTestUtils;
    SimpleDataSource dataSource;
    Connection connection;
    TransactionManager transactionManager;

    String GET_ALL_CLIENTS_QUERY_TEST = """
            SELECT c.client_id AS client_id,
                   c.client_name AS client_name,
                   c.client_risk AS client_risk,
                   p.phone_number AS phone_number
            FROM aa_client c
            LEFT JOIN aa_client_phone p
            ON c.client_id = p.client_id
            """;

    @BeforeEach
    void setUp() {
        dataSource = new SimpleDataSource();
        connection = dataSource.getConnection();
        dbTestUtils = new DbTestUtils(connection);
        jdbcTemplate = dbTestUtils.initJdbcTemplate();
        transactionManager = new TransactionManager(dataSource);
        transactionManager.startTransaction();
        clientDao = new ClientDaoImpl(dataSource);
    }

    @AfterEach
    void tearDown() {
        transactionManager.rollbackTransaction();
        dataSource.shutdown();
    }

    @Test
    @DisplayName("getClients Test: Success")
    void getClientsTest_Success() {
        List<Client> clients = clientDao.getClients();
        assertFalse(clients.isEmpty(), "getClients() successfully runs");
    }

    @Test
    @DisplayName("getClientById Test: Success")
    void getClientByIdTest_Success() {
        List<Client> clients = clientDao.getClientById(1);
        assertEquals(1, clients.size());
    }

    @Test
    @DisplayName("getClientById Test: Failure - ID Not Exists")
    void getClientByIdTest_Failure_IDNotExist() {
        assertThrows(DatabaseException.class, () -> {
            clientDao.getClientById(99);
        });
    }

    @Test
    @DisplayName("getClientById Test: Failure - Negative ID")
    void getClientByIdTest_Failure_NegativeID() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientDao.getClientById(-1);
        });
    }

    @Test
    @DisplayName("getClientById Test: Failure - ID Very Large")
    void getClientByIdTest_Failure_VeryLargeID() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientDao.getClientById(9999999);
        });
    }

    @Test
    @DisplayName("insertClient Test: Success")
    void insertClientTest_Success() {
        int clientId = 17;
        String GET_CLIENT_BY_ID_QUERY_TEST = "SELECT client_id, client_name, client_risk FROM aa_client WHERE client_id = " + clientId;
        int oldSize = countRowsInTable(jdbcTemplate, "aa_client");
        Client newClient = new Client(clientId, "Test", ClientRisk.LOW, "111");
        assertEquals(0, jdbcTemplate.queryForList(GET_CLIENT_BY_ID_QUERY_TEST).size());
        clientDao.insertClient(newClient);
        int newSize = countRowsInTable(jdbcTemplate, "aa_client");
        assertEquals(oldSize + 1, newSize);
        assertEquals(1, jdbcTemplate.queryForList(GET_CLIENT_BY_ID_QUERY_TEST).size());
    }

    @Test
    @DisplayName("insertClient Test: Failure - Duplicate Client Id")
    void insertClientTest_Failure_Duplicate_ClientId() {
        int clientId = 17;
        String GET_CLIENT_BY_ID_QUERY_TEST = "SELECT client_id, client_name, client_risk FROM aa_client WHERE client_id = " + clientId;
        Client newClient = new Client(clientId, "Test", ClientRisk.LOW, "111");
        assertEquals(0, jdbcTemplate.queryForList(GET_CLIENT_BY_ID_QUERY_TEST).size());
        clientDao.insertClient(newClient);
        Client newClient2 = new Client(clientId, "Test2", ClientRisk.LOW, "111");
        assertEquals(1, jdbcTemplate.queryForList(GET_CLIENT_BY_ID_QUERY_TEST).size());
        assertThrows(DatabaseException.class, () -> {
            clientDao.insertClient(newClient2);
        });
    }

    @Test
    @DisplayName("insertClient Test: Failure - Null Client")
    void insertClientTest_Failure_Null_Client() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientDao.insertClient(null);
        });
    }

    @Test
    @DisplayName("insertClient Test: Failure - Negative ID")
    void insertClientTest_Failure_NegativeID() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientDao.insertClient(new Client(-1, "Test", ClientRisk.LOW, "111"));
        });
    }

    @Test
    @DisplayName("insertClient Test: Failure - ID Very Large")
    void insertClientTest_Failure_VeryLargeID() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientDao.insertClient(new Client(9999999, "Test", ClientRisk.LOW, "111"));
        });
    }

    @Test
    @DisplayName("deleteClient Test: Success")
    void deleteClientTest_Success() {
        int oldSizeOfClientsTable = countRowsInTable(jdbcTemplate, "aa_client");
        int oldSizeOfClientPhonesTable = countRowsInTable(jdbcTemplate, "aa_client_phone");
        int id = 1;
        clientDao.deleteClient(id);

        int newSizeOfClientsTable = countRowsInTable(jdbcTemplate, "aa_client");
        int newSizeOfClientPhonesTable = countRowsInTable(jdbcTemplate, "aa_client_phone");

        assertEquals(oldSizeOfClientsTable - 1, newSizeOfClientsTable);
        assertEquals(oldSizeOfClientPhonesTable - 1, newSizeOfClientPhonesTable);
    }

    @Test
    @DisplayName("deleteClient Test Failure ID Not Exists")
    void deleteClientTest_Failure_IDNotExists() {
        assertThrows(DatabaseException.class, () -> {
            clientDao.deleteClient(190);
        });
    }

    @Test
    @DisplayName("deleteClient Test: Failure - Negative ID")
    void deleteClientTest_Failure_NegativeID() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientDao.deleteClient(-1);
        });
    }

    @Test
    @DisplayName("deleteClient Test: Failure - ID Very Large")
    void deleteClientTest_Failure_VeryLargeID() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientDao.deleteClient(9999999);
        });
    }

}