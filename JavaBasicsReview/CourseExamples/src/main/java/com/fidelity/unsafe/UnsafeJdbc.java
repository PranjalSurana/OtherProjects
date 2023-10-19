package com.fidelity.unsafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UnsafeJdbc {
	
	public List<Permission> queryPermissionsByUserUnsafe(String user) {
		String sql = "SELECT perm FROM permissions WHERE username = '" + user + "'";
		System.out.println(sql);
		List<Permission> perms = new ArrayList<>();
		Statement stmt = null;
		Connection conn = getConnection();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String perm = rs.getString("perm");
				Permission permission = new Permission(perm);
				perms.add(permission);
			}
		} catch (SQLException e) {
			e.printStackTrace();	// We'll see a better way of dealing with this soon
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return perms;
	}

	public List<Permission> queryPermissionsByUserSafe(String user) {
		String sql = "SELECT perm FROM permissions WHERE username = ?";
		List<Permission> perms = new ArrayList<>();
		PreparedStatement stmt = null;
		Connection conn = getConnection();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String perm = rs.getString("perm");
				Permission permission = new Permission(perm);
				perms.add(permission);
			}
		} catch (SQLException e) {
			e.printStackTrace();	// We'll see a better way of dealing with this soon
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return perms;
	}

	private Connection conn;

	private Connection getConnection() {			
		if (conn == null) {
			String dbUrl = "jdbc:oracle:thin:@localhost:1521:xepdb1";
			String user = "scott";
			String password = "TIGER";

			try {
				conn = DriverManager.getConnection(dbUrl, user, password);
			} catch (SQLException e) {
				e.printStackTrace(); // better way coming soon
			}
		}
		return conn;
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
