package com.tgs.warehouse.listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.tgs.warehouse.connection.DBConnection;

@WebListener
public class AppServletContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
		System.out.println("ServletContextListener destroyed");		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		try {
            //look up JNDI datasource
            //ServletContext servletContext = event.getServletContext();
            Context initContext = (Context) new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            if(envContext == null) {
            	System.out.println("Failed to get environment context");
                return;
            }
            
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/DatasourcesDB");
            if(dataSource == null) {
                System.out.println("Failed to get datasource");
                return;
            }
          
            //initialize the singleton DataSourceHolder object
            DBConnection dataSourceHolder = DBConnection.getInstance();
            dataSourceHolder.setDataSource(dataSource);
            
            System.out.println("ServletContextListener started");
		
		} catch (NamingException ne){
			System.out.println(ne);
		}
	}
}
