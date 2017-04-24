package com.tgs.warehouse.connection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

	private static DBConnection dbConnection = null;
	private DataSource dataSource = null;
	private DBConnection() {
	}

	// method called from the context listener and stored in the dbConnection instance variable
	public static DBConnection getInstance() {
		if (dbConnection == null) {
			dbConnection = new DBConnection();
		}
		return dbConnection;
	}

	// called after the above from the context listener to set the datasource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	//to be used from DAO to get a connection object
	public Connection getConnection() {
		Connection connection = null;
			try {
				connection = dataSource.getConnection();
			} 
			catch (SQLException se) {
				System.out.println(se);
			}
		return connection;
	}
}
