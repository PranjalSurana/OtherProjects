package com.fidelity.dbtestutils;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.core.io.PathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.jdbc.JdbcTestUtils;

public class DatabaseTestSupport {
	private JdbcTemplate jdbcTemplate;

	public DatabaseTestSupport(JdbcTemplate template) {
		jdbcTemplate = template;
	}

	public void dropTablesIgnoreExceptions(String[] tables) {
		// Drop each table
		for (String table : tables) {
			try {
				JdbcTestUtils.dropTables(jdbcTemplate, table);
			} catch (Exception e) {
				// Ignore exception generated from sql drop table command
			}
		}
	}

	public void executeScripts(DataSource datasource, PathResource[] sqlscripts, String[] tables) throws SQLException {
		if (sqlscripts == null) {
			throw new IllegalArgumentException("No database scripts provided");
		}
		
		try (Connection connection = datasource.getConnection()) {
			// drop the tables
			dropTablesIgnoreExceptions(tables);
			
			// Run the database scripts 
			ScriptUtils.executeSqlScript(connection, sqlscripts[0]);  // schema
			ScriptUtils.executeSqlScript(connection, sqlscripts[1]);  // data
		}
	}
}
