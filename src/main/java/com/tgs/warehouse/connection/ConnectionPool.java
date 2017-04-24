package com.tgs.warehouse.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPool {
	
	static final BasicDataSource ds_dbcp2 = new BasicDataSource();
   
    static Properties prop = new Properties();
    static {
        try {
            prop.load(ConnectionPool.class.getClassLoader().getResourceAsStream("DBConnection.properties"));

            ds_dbcp2.setDriverClassName( prop.getProperty("DriverClass") );
            ds_dbcp2.setUrl( prop.getProperty("dburl") );
            ds_dbcp2.setUsername( prop.getProperty("user") );
            ds_dbcp2.setPassword( prop.getProperty("pass") );
            ds_dbcp2.setInitialSize(5);

        } catch (IOException e) {   
        	e.printStackTrace();
        }      
    }

    public static Connection getDBCP2Connection() throws SQLException {
        return ds_dbcp2.getConnection();
    }
}
