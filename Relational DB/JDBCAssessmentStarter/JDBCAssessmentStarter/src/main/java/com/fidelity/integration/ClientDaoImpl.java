package com.fidelity.integration;

import com.fidelity.model.Client;
import com.fidelity.model.ClientRisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<Client> clientList;
    private DataSource dataSource;
    private static final String GET_ALL_CLIENTS_QUERY = """
             SELECT c.client_id AS client_id,
                    c.client_name AS client_name,
                    c.client_risk AS client_risk,
                    p.phone_number AS phone_number
             FROM aa_client c
             LEFT JOIN aa_client_phone p
             ON c.client_id = p.client_id
             ORDER BY  c.client_id, c.client_name
            """;

    private static final String GET_CLIENT_BY_ID_QUERY = """
             SELECT c.client_id AS client_id,
                    c.client_name AS client_name,
                    c.client_risk AS client_risk,
                    p.phone_number AS phone_number
             FROM aa_client c
             LEFT JOIN aa_client_phone p
             ON c.client_id = p.client_id
             WHERE c.client_id = ?
             ORDER BY  c.client_id, c.client_name
            """;

    private static final String INSERT_INTO_CLIENTS_TABLE_QUERY = """
             INSERT INTO aa_client (client_id, client_name, client_risk) VALUES (?, ?, ?)
             """;

    private static final String INSERT_INTO_CLIENT_PHONE_TABLE_QUERY = """
            INSERT INTO aa_client_phone (client_id, phone_number) VALUES (?, ?)
           """;

    private static final String DELETE_FROM_CLIENTS_TABLE_QUERY = """
            DELETE FROM aa_client WHERE client_id = ?
             """;

    private static final String DELETE_FROM_CLIENT_PHONE_TABLE_QUERY = """
            DELETE FROM aa_client_phone WHERE client_id = ?
           """;

    public ClientDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    DataSource getDataSource() {
        return dataSource;
    }


    @Override
    public List<Client> getClients() {
        clientList = new ArrayList<>();
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_CLIENTS_QUERY)) {
            ResultSet resultSet = stmt.executeQuery();
            clientList = getAndHandleResults(resultSet);
        }
        catch (SQLException e) {
            logger.error("Cannot execute GET_ALL_CONTRACTS_QUERY", e);
            throw new DatabaseException("Cannot execute GET_ALL_CONTRACTS_QUERY", e);
        }
        return clientList;
    }

    @Override
    public List<Client> getClientById(int clientId) {
        if(clientId < 0) {
            logger.error("Client cannot have a negative ID");
            throw new IllegalArgumentException("Client cannot have a negative ID");
        }
        if(clientId > 99999) {
            logger.error("Client ID cannot be very large");
            throw new IllegalArgumentException("Client cannot be very large");
        }
        clientList = new ArrayList<>();
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_CLIENT_BY_ID_QUERY)) {
            stmt.setInt(1, clientId);
            ResultSet resultSet = stmt.executeQuery();
            clientList = getAndHandleResults(resultSet);
        }
        catch (SQLException e) {
            logger.error("Cannot execute " + GET_CLIENT_BY_ID_QUERY + " for Client with ID " + clientId, e);
            throw new DatabaseException("Cannot execute GET_ALL_CLIENT_BY_ID_QUERY", e);
        }
        if(!clientList.isEmpty())
            return clientList;
        else {
            logger.error("Client with ID " + clientId + " doesn't exist!");
            throw new DatabaseException("Client with ID " + clientId + " doesn't exist!");
        }
    }

    @Override
    public void insertClient(Client client) {
        if(client == null) {
            logger.error("Client cannot be null");
            throw new IllegalArgumentException("Client cannot be null");
        }
        if(client.getClientId() < 0) {
            logger.error("Client cannot have a negative ID");
            throw new IllegalArgumentException("Client cannot have a negative ID");
        }
        if(client.getClientId() > 99999) {
            logger.error("Client ID cannot be very large");
            throw new IllegalArgumentException("Client cannot be very large");
        }
        try (Connection connection = getDataSource().getConnection()) {
            insertIntoClientTable(connection, client);
            insertIntoClientPhoneTable(connection, client);
        }
        catch (SQLException | DatabaseException e) {
            logger.error("Cannot execute insertClient for " + client, e);
            throw new DatabaseException("Cannot INSERT client ", e);
        }
    }

    private void insertIntoClientTable(Connection connection, Client client) {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_INTO_CLIENTS_TABLE_QUERY)) {
            stmt.setInt(1, client.getClientId());
            stmt.setString(2, client.getClientName());
            stmt.setString(3, client.getClientRisk().getCode());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            logger.error("Cannot execute " + INSERT_INTO_CLIENTS_TABLE_QUERY + " for " + client, e);
            throw new DatabaseException("Cannot Insert Into Clients Table", e);
        }
    }

    private void insertIntoClientPhoneTable(Connection connection, Client client) {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_INTO_CLIENT_PHONE_TABLE_QUERY)) {
            stmt.setInt(1, client.getClientId());
            stmt.setString(2, client.getWorkPhone());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            logger.error("Cannot execute " + INSERT_INTO_CLIENTS_TABLE_QUERY + " for " + client, e);
            throw new DatabaseException("Cannot Insert Into Client Phone Table", e);
        }
    }
    @Override
    public void deleteClient(int clientId) {
        if(clientId < 0) {
            logger.error("Client cannot have a negative ID");
            throw new IllegalArgumentException("Client cannot have a negative ID");
        }
        if(clientId > 99999) {
            logger.error("Client ID cannot be very large");
            throw new IllegalArgumentException("Client cannot be very large");
        }
        int rowsDeleted = 0;
        try(Connection connection = getDataSource().getConnection()) {
            deleteFromClientPhoneTable(connection, clientId);
            rowsDeleted += deleteFromClientTable(connection, clientId);
            if(rowsDeleted == 0) {
                logger.error("Client with ID " + clientId + " doesn't exist");
                throw new DatabaseException("Client with ID " + clientId + " doesn't exist");
            }
        }
        catch (SQLException | DatabaseException e) {
            logger.error("Cannot execute deleteClient for client with ID " + clientId, e);
            throw new DatabaseException("Cannot DELETE client with ID " + clientId, e);
        }
    }

    private void deleteFromClientPhoneTable(Connection connection, int clientId) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_FROM_CLIENT_PHONE_TABLE_QUERY)) {
            stmt.setInt(1, clientId);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            logger.error("Cannot execute DELETE_FROM_CLIENT_PHONE_TABLE_QUERY for Client with ID" + clientId, e);
            throw new DatabaseException("Cannot DELETE client with ID " + clientId, e);
        }
    }

    private int deleteFromClientTable(Connection connection, int clientId) {
        int rowsDeleted = 0;
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_FROM_CLIENTS_TABLE_QUERY)) {
            stmt.setInt(1, clientId);
            rowsDeleted += stmt.executeUpdate();
            return rowsDeleted;
        }
        catch (SQLException e) {
            logger.error("Cannot execute DELETE_FROM_CLIENTS_TABLE_QUERY for Client with ID" + clientId, e);
            throw new DatabaseException("Cannot DELETE client with ID " + clientId, e);
        }
    }

    private List<Client> getAndHandleResults(ResultSet resultSet) throws SQLException {
        List<Client> clients = new ArrayList<>();
        while (resultSet.next()) {
            int clientId = resultSet.getInt("client_id");
            String clientName = resultSet.getString("client_name");
            ClientRisk clientRisk = ClientRisk.of(resultSet.getString("client_risk"));
            String phoneNumber = resultSet.getString("phone_number");
            Client client = new Client(clientId, clientName, clientRisk, phoneNumber);
            clients.add(client);
        }
        return clients;
    }

}