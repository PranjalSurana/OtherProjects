package com.fidelity.integration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fidelity.model.PhoneContract;

public interface PhoneContractDao {
	List<PhoneContract> getFullPhoneContracts() throws SQLException;
	List<PhoneContract> getPhoneContractByID(int pcId);
	PhoneContract insertPhoneContract(PhoneContract phoneContract);
	void deletePhoneContract(int carId);
	void deleteFromHoursTable(Connection connection, int carId);

}
