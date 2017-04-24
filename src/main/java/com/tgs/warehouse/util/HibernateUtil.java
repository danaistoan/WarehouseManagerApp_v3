package com.tgs.warehouse.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.tgs.warehouse.entities.ProductPackage;
import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.entities.User;

public class HibernateUtil {

	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration().configure();
			configuration.addAnnotatedClass(ProductPallet.class);
			configuration.addAnnotatedClass(ProductPackage.class);
			configuration.addAnnotatedClass(User.class);

			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(builder.build());
			return sessionFactory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("There was an error building the factory");
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
