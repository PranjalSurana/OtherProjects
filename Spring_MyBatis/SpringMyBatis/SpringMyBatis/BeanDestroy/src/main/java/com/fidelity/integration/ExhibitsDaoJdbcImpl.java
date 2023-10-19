package com.fidelity.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fidelity.business.Exhibit;

public class ExhibitsDaoJdbcImpl implements ExhibitsDao {
	private Log log = LogFactory.getLog(this.getClass());
	
	private Connection conn;

	private void getConnection() {
		try {
			Properties properties = new Properties();
			properties.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));

			String dburl = properties.getProperty("db.url");
			String user = properties.getProperty("db.username");
			String password = properties.getProperty("db.password");
			
			conn = DriverManager.getConnection(dburl, user, password);
		} catch (IOException | SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public synchronized List<Exhibit> getExhibits() {
		List<Exhibit> exhibits = new ArrayList<Exhibit>();
		getConnection();
		try (Statement statement = conn.createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT * from exhibits");
			while (rs.next()){
				String name = rs.getString("name");
				boolean permanent = rs.getInt("permanent") == 1;
				int cost = rs.getInt("cost");
				Exhibit e = new Exhibit(name, new BigDecimal(cost/100.0), permanent);
				exhibits.add(e);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return exhibits;

	}

	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		log.info("Dao resources released.");
	}
	
}
