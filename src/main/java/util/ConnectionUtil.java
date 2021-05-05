package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

	private static Connection connection;
	
	public static Connection getHardCodedConnection() throws SQLException
	{

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "password";

		if(connection == null || connection.isClosed()) {
		connection = DriverManager.getConnection(url, username, password);
		}
		
		return connection;
	}
	
	public static Connection getConnectionFromFile() throws SQLException, IOException
	{
		// loading connection.properties file in order to read properties
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		prop.load(loader.getResourceAsStream("connection.properties"));
		
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");

		if(connection == null || connection.isClosed()) {
		connection = DriverManager.getConnection(url, username, password);
		}
		
		return connection;
	}
	
	public static Connection getConnectionFromEnv() throws SQLException
	{

		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USER");
		String password = System.getenv("DB_PASS");

		if(connection == null || connection.isClosed()) {
		connection = DriverManager.getConnection(url, username, password);
		}
		
		return connection;
	}

	public static Connection getConnectionH2() throws SQLException{
			if(connection==null || connection.isClosed()){
				try {
					Class.forName("org.h2.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				connection = DriverManager.getConnection("jdbc:h2:mem:db1", "sa", "");
			}
		return connection;
	}
}
