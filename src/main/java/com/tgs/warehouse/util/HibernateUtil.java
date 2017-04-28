package com.tgs.warehouse.util;
import com.tgs.warehouse.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration().configure();
			configuration.addAnnotatedClass(ProductPallet.class);
			configuration.addAnnotatedClass(ProductPackage.class);
			configuration.addAnnotatedClass(User.class);
			configuration.addAnnotatedClass(Shipment.class);
            configuration.addAnnotatedClass(PlannedShipment.class);

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
