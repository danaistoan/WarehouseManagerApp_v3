package com.tgs.warehouse.listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// Not used
@WebListener
public class AppServletContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
		System.out.println("ServletContextListener destroyed");		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
        //
	}
}
