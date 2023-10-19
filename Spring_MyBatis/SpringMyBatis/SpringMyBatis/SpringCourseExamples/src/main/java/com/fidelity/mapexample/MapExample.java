package com.fidelity.mapexample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapExample {
	private final Logger logger = LoggerFactory.getLogger(MapExample.class);

	private Connection getConnection() {
		return null;
	}

	public Map<Employee, Phone> getEmployeePhoneNumbers(Employee emp) throws SQLException {
		String sql = "SELECT type, number FROM phone_numbers WHERE employee_id = ?";
		Map<Employee, Phone> directory = new HashMap<>();
		Connection conn = getConnection();
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, emp.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String type = rs.getString("type");
				String number = rs.getString("number");
				Phone phone = new Phone(type, number);
				directory.put(emp, phone);
			}
		} catch (SQLException e) {
			logger.error("Cannot execute SQL query for phone: {}", emp.getId(), e);
			throw new DatabaseException("Cannot execute SQL query for phone: " + emp.getId(), e);	
		}

		Employee janitor = new Employee();
		Phone janitorPhone = directory.get(janitor);
		
		System.out.println(janitorPhone);

		return directory;
	}
}
