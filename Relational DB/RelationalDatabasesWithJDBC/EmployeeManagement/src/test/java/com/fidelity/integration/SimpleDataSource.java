package com.fidelity.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.Properties;

/**
 * There are several very good open source implementations
 * of the Java DataSource interface and Connection pools to choose from.
 * So there is no need to define our own DataSource from the ground up.
 * However, there is value to peering into the inner workings of a 
 * very simple DataSource (hence the name of this class).
 *  
 *  This simple DataSource manages only one database Connection.
 *  
 * @author ROI Instructor
 *
 */
public class SimpleDataSource implements DataSource {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Connection connection;
    private Connection dbconnection;
    
    public SimpleDataSource() {
    }
    
	/**
	 * The client will call this method to obtain a database Connection.
	 */
    @Override
	public Connection getConnection() {
		try {	
			if (Objects.isNull(connection) || connection.isClosed()) {
				// Open the database connection
				dbconnection = openConnection();
				
				if (Objects.isNull(dbconnection)) {
					connection = null;
				}
				else {
					// Create a dynamic proxy with the database connection as its target
					// The ConnectionDynamicInvocationHandler class is defined at the end of this file
					ConnectionDynamicInvocationHandler proxy = new ConnectionDynamicInvocationHandler(dbconnection, this);
					
					// Assign the dynamic proxy to the connection field
					connection = (Connection) Proxy.newProxyInstance(ConnectionDynamicInvocationHandler.class.getClassLoader(),	
							new Class[] { Connection.class }, proxy);
				}
				
				// Return the dynamic proxy
				return connection;
			}			
		} catch (SQLException | NoSuchMethodException | SecurityException e) {
			String errorMessage = "Error creating database connection";
			logger.error(errorMessage, e);
			throw new DatabaseException(errorMessage, e);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }

        return connection;
	}
	
    /**
     * This method uses the DriverManager to open a connection
     * @return
     */
	private Connection openConnection() throws IOException {
//		Connection connection = null;
//		Properties properties = new Properties();
		Properties properties = new Properties();
		properties.load(this.getClass()
				.getClassLoader()
				.getResourceAsStream("db.properties"));
		String dbUrl = properties.getProperty("db.url");
		String user = properties.getProperty("db.username");
		String password = properties.getProperty("db.password");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbUrl, user, password);
		} catch (SQLException e) {
			e.printStackTrace(); // better way coming soon
		}
		return conn;
	}

	/**
	 * The shutdown() method should be called to insure that the database
	 * Connection is closed.
	 */	
	public void shutdown()  {
		if (dbconnection != null) {
			try {
				dbconnection.close();
				connection = null;
			} catch (SQLException e) {
				logger.error("Error closing database connection", e);
			}
		}
	}

	public void returnConnection (Connection connection) {
	}
	
	/****** DataSource methods that we are not using *****/
	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return null;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}


}

class ConnectionDynamicInvocationHandler implements InvocationHandler {
	Connection connection;
	SimpleDataSource dataSource;
	
	ConnectionDynamicInvocationHandler(Connection target, SimpleDataSource ds) throws NoSuchMethodException, SecurityException {
		connection = target;
		dataSource = ds;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		
		if ("close".equals(method.getName())){
			dataSource.returnConnection(connection);
		}
		else {
			result = method.invoke(connection, args);
		}
		
		
		return result;
	}
	
}
