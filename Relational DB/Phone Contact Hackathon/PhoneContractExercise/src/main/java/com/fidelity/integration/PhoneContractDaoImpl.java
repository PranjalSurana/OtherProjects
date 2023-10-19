package com.fidelity.integration;

import com.fidelity.model.PhoneContract;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhoneContractDaoImpl implements PhoneContractDao {

    List<PhoneContract> phoneContractList;

    DataSource dataSource;
    Random random;

    public PhoneContractDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.random = new Random();
    }

    DataSource getDataSource() {
        return dataSource;
    }

    private final static String GET_ALL_CONTRACTS_QUERY = """
        SELECT p.pcid as pcid,
               p.pcname as pcname,
               h.hoursid as hoursid,
               h.hours_quantity as hours_quantity,
               r.ratesid as ratesid,
               r.ratesname as ratesname,
               COALESCE(TO_CHAR(r.ratesprice, '0.00'), '0.00') AS ratesprice
        FROM b_hours h
        INNER JOIN b_phoneContracts p ON p.pcid = h.pcid
        INNER JOIN b_rates r ON r.ratesid = h.ratesid
        """;

    private final static String GET_CONTRACTS_BY_ID_QUERY = """
        SELECT p.pcid as pcid,
               p.pcname as pcname,
               h.hoursid as hoursid,
               h.hours_quantity as hours_quantity,
               r.ratesid as ratesid,
               r.ratesname as ratesname,
               COALESCE(TO_CHAR(r.ratesprice, '0.00'), '0.00') AS ratesprice
        FROM b_hours h
        JOIN b_phoneContracts p ON p.pcid = h.pcid
        INNER JOIN b_rates r ON r.ratesid = h.ratesid
        WHERE p.pcid = ?
        """;

    private final static String INSERT_INTO_PHONECONTRACTS_QUERY = """
            INSERT INTO b_phoneContracts (pcid, pcname) VALUES (?, ?)
            """;

    private final static String INSERT_INTO_HOURS_QUERY = "INSERT INTO b_hours (hoursid, pcid, hours_quantity) VALUES (?, ?, ?)";
    private final static String DELETE_FROM_PHONECONTRACTS_QUERY = "DELETE FROM b_phoneContracts WHERE pcid = ?";
    private final static String DELETE_FROM_HOURS_QUERY = "DELETE FROM b_hours WHERE pcid = ?";

    public int generateRandomHoursId() {
        return random.nextInt(100) + 1;
    }

    @Override
    public List<PhoneContract> getFullPhoneContracts() throws SQLException {
        phoneContractList = new ArrayList<>();
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_ALL_CONTRACTS_QUERY)) {
            ResultSet rs = stmt.executeQuery();
            phoneContractList = getAndHandleResults(rs);
        }
        catch (SQLException e) {
            throw new DatabaseException("Cannot execute GET_ALL_CONTRACTS_QUERY", e);
        }
        return phoneContractList;
    }

    @Override
    public List<PhoneContract> getPhoneContractByID(int pcId) {
        phoneContractList = new ArrayList<>();
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_CONTRACTS_BY_ID_QUERY)) {
            stmt.setInt(1, pcId);
            ResultSet rs = stmt.executeQuery();
            phoneContractList = getAndHandleResults(rs);
        }
        catch (SQLException e) {
            throw new DatabaseException("Cannot execute GET_CONTRACTS_BY_ID_QUERY", e);
        }
        return phoneContractList;
    }

    @Override
    public PhoneContract insertPhoneContract(PhoneContract phoneContract) {
        try (Connection connection = getDataSource().getConnection()) {
            insertInPhoneContractsTable(connection, phoneContract);
            insertInHoursTable(connection, phoneContract);
        }
        catch (SQLException | DatabaseException e) {
            throw new DatabaseException("Cannot execute INSERT Query", e);
        }
        return phoneContract;
    }

    public void insertInPhoneContractsTable(Connection connection, PhoneContract phoneContract) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_INTO_PHONECONTRACTS_QUERY)) {
            stmt.setInt(1, phoneContract.getPhoneContractId());
            stmt.setString(2, phoneContract.getPhoneContractName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseException("Cannot execute INSERT into phoneContracts Query", e);
        }
    }

    public void insertInHoursTable(Connection connection, PhoneContract phoneContract) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_INTO_HOURS_QUERY)) {
            stmt.setInt(1, generateRandomHoursId());
            stmt.setInt(2, phoneContract.getPhoneContractId());
            stmt.setInt(3, phoneContract.getQuantity());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseException("Cannot execute INSERT into Hours Query", e);
        }
    }

    @Override
    public void deletePhoneContract(int pcId) {
        try (Connection connection = getDataSource().getConnection()) {
            deleteFromHoursTable(connection, pcId);
//            if(valueFromHoursDeleted)
//                deleteFromPhoneContractsTable(connection, pcId);
        }
        catch (SQLException | DatabaseException e) {
            throw new DatabaseException("Cannot execute DELETE Query", e);
        }
    }

    public void deleteFromPhoneContractsTable(Connection connection, int pcId) throws SQLException {  // Handle when duplicates? ratesId needed
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_FROM_PHONECONTRACTS_QUERY)) {
            stmt.setInt(1, pcId);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DatabaseException("Cannot execute DELETE from phoneContracts Query", e);
        }
    }

    @Override
    public void deleteFromHoursTable(Connection connection, int pcId) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_FROM_HOURS_QUERY)) {
            stmt.setInt(1, pcId);
            stmt.executeUpdate();
            System.out.println("fvbf");
        }
        catch (SQLException e) {
            throw new DatabaseException("Cannot execute DELETE from Hours Query", e);
        }
    }

    private List<PhoneContract> getAndHandleResults(ResultSet resultSet) throws SQLException {
        List<PhoneContract> phoneContracts = new ArrayList<>();
        while(resultSet.next()) {
            int pcid = resultSet.getInt("pcid");
            String pcname = resultSet.getString("ratesid");
            int hoursid = resultSet.getInt("hoursid");
            int hours_quantity = resultSet.getInt("hours_quantity");
            int ratesid = resultSet.getInt("ratesid");
            String ratesname = resultSet.getString("ratesname");
            BigDecimal ratesprice = resultSet.getBigDecimal("ratesprice").setScale(2, BigDecimal.ROUND_HALF_UP);
            PhoneContract phoneContract = new PhoneContract(pcid, pcname, ratesname, hours_quantity, ratesprice);
            phoneContracts.add(phoneContract);
        }
        return phoneContracts;
    }

}